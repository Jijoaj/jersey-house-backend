package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.model.Teams;

import java.util.List;
public interface ProductServiceInterface {
    List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList);
}
