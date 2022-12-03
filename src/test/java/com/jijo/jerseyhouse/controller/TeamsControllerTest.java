package com.jijo.jerseyhouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jijo.jerseyhouse.dto.JerseyRequestDto;
import com.jijo.jerseyhouse.model.League;
import com.jijo.jerseyhouse.model.enums.JerseyViewSortBy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.jijo.jerseyhouse.controller.ControllerTestConfig.getLeagueList;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("junit")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class TeamsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void testGetTeamsByLeagues() throws Exception {
        List<Integer> leagueList = getLeagueNameList();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/getTeamsByLeagues")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(leagueList))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    void testGetTeams() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/getTeamsByLeagues")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.emptyList()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @Order(3)
    void testGetTeamsForNonLeague() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/getTeamsByLeagues")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Collections.singleton("2")))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("[]"));
    }

    private static List<Integer> getLeagueNameList() {
        return getLeagueList().stream()
                .map(League::getLeagueCode)
                .collect(Collectors.toList());
    }

    @Test
    @Order(4)
    void testGetJersey() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/getJerseyView/single")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getJerseyRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    JerseyRequestDto getJerseyRequestDto(){
        return JerseyRequestDto.builder()
                .Teams(Collections.emptyList())
                .seasons(Collections.emptyList())
                .size(Collections.emptyList())
                .page(0)
                .records(1)
                .sortBy(JerseyViewSortBy.teamName)
                .sortByOrder("ASC")
                .build();
    }

    @Test
    @Order(5)
    void testGetJerseyGrouped() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/teams/getJerseyView/grouped")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getJerseyRequestDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
