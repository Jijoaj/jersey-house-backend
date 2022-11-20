package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Integer> {
    @Query("select j " +
            "from Jersey j " +
            "where j.teamCode in ?#{#jerseyRequest.teams} " +
            "and j.size in ?#{#jerseyRequest.size} " +
            "and j.seasonCode in ?#{#jerseyRequest.seasons} " +
            "group by j.seasonCode, j.teamCode " +
            "order by j.teamCode.teamId")
    List<Jersey> findJerseyView(@Param("jerseyRequest") JerseyRequest jerseyRequest);
}