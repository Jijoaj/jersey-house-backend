package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;

import java.util.List;

public interface DeliveryServiceInterface {
    List<Country> getCountryList();

    List<League> getLeagueByCountry(String country);
}
