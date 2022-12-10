package com.jijo.jerseyhouse.controller.impl;

import com.jijo.jerseyhouse.controller.TeamsControllerInterface;
import com.jijo.jerseyhouse.dto.JerseyOrderDto;
import com.jijo.jerseyhouse.dto.JerseyViewDto;
import com.jijo.jerseyhouse.model.Jersey;
import com.jijo.jerseyhouse.model.Teams;
import com.jijo.jerseyhouse.dto.JerseyRequestDto;
import com.jijo.jerseyhouse.service.OrderService;
import com.jijo.jerseyhouse.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TeamsController implements TeamsControllerInterface {

    private final ProductService productService;
    private final OrderService orderService;

    public TeamsController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
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
     * @param jerseyRequestDto
     * @return list of all jerseys satisfying filters
     */
    @Override
    public ResponseEntity<List<Jersey>> getJersey(JerseyRequestDto jerseyRequestDto) {
        return new ResponseEntity<>(productService.getJerseyView(jerseyRequestDto), HttpStatus.OK);
    }

    /**
     * @param jerseyRequestDto
     * @return list of all jerseys satisfying filters grouped by Team, size and seasons
     */
    @Override
    public ResponseEntity<List<JerseyViewDto>> getJerseyGroupedBySize(JerseyRequestDto jerseyRequestDto) {
        return new ResponseEntity<>(productService.getJerseyViewGrouped(jerseyRequestDto), HttpStatus.OK);
    }

    /**
     * @param JerseyOrderDto
     * @param userId
     * @return
     */
    @Override
    public ResponseEntity<Map<String, String>> postJerseyOrder(JerseyOrderDto JerseyOrderDto, String userId) {
        return new ResponseEntity<>(orderService.orderJersey(JerseyOrderDto, userId), HttpStatus.OK);
    }
}
