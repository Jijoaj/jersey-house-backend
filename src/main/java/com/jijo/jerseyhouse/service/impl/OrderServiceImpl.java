package com.jijo.jerseyhouse.service.impl;

import com.jijo.jerseyhouse.dto.JerseyOrderDto;
import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.dto.OrderStatusDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.messaging.publisher.JerseyOrderPublisher;
import com.jijo.jerseyhouse.model.OrderPlacement;
import com.jijo.jerseyhouse.model.enums.OrderStatus;
import com.jijo.jerseyhouse.repository.OrderPlacementRepository;
import com.jijo.jerseyhouse.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final JerseyOrderPublisher jerseyOrderPublisher;
    private final OrderPlacementRepository orderPlacementRepository;

    public OrderServiceImpl(JerseyOrderPublisher jerseyOrderPublisher, OrderPlacementRepository orderPlacementRepository) {
        this.jerseyOrderPublisher = jerseyOrderPublisher;
        this.orderPlacementRepository = orderPlacementRepository;
    }

    /**
     * @param jerseyOrderDto
     * @param userId
     * @return status
     */
    @Override
    public Map<String, String> orderJersey(JerseyOrderDto jerseyOrderDto, String userId) throws CommonInternalException {
        try {
            JerseyOrderPlacementDto newOrder = new JerseyOrderPlacementDto(jerseyOrderDto, userId);
            return jerseyOrderPublisher.publishOrder(newOrder);
        }catch (Exception e) {
            log.error("Error while creating order for user {} , time {}, error : {}", userId, Calendar.getInstance().getTime(), e);
            throw new CommonInternalException("order creation failed");
        }
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public OrderStatusDto findOrder(String orderId) throws CommonInternalException {
        Optional<OrderPlacement> orderById = orderPlacementRepository.findById(orderId);
        if (orderById.isPresent()) {
            OrderPlacement orderPlacement = orderById.get();
            OrderStatusDto orderStatusDto = new OrderStatusDto();
            orderStatusDto.setOrderID(orderPlacement.getOrderId());
            orderStatusDto.setStatus(OrderStatus.valueOf(orderPlacement.getOrderStatus()));
            return orderStatusDto;
        } else {
            throw new CommonInternalException("order with order ID : "+ orderId +"not found");
        }

    }
}
