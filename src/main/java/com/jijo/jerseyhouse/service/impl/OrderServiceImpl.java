package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.dto.JerseyOrderDto;
import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.service.OrderService;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    /**
     * @param jerseyOrderDto
     * @param userId
     * @return status
     */
    @Override
    public Map<String, String> orderJersey(JerseyOrderDto jerseyOrderDto, String userId) {
        // TODO send order to rabbitMQ
        try {
            JerseyOrderPlacementDto newOrder = new JerseyOrderPlacementDto(jerseyOrderDto, userId);
        }catch (Exception e) {
            return CommonMethods.getFailureMapResponse();
        }
        return CommonMethods.getPendingMapResponse();
    }
}
