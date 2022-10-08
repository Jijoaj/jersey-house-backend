package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.repository.CountryRepository;
import com.jijo.jerseyhouse.service.DeliveryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService implements DeliveryServiceInterface {

    @Autowired
    CountryRepository countryRepository;

    /**
     * method getCountryList
     * @return list of Country
     */
    @Override
    public List<Country> getCountryList() {
        return countryRepository.findAllByOrderByCountryName();
    }
}
