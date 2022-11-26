package com.jijo.jerseyhouse.repository;

import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.dto.JerseyRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JerseyRepository extends JpaRepository<Jersey, Integer> {
    @Query("select j.teamCode.teamId , j.teamCode.teamName, j.seasonCode.seasonCode, j.seasonCode.startYear, j.seasonCode.endYear, " +
            "sum(j.stock), coalesce(j.imageUrl, '') " +
            "from Jersey j " +
            "where ((?#{#jerseyRequest.teams}) is null or j.teamCode.teamId in (?#{#jerseyRequest.teams})) " +
            "and ((?#{#jerseyRequest.size}) is null or j.size in (?#{#jerseyRequest.size})) " +
            "and ((?#{#jerseyRequest.seasons}) is null or j.seasonCode.seasonCode in (?#{#jerseyRequest.seasons})) " +
            "group by j.seasonCode, j.teamCode.teamId, j.teamCode.teamName " +
            "order by j.teamCode.teamId")
    List<Object[]> findJerseyView(@Param("jerseyRequest") JerseyRequestDto jerseyRequestDto);
}