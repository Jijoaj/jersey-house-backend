(() => {
  "use strict";

  const SIZE_OPTIONS = [
    { value: "xs", label: "Extra Small" },
    { value: "s", label: "Small" },
    { value: "m", label: "Medium" },
    { value: "l", label: "Large" },
    { value: "xl", label: "Extra Large" },
  ];
  const RECORDS_PER_PAGE = 12;

  const state = {
    countries: [],
    leagues: [],
    teams: [],
    seasons: [],
    selectedCountry: "",
    selectedLeagues: new Set(),
    selectedTeams: new Set(),
    selectedSeasons: new Set(),
    selectedSizes: new Set(),
    sortBy: "teamName",
    sortByOrder: "ASC",
    page: 0,
    results: [],
    hasMore: false,
  };

  const el = (id) => document.getElementById(id);

  function escapeHtml(str) {
    return String(str ?? "").replace(/[&<>"']/g, (c) => (
      { "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]
    ));
  }

  function getUserId() {
    let id = localStorage.getItem("jh_user_id");
    if (!id) {
      id = "guest-" + Math.random().toString(36).slice(2, 8);
      localStorage.setItem("jh_user_id", id);
    }
    return id;
  }

  function setUserId(id) {
    localStorage.setItem("jh_user_id", id || "guest");
  }

  // ---------- toast ----------
  function toast(message, kind = "info") {
    const stack = el("toastStack");
    const node = document.createElement("div");
    node.className = `toast ${kind}`;
    node.textContent = message;
    stack.appendChild(node);
    setTimeout(() => node.remove(), 4200);
  }

  // ---------- API helpers ----------
  async function apiGet(path) {
    const res = await fetch(path);
    if (!res.ok) throw await toError(res);
    return res.json();
  }

  async function apiPost(path, body, extraHeaders = {}) {
    const res = await fetch(path, {
      method: "POST",
      headers: { "Content-Type": "application/json", ...extraHeaders },
      body: JSON.stringify(body),
    });
    if (!res.ok) throw await toError(res);
    const text = await res.text();
    return text ? JSON.parse(text) : null;
  }

  async function toError(res) {
    try {
      const data = await res.json();
      return new Error(data.message || `Request failed (${res.status})`);
    } catch {
      return new Error(`Request failed (${res.status})`);
    }
  }

  // ---------- jersey placeholder art ----------
  function hashHue(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) hash = (hash * 31 + str.charCodeAt(i)) >>> 0;
    return hash % 360;
  }

  function initials(name) {
    return (name || "?")
      .split(/\s+/)
      .filter(Boolean)
      .slice(0, 2)
      .map((w) => w[0].toUpperCase())
      .join("");
  }

  function jerseyArt(teamName, imageUrl) {
    const hue = hashHue(teamName || "team");
    const bg = `linear-gradient(155deg, hsl(${hue} 70% 22%), hsl(${(hue + 40) % 360} 65% 34%))`;
    const glyphBg = `linear-gradient(160deg, hsl(${hue} 85% 55%), hsl(${(hue + 40) % 360} 80% 45%))`;
    const glyphHtml = `<div class="jersey-glyph" style="background:${glyphBg}">${escapeHtml(initials(teamName))}</div>`;
    const imgHtml =
      imageUrl && /^https?:\/\//.test(imageUrl)
        ? `<img class="card-art-img" src="${escapeHtml(imageUrl)}" alt="${escapeHtml(teamName)} jersey" loading="lazy" />`
        : "";
    // The glyph always renders underneath; a real photo (if any) layers on top and
    // is removed on load failure via an attached listener (see wireCardArtFallbacks),
    // rather than an inline onerror string, which is fragile to escape correctly.
    return `<div class="card-art" style="background:${bg}">${glyphHtml}${imgHtml}</div>`;
  }

  function wireCardArtFallbacks(container) {
    container.querySelectorAll(".card-art-img").forEach((img) => {
      img.addEventListener("error", () => img.remove(), { once: true });
    });
  }

  // ---------- rendering: chips ----------
  function renderChips(container, items, selectedSet, getId, getLabel, onToggle) {
    if (!items || items.length === 0) {
      container.innerHTML = `<span class="placeholder-text">Nothing available.</span>`;
      return;
    }
    container.innerHTML = items
      .map((item) => {
        const id = getId(item);
        const active = selectedSet.has(id);
        return `<button type="button" class="chip${active ? " active" : ""}" data-id="${escapeHtml(id)}">${escapeHtml(getLabel(item))}</button>`;
      })
      .join("");
    container.querySelectorAll(".chip").forEach((btn) => {
      btn.addEventListener("click", () => {
        const id = btn.dataset.id;
        if (selectedSet.has(id)) selectedSet.delete(id);
        else selectedSet.add(id);
        btn.classList.toggle("active");
        onToggle && onToggle();
      });
    });
  }

  function renderSizeChips() {
    renderChips(
      el("sizeChips"),
      SIZE_OPTIONS,
      state.selectedSizes,
      (s) => s.value,
      (s) => s.label,
      null
    );
  }

  // ---------- data loading ----------
  async function loadCountries() {
    state.countries = await apiGet("/main/getCountryList");
    const sel = el("countrySelect");
    sel.innerHTML =
      `<option value="">All countries</option>` +
      state.countries
        .map((c) => `<option value="${escapeHtml(c.countryName)}">${escapeHtml(c.countryName)}</option>`)
        .join("");
  }

  async function loadSeasons() {
    state.seasons = await apiGet("/main/getAllSeasons");
    renderChips(
      el("seasonChips"),
      state.seasons,
      state.selectedSeasons,
      (s) => String(s.seasonCode),
      (s) => `${s.startYear}-${s.endYear}`,
      null
    );
  }

  async function loadLeaguesForCountry(country) {
    const chipsEl = el("leagueChips");
    if (!country) {
      state.leagues = [];
      state.selectedLeagues.clear();
      chipsEl.innerHTML = `<span class="placeholder-text">Select a country to see available leagues, or leave blank for all teams.</span>`;
      await loadTeamsForLeagues();
      return;
    }
    chipsEl.innerHTML = `<span class="placeholder-text">Loading leagues…</span>`;
    try {
      state.leagues = await apiGet(`/main/getLeagueByCountry?country=${encodeURIComponent(country)}`);
      state.selectedLeagues.clear();
      renderChips(
        chipsEl,
        state.leagues,
        state.selectedLeagues,
        (l) => String(l.leagueCode),
        (l) => l.leagueName,
        loadTeamsForLeagues
      );
      if (state.leagues.length === 0) {
        chipsEl.innerHTML = `<span class="placeholder-text">No leagues available for ${escapeHtml(country)}.</span>`;
      }
    } catch (e) {
      chipsEl.innerHTML = `<span class="placeholder-text">Could not load leagues.</span>`;
      toast(e.message, "error");
    }
    await loadTeamsForLeagues();
  }

  async function loadTeamsForLeagues() {
    const chipsEl = el("teamChips");
    chipsEl.innerHTML = `<span class="placeholder-text">Loading teams…</span>`;
    try {
      const leagueCodes = Array.from(state.selectedLeagues).map(Number);
      state.teams = await apiPost("/teams/getTeamsByLeagues", leagueCodes);
      state.selectedTeams.clear();
      renderChips(
        chipsEl,
        state.teams,
        state.selectedTeams,
        (t) => String(t.teamId),
        (t) => t.teamName,
        null
      );
      if (state.teams.length === 0) {
        chipsEl.innerHTML = `<span class="placeholder-text">No teams found.</span>`;
      }
    } catch (e) {
      chipsEl.innerHTML = `<span class="placeholder-text">Could not load teams.</span>`;
      toast(e.message, "error");
    }
  }

  function teamNameById(teamId) {
    const t = state.teams.find((t) => String(t.teamId) === String(teamId));
    return t ? t.teamName : null;
  }

  // ---------- search / results ----------
  function buildRequestDto() {
    return {
      teams: Array.from(state.selectedTeams).map(Number),
      seasons: Array.from(state.selectedSeasons).map(Number),
      size: Array.from(state.selectedSizes),
      page: state.page,
      records: RECORDS_PER_PAGE,
      sortBy: state.sortBy,
      sortByOrder: state.sortByOrder,
    };
  }

  async function search() {
    const grid = el("resultsGrid");
    const empty = el("emptyState");
    grid.innerHTML = `<p class="muted">Loading jerseys…</p>`;
    empty.hidden = true;
    try {
      const dto = buildRequestDto();
      const results = await apiPost("/teams/getJerseyView/grouped", dto);
      state.results = results || [];
      state.hasMore = state.results.length === RECORDS_PER_PAGE;
      renderResults();
    } catch (e) {
      grid.innerHTML = "";
      toast(e.message, "error");
    }
  }

  function renderResults() {
    const grid = el("resultsGrid");
    const empty = el("emptyState");
    el("pageIndicator").textContent = `Page ${state.page + 1}`;
    el("prevPageBtn").disabled = state.page === 0;
    el("nextPageBtn").disabled = !state.hasMore;

    if (state.results.length === 0) {
      grid.innerHTML = "";
      empty.hidden = false;
      return;
    }
    empty.hidden = true;
    grid.innerHTML = state.results
      .map((r, idx) => {
        const inStock = r.hasStock;
        return `
        <article class="card">
          ${jerseyArt(r.teamName, r.imageUrl)}
          <span class="stock-badge ${inStock ? "in" : "out"}">${inStock ? `In stock: ${r.stock}` : "Out of stock"}</span>
          <div class="card-body">
            <div class="card-team">${escapeHtml(r.teamName)}</div>
            <div class="card-meta">Season ${escapeHtml(r.season)}</div>
            <div class="card-actions">
              <button class="ghost-btn" data-action="details" data-idx="${idx}">View sizes</button>
              <button class="primary-btn" data-action="order" data-idx="${idx}" ${inStock ? "" : "disabled"}>Order</button>
            </div>
          </div>
        </article>`;
      })
      .join("");

    wireCardArtFallbacks(grid);
    grid.querySelectorAll('[data-action="details"]').forEach((btn) =>
      btn.addEventListener("click", () => openSizeDetail(state.results[Number(btn.dataset.idx)]))
    );
    grid.querySelectorAll('[data-action="order"]').forEach((btn) =>
      btn.addEventListener("click", () => openOrderModal(state.results[Number(btn.dataset.idx)]))
    );
  }

  // ---------- size detail modal ----------
  async function openSizeDetail(item) {
    const overlay = el("modalOverlay");
    const content = el("detailModalContent");
    content.innerHTML = `<h3>${escapeHtml(item.teamName)} &middot; ${escapeHtml(item.season)}</h3><p class="muted">Loading size breakdown…</p>`;
    overlay.hidden = false;
    try {
      const dto = {
        teams: [item.teamId],
        seasons: [item.seasonCode],
        size: [],
        page: 0,
        records: 50,
        sortBy: "teamName",
        sortByOrder: "ASC",
      };
      const jerseys = await apiPost("/teams/getJerseyView/single", dto);
      if (!jerseys || jerseys.length === 0) {
        content.innerHTML = `<h3>${escapeHtml(item.teamName)} &middot; ${escapeHtml(item.season)}</h3><p class="muted">No size-level stock records found.</p>`;
        return;
      }
      const rows = jerseys
        .map((j) => {
          const stock = j.stock ?? 0;
          return `
          <div class="size-row">
            <div style="display:flex;align-items:center;gap:12px;">
              <span class="size-tag">${escapeHtml(j.size)}</span>
              <span>${stock > 0 ? `${stock} in stock` : "Out of stock"}</span>
            </div>
            <button class="ghost-btn small" data-size="${escapeHtml(j.size)}" ${stock > 0 ? "" : "disabled"}>Order</button>
          </div>`;
        })
        .join("");
      content.innerHTML = `<h3>${escapeHtml(item.teamName)} &middot; ${escapeHtml(item.season)}</h3>${rows}`;
      content.querySelectorAll("[data-size]").forEach((btn) =>
        btn.addEventListener("click", () => {
          overlay.hidden = true;
          openOrderModal(item, btn.dataset.size);
        })
      );
    } catch (e) {
      content.innerHTML = `<h3>${escapeHtml(item.teamName)}</h3><p class="muted">Could not load size details.</p>`;
      toast(e.message, "error");
    }
  }

  // ---------- order modal ----------
  function openOrderModal(item, presetSize) {
    const overlay = el("orderModalOverlay");
    const content = el("orderModalContent");
    const sizeOptionsHtml = SIZE_OPTIONS.map(
      (s) => `<option value="${s.value}" ${s.value === presetSize ? "selected" : ""}>${s.label}</option>`
    ).join("");

    content.innerHTML = `
      <h3>Order ${escapeHtml(item.teamName)}</h3>
      <p class="muted">Season ${escapeHtml(item.season)}</p>
      <div class="filter-block" style="margin-bottom:16px;">
        <label class="filter-label">Size</label>
        <select id="orderSizeSelect" class="select-input">${sizeOptionsHtml}</select>
      </div>
      <div class="filter-block" style="margin-bottom:16px;">
        <label class="filter-label">Quantity</label>
        <input type="number" id="orderQtyInput" min="1" value="1" style="width:100%" />
      </div>
      <button class="primary-btn" id="confirmOrderBtn" style="width:100%;">Place Order</button>
      <div id="orderFeedback"></div>
    `;
    overlay.hidden = false;

    el("confirmOrderBtn").addEventListener("click", async () => {
      const size = el("orderSizeSelect").value;
      const quantity = Math.max(1, parseInt(el("orderQtyInput").value, 10) || 1);
      const feedback = el("orderFeedback");
      const confirmBtn = el("confirmOrderBtn");
      confirmBtn.disabled = true;
      confirmBtn.textContent = "Placing order…";
      try {
        const result = await apiPost(
          "/teams/order",
          { teamId: item.teamId, seasonCode: item.seasonCode, size, quantity },
          { userId: getUserId() }
        );
        feedback.innerHTML = `
          <div class="status-badge status-CREATED">Order ${escapeHtml(result?.status || "received")}</div>
          <p class="muted" style="margin-top:12px;">Your order has been sent for processing. Orders are confirmed asynchronously, so it may take a short while before it's ready to track by ID.</p>
        `;
        confirmBtn.hidden = true;
        toast("Order submitted successfully", "success");
      } catch (e) {
        feedback.innerHTML = `<div class="status-badge status-error">Order failed</div><p class="muted" style="margin-top:12px;">${escapeHtml(e.message)}</p>`;
        confirmBtn.disabled = false;
        confirmBtn.textContent = "Place Order";
      }
    });
  }

  // ---------- track order ----------
  async function trackOrder() {
    const orderId = el("trackOrderInput").value.trim();
    const resultEl = el("trackOrderResult");
    if (!orderId) {
      resultEl.innerHTML = `<p class="muted">Enter an order ID first.</p>`;
      return;
    }
    resultEl.innerHTML = `<p class="muted">Checking…</p>`;
    try {
      const data = await apiGet(`/teams/order/${encodeURIComponent(orderId)}`);
      resultEl.innerHTML = `
        <div class="status-badge status-${escapeHtml(data.status)}">${escapeHtml(data.status)}</div>
        <p class="muted" style="margin-top:10px;">Order ID: ${escapeHtml(data.orderID)}</p>
      `;
    } catch (e) {
      resultEl.innerHTML = `<div class="status-badge status-error">Not found</div><p class="muted" style="margin-top:10px;">${escapeHtml(e.message)}</p>`;
    }
  }

  // ---------- stock upload ----------
  async function uploadStock() {
    const fileInput = el("stockFile");
    if (!fileInput.files.length) {
      toast("Choose a CSV file first", "error");
      return;
    }
    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    try {
      const res = await fetch("/stock/update", { method: "POST", body: formData });
      const data = await res.json();
      if (!res.ok) throw new Error(data.message || "Upload failed");
      toast(`Stock upload: ${data.status || "done"}`, "success");
    } catch (e) {
      toast(e.message, "error");
    }
  }

  // ---------- wiring ----------
  function wireEvents() {
    el("countrySelect").addEventListener("change", (e) => {
      state.selectedCountry = e.target.value;
      loadLeaguesForCountry(state.selectedCountry);
    });

    el("sortBySelect").addEventListener("change", (e) => (state.sortBy = e.target.value));
    el("sortOrderSelect").addEventListener("change", (e) => (state.sortByOrder = e.target.value));

    el("searchBtn").addEventListener("click", () => {
      state.page = 0;
      search();
    });

    el("resetBtn").addEventListener("click", async () => {
      state.selectedCountry = "";
      state.selectedLeagues.clear();
      state.selectedTeams.clear();
      state.selectedSeasons.clear();
      state.selectedSizes.clear();
      state.sortBy = "teamName";
      state.sortByOrder = "ASC";
      state.page = 0;
      el("countrySelect").value = "";
      el("sortBySelect").value = "teamName";
      el("sortOrderSelect").value = "ASC";
      await loadLeaguesForCountry("");
      renderChips(el("seasonChips"), state.seasons, state.selectedSeasons, (s) => String(s.seasonCode), (s) => `${s.startYear}-${s.endYear}`, null);
      renderSizeChips();
      search();
    });

    el("prevPageBtn").addEventListener("click", () => {
      if (state.page > 0) {
        state.page -= 1;
        search();
      }
    });
    el("nextPageBtn").addEventListener("click", () => {
      if (state.hasMore) {
        state.page += 1;
        search();
      }
    });

    el("adminToggle").addEventListener("click", () => {
      const body = el("adminBody");
      body.hidden = !body.hidden;
    });
    el("uploadStockBtn").addEventListener("click", uploadStock);

    el("openTrackBtn").addEventListener("click", () => (el("trackModalOverlay").hidden = false));
    el("closeTrackModal").addEventListener("click", () => (el("trackModalOverlay").hidden = true));
    el("trackOrderBtn").addEventListener("click", trackOrder);

    el("closeDetailModal").addEventListener("click", () => (el("modalOverlay").hidden = true));
    el("closeOrderModal").addEventListener("click", () => (el("orderModalOverlay").hidden = true));

    [el("modalOverlay"), el("orderModalOverlay"), el("trackModalOverlay")].forEach((overlay) => {
      overlay.addEventListener("click", (e) => {
        if (e.target === overlay) overlay.hidden = true;
      });
    });

    const userIdInput = el("userIdInput");
    userIdInput.value = getUserId();
    userIdInput.addEventListener("change", () => setUserId(userIdInput.value.trim()));
  }

  async function init() {
    wireEvents();
    renderSizeChips();
    try {
      await Promise.all([loadCountries(), loadSeasons(), loadTeamsForLeagues()]);
    } catch (e) {
      toast("Could not reach the Jersey House API. Is the backend running?", "error");
    }
    search();
  }

  document.addEventListener("DOMContentLoaded", init);
})();
