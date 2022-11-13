package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.aspect.TrackExecutionTime;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;
import com.jijo.jerseyhouse.repository.TeamsRepository;
import com.jijo.jerseyhouse.service.ProductServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = {"jersey-house-cache"})
public class ProductService implements ProductServiceInterface {

    EntityManager em;

    TeamsRepository teamsRepository;

    /**
     * @param leagueCodeList
     * @return List of Teams that have the specified leagueCodeList
     */
    @Override
    @TrackExecutionTime
    @Cacheable(key = "#leagueCodeList")
    public List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList) {
        if (leagueCodeList.isEmpty()) {
            log.info("No Specified leagues selected. returning all teams available");
            return teamsRepository.findAllByOrderByTeamName();
        } else {
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
    @Cacheable(key = "#jerseyRequest", unless = "#result==null")
    public List<Jersey> getJerseyView(JerseyRequest jerseyRequest) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Jersey> queryJersey = criteriaBuilder.createQuery(Jersey.class);
        Root<Jersey> jerseyRoot = queryJersey.from(Jersey.class);
        List<Predicate> predicateList = new ArrayList<>();
        if (!jerseyRequest.getSeasons().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("seasonCode").get("seasonCode"))
                    .value(jerseyRequest.getSeasons()));
        }
        if (!jerseyRequest.getTeams().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("teamCode").get("teamId"))
                    .value(jerseyRequest.getTeams()));
        }
        if (!jerseyRequest.getSize().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("size"))
                    .value(jerseyRequest.getSize()));
        }
        Predicate finalPredicate = criteriaBuilder.and(predicateList.toArray(Predicate[]::new));
        TypedQuery<Jersey> query = em.createQuery(queryJersey.where(finalPredicate));
        return query.getResultList();
    }
}
