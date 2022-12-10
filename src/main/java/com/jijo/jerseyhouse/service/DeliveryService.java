package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.Season;

import java.util.List;

public interface DeliveryService {
    List<Country> getCountryList();

    List<League> getLeagueByCountry(String country);

    List<Season> getAllSeasons();
}
