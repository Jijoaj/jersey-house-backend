package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.LeagueAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueAvailabilityRepository extends JpaRepository<LeagueAvailability, Integer> {

    /* Query to find the available leagues in the specified country */
    @Query("Select l from LeagueAvailability la " +
            "join League l on l.leagueCode = la.leagueCode " +
            "join Country c on c.countryCode = la.countryCode " +
            "where c.countryName = ?#{#country} " +
            "order by l.leagueName")
    List<League> getLeagueAvailableForCountry(@Param("country") String country);
}