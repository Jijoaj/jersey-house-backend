package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Integer> {

    @Query("select t from Teams t " +
            "where t.leagueCode in ?1 " +
            "Order by teamName ASC")
    List<Teams> findByLeagueCode(List<Integer> leagueCode);

    List<Teams> findAllByOrderByTeamName();
}