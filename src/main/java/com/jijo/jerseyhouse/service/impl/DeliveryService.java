package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.repository.CountryRepository;
import com.jijo.jerseyhouse.repository.LeagueAvailabilityRepository;
import com.jijo.jerseyhouse.service.DeliveryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService implements DeliveryServiceInterface {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    LeagueAvailabilityRepository leagueAvailabilityRepository;

    /**
     * method getCountryList
     *
     * @return list of Country
     */
    @Override
    public List<Country> getCountryList() {
        return countryRepository.findAllByOrderByCountryName();
    }

    /**
     * method getLeagueByCountry
     *
     * @param country
     * @return List of Country that is available in the country
     */
    @Override
    public List<League> getLeagueByCountry(String country) {
        return leagueAvailabilityRepository.getLeagueAvailableForCountry(country);
    }
}
