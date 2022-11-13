package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.aspect.TrackExecutionTime;
import com.jijo.jerseyhouse.model.Country;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.Season;
import com.jijo.jerseyhouse.repository.CountryRepository;
import com.jijo.jerseyhouse.repository.LeagueAvailabilityRepository;
import com.jijo.jerseyhouse.repository.SeasonRepository;
import com.jijo.jerseyhouse.service.DeliveryServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DeliveryService implements DeliveryServiceInterface {

    CountryRepository countryRepository;

    LeagueAvailabilityRepository leagueAvailabilityRepository;

    SeasonRepository seasonRepository;

    /**
     * method getCountryList
     *
     * @return list of Country
     */
    @Override
    @TrackExecutionTime
    @Cacheable(cacheNames = {"jersey-house-cache"}, key = "{#root.methodName}")
    public List<Country> getCountryList() {
        return countryRepository.findAllByOrderByCountryName();
    }

    /**
     * method getLeagueByCountry
     *
     * @param country country
     * @return List of Country that is available in the country
     */
    @Override
    @TrackExecutionTime
    @Cacheable(value = "jersey-house-cache", key = "#country", unless = "#result==null")
    public List<League> getLeagueByCountry(String country) {
        log.info("getCountryList :: fetching available leagues for country : " + country);
        return leagueAvailabilityRepository.getLeagueAvailableForCountry(country);
    }

    /**
     * @return
     */
    @Override
    @TrackExecutionTime
    @Cacheable(cacheNames = {"jersey-house-cache"}, key = "{#root.methodName}")
    public List<Season> getAllSeasons() {
        return seasonRepository.findAllByOrderByStartYearDesc();
    }
}
