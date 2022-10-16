package com.jijo.jerseyhouse.controller;

import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.Season;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("main")
public interface MainControllerInterface {

    @GetMapping("connect")
    ResponseEntity<String> connect();

    @GetMapping("getCountryList")
    ResponseEntity<List<Country>> getCountryList();

    @GetMapping("getLeagueByCountry")
    ResponseEntity<List<League>> getLeagueByCountry(@RequestParam String country);

    @GetMapping("getAllSeasons")
    ResponseEntity<List<Season>> getAllSeasons();
}
