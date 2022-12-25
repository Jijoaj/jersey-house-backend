package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.aspect.TrackExecutionTime;
import com.jijo.jerseyhouse.dto.JerseyViewDto;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.dto.JerseyRequestDto;
import com.jijo.jerseyhouse.repository.JerseyRepository;
import com.jijo.jerseyhouse.repository.TeamsRepository;
import com.jijo.jerseyhouse.service.ProductService;
import com.jijo.jerseyhouse.transformer.JerseyTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = {"jersey-house-cache"})
public class ProductServiceImpl implements ProductService {

    EntityManager em;

    TeamsRepository teamsRepository;

    JerseyRepository jerseyRepository;

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
     * @param jerseyRequestDto
     * @return
     */
    @Override
    @TrackExecutionTime
    @Cacheable(key = "#jerseyRequestDto", unless = "#result==null")
    public List<Jersey> getJerseyView(JerseyRequestDto jerseyRequestDto) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Jersey> queryJersey = criteriaBuilder.createQuery(Jersey.class);
        Root<Jersey> jerseyRoot = queryJersey.from(Jersey.class);
        Predicate finalPredicate = getFinalPredicate(jerseyRequestDto, criteriaBuilder, jerseyRoot);
        TypedQuery<Jersey> query = em.createQuery(queryJersey.where(finalPredicate));
        return query.getResultList();
    }

    private static Predicate getFinalPredicate(JerseyRequestDto jerseyRequestDto, CriteriaBuilder criteriaBuilder, Root<Jersey> jerseyRoot) {
        List<Predicate> predicateList = new ArrayList<>();
        if (!jerseyRequestDto.getSeasons().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("seasonCode").get("seasonCode"))
                    .value(jerseyRequestDto.getSeasons()));
        }
        if (!jerseyRequestDto.getTeams().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("teamCode").get("teamId"))
                    .value(jerseyRequestDto.getTeams()));
        }
        if (!jerseyRequestDto.getSize().isEmpty()) {
            predicateList.add(criteriaBuilder.in(jerseyRoot.get("size"))
                    .value(jerseyRequestDto.getSize()));
        }
        return criteriaBuilder.and(predicateList.toArray(Predicate[]::new));
    }

    /**
     * @param jerseyRequestDto JerseyRequest
     * @return list of all jerseys satisfying filters grouped by Team, size and seasons
     */
    @Override
    @Cacheable(key = "#jerseyRequestDto+#root.methodName", unless = "#result==null")
    public List<JerseyViewDto> getJerseyViewGrouped(JerseyRequestDto jerseyRequestDto) {
        prepareJerseyRequest(jerseyRequestDto);
        Sort sortBy = getSortBy(jerseyRequestDto);
        Pageable pageable = PageRequest.of(jerseyRequestDto.getPage(), jerseyRequestDto.getRecords(), sortBy);
        List<Object[]> jerseyViewResultFromDB = jerseyRepository.findJerseyView(jerseyRequestDto, pageable);
        return jerseyViewResultFromDB.stream()
                .map(JerseyTransformer::toJerseyViewDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static Sort getSortBy(JerseyRequestDto jerseyRequestDto) {
        return jerseyRequestDto.getSortByOrder().equals("ASC")
                ? Sort.by(jerseyRequestDto.getSortBy().value)
                : Sort.by(jerseyRequestDto.getSortBy().value).descending();
    }

    private static void prepareJerseyRequest(JerseyRequestDto jerseyRequestDto) {
        jerseyRequestDto.setSeasons(jerseyRequestDto.getSeasons().isEmpty() ? null : jerseyRequestDto.getSeasons());
        jerseyRequestDto.setSize(jerseyRequestDto.getSize().isEmpty() ? null : jerseyRequestDto.getSize());
        jerseyRequestDto.setTeams(jerseyRequestDto.getTeams().isEmpty() ? null : jerseyRequestDto.getTeams());
    }
}
