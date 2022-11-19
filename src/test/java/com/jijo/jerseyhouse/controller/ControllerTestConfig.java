package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.model.League;

import java.util.ArrayList;
import java.util.List;

public class ControllerTestConfig {
    public static List<League> getLeagueList() {
        League testLeague = new League(1, "SAMPLE League");
        List<League> leagueList = new ArrayList<>();
        leagueList.add(testLeague);
        return leagueList;
    }
}
