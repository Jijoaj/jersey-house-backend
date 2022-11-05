package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.aspect.TrackExecutionTime;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;
import com.jijo.jerseyhouse.repository.TeamsRepository;
import com.jijo.jerseyhouse.service.ProductServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService implements ProductServiceInterface {

    @Autowired
    EntityManager em;

    @Autowired
    TeamsRepository teamsRepository;

    /**
     * @param leagueCodeList
     * @return List of Teams that have the specified leagueCodeList
     */
    @Override
    @TrackExecutionTime
    public List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList) {
        if(leagueCodeList.isEmpty()) {
            log.info("No Specified leagues selected. returning all teams available");
            return  teamsRepository.findAllByOrderByTeamName();
        }
        else {
            log.info("Number of leagues selected: " + leagueCodeList.size());
            return teamsRepository.findByLeagueCode(leagueCodeList);
        }
    }

    /**
     * @param jerseyRequest
     * @return
     */
    @Override
    @TrackExecutionTime
    public List<Jersey> getJerseyView(JerseyRequest jerseyRequest) {
        CriteriaBuilder criteriaBuilder= em.getCriteriaBuilder();
        CriteriaQuery<Jersey> queryJersey = criteriaBuilder.createQuery(Jersey.class);
        Root<Jersey> jerseyRoot = queryJersey.from(Jersey.class);
        List<Predicate> predicates= new ArrayList<>();
        if(!jerseyRequest.getSeasons().isEmpty()){
            queryJersey.where(jerseyRoot.get("season_code").in(jerseyRequest.getSeasons()));
        }
        TypedQuery<Jersey> query = em.createQuery(queryJersey);
        return query.getResultList();
    }
}
