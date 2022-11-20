package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;

import java.util.List;
public interface ProductServiceInterface {
    List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList);

    List<Jersey> getJerseyView(JerseyRequest jerseyRequest);

    List<Jersey> getJerseyViewGrouped(JerseyRequest jerseyRequest);
}
