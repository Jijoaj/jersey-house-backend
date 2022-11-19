package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.MainControllerInterface;
import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.Season;
import com.jijo.jerseyhouse.service.DeliveryServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController implements MainControllerInterface {

    private final DeliveryServiceInterface deliveryService;

    public MainController(DeliveryServiceInterface deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public ResponseEntity<String> connect() {
        return new ResponseEntity<>("Successfully connected to Jersey house !",HttpStatus.OK);
    }

    /**
     * method getCountryList
     * @return List of countries
     */
    @Override
    public ResponseEntity<List<Country>> getCountryList() {
        return new ResponseEntity<>(deliveryService.getCountryList(),HttpStatus.OK);
    }

    /**
     * @param country country
     * @return list of league available in the country
     */
    @Override
    public ResponseEntity<List<League>> getLeagueByCountry(String country) {
        return new ResponseEntity<>(deliveryService.getLeagueByCountry(country),HttpStatus.OK);
    }

    /**
     * method getAllSeasons
     * @return list of seasons
     */
    @Override
    public ResponseEntity<List<Season>> getAllSeasons() {
        return new ResponseEntity<>(deliveryService.getAllSeasons(),HttpStatus.OK);
    }
}
