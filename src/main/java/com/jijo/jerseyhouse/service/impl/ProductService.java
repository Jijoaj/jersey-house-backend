package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.aspect.TrackExecutionTime;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.repository.TeamsRepository;
import com.jijo.jerseyhouse.service.ProductServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements ProductServiceInterface {
    @Autowired
    TeamsRepository teamsRepository;

    /**
     * @param leagueCodeList
     * @return List of Teams that have the specified leagueCodeList
     */
    @Override
    @TrackExecutionTime
    public List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList) {
        if(leagueCodeList.size() == 0) {
            log.info("No Specified leagues selected. returning all teams available");
            return  teamsRepository.findAllByOrderByTeamName();
        }
        else {
            log.info("Number of leagues selected: " + leagueCodeList.size());
            return teamsRepository.findByLeagueCode(leagueCodeList);
        }
    }
}
