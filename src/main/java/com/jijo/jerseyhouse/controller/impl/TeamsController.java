package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.TeamsControllerInterface;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.model.requests.JerseyRequest;
import com.jijo.jerseyhouse.service.ProductServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamsController implements TeamsControllerInterface {

    private final ProductServiceInterface productService;

    public TeamsController(ProductServiceInterface productService) {
        this.productService = productService;
    }

    /**
     * @param leagueCodeList
     * @return
     */
    @Override
    public ResponseEntity<List<Teams>> getTeamsByLeagues(List<Integer> leagueCodeList) {
        return new ResponseEntity<>(productService.getTeamsByLeagues(leagueCodeList), HttpStatus.OK);
    }

    /**
     * @param jerseyRequest
     * @return list of all jerseys satisfying filters
     */
    @Override
    public ResponseEntity<List<Jersey>> getJersey(JerseyRequest jerseyRequest) {
        return new ResponseEntity<>(productService.getJerseyView(jerseyRequest), HttpStatus.OK);
    }

    /**
     * @param jerseyRequest
     * @return list of all jerseys satisfying filters grouped by Team, size and seasons
     */
    @Override
    public ResponseEntity<List<Jersey>> getJerseyGroupedBySize(JerseyRequest jerseyRequest) {
        return new ResponseEntity<>(productService.getJerseyViewGrouped(jerseyRequest), HttpStatus.OK);;
    }
}
