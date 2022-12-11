package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.dto.JerseyOrderDto;
import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.mq.publisher.JerseyOrderPublisher;
import com.jijo.jerseyhouse.service.OrderService;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final JerseyOrderPublisher jerseyOrderPublisher;

    public OrderServiceImpl(JerseyOrderPublisher jerseyOrderPublisher) {
        this.jerseyOrderPublisher = jerseyOrderPublisher;
    }

    /**
     * @param jerseyOrderDto
     * @param userId
     * @return status
     */
    @Override
    public Map<String, String> orderJersey(JerseyOrderDto jerseyOrderDto, String userId) throws CommonInternalException {
        // TODO send order to rabbitMQ
        try {
            JerseyOrderPlacementDto newOrder = new JerseyOrderPlacementDto(jerseyOrderDto, userId);
            return jerseyOrderPublisher.publishOrder(newOrder);
        }catch (Exception e) {
            log.error("Error while creating order for user {} , time {}, error : {}", userId, Calendar.getInstance().getTime(), e);
            throw new CommonInternalException("order creation failed");
        }
    }
}
