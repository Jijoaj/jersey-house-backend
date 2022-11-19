package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("teams")
public interface TeamsControllerInterface {

    @PostMapping("getTeamsByLeagues")
    ResponseEntity<List<Teams>> getTeamsByLeagues(@RequestBody List<Integer> leagueCodeList);

    @PostMapping("getJerseyView")
    ResponseEntity<List<Jersey>> getJersey(@RequestBody JerseyRequest jerseyRequest);
}
