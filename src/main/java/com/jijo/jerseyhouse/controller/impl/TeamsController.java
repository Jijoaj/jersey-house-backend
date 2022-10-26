package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.TeamsControllerInterface;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamsController implements TeamsControllerInterface {

    @Autowired
    ProductServiceInterface productService;

    /**
     * @param leagueCodeList
     * @return
     */
    @Override
    public ResponseEntity<List<Teams>> getTeamsByLeagues(List<Integer> leagueCodeList) {
        return new ResponseEntity<>(productService.getTeamsByLeagues(leagueCodeList), HttpStatus.OK);
    }
}