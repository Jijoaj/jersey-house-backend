package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.dto.JerseyViewDto;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.dto.JerseyRequestDto;

import java.util.List;
public interface ProductServiceInterface {
    List<Teams> getTeamsByLeagues(List<Integer> leagueCodeList);

    List<Jersey> getJerseyView(JerseyRequestDto jerseyRequestDto);

    List<JerseyViewDto> getJerseyViewGrouped(JerseyRequestDto jerseyRequestDto);
}
