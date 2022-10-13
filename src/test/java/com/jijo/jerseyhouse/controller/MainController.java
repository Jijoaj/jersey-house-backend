package com.jijo.jerseyhouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jijo.jerseyhouse.model.Country;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("junit")
public class MainController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Sql("classpath:/junit_db_scripts/INSERT_TEST_DATA.sql")
    public void shouldFetchAllCountries() throws Exception {
        List<Country> countryList = getCountryList();
        String expectedResult = objectMapper.writeValueAsString(countryList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/main/getCountryList")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(expectedResult));
    }

    private List<Country> getCountryList(){
        Country testCountry= new Country(1,"SAMPLE_COUNTRY");
        List<Country> countryList= new ArrayList<>();
        countryList.add(testCountry);
        return  countryList;
    }
}
