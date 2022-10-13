package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.LeagueAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueAvailabilityRepository extends JpaRepository<LeagueAvailability, Integer> {
}