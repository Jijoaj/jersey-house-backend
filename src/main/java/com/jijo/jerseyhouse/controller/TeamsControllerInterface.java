package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.model.Teams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("teams")
public interface TeamsControllerInterface {

    @GetMapping("getTeamsByLeagues")
    ResponseEntity<List<Teams>> getTeamsByLeagues(@RequestBody List<Integer> leagueCodeList);
}
