package com.jijo.jerseyhouse.scheduler.order;

import com.jijo.jerseyhouse.model.OrderPlacement;
import com.jijo.jerseyhouse.model.enums.OrderStatus;
import com.jijo.jerseyhouse.repository.OrderPlacementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

@Component
@EnableScheduling
@Slf4j
public class OrderConfirmationScheduler {

    private final OrderPlacementRepository orderPlacementRepository;

    public OrderConfirmationScheduler(OrderPlacementRepository orderPlacementRepository) {
        this.orderPlacementRepository = orderPlacementRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void scheduleOrderConfirmation(){
        Optional<OrderPlacement> orderPlacement = orderPlacementRepository.findFirstByOrderStatusOrderByOrderPlacedDate(OrderStatus.CREATED.orderStatus);
        if(orderPlacement.isPresent()) {
            OrderPlacement order = orderPlacement.get();
            String orderId = order.getOrderId();
            log.info("Confirming order : {} time {}", orderId, Calendar.getInstance().getTime());
            order.setOrderStatus(OrderStatus.CONFIRMED.orderStatus);
            orderPlacementRepository.save(order);
            log.info("order confirmed for order : [ orderID: {} ]", orderId);
        }
    }

    @Scheduled(cron = "0 30 * * * *")
    public void scheduleOrderProcessing(){
        Optional<OrderPlacement> orderPlacement = orderPlacementRepository.findFirstByOrderStatusOrderByOrderPlacedDate(OrderStatus.CONFIRMED.orderStatus);
        if(orderPlacement.isPresent()) {
            OrderPlacement order = orderPlacement.get();
            String orderId = order.getOrderId();
            log.info("Processing orderPlacement : {} time {}", orderId, Calendar.getInstance().getTime());
            order.setOrderStatus(OrderStatus.IN_PROGRESS.orderStatus);
            orderPlacementRepository.save(order);
            log.info("orderPlacement In-progress for orderPlacement : [ orderID: {} ]", orderId);
        }
    }

    @Scheduled(cron = "0 45 * * * *")
    public void scheduleOrderCompletion(){
        Optional<OrderPlacement> orderPlacement = orderPlacementRepository.findFirstByOrderStatusOrderByOrderPlacedDate(OrderStatus.IN_PROGRESS.orderStatus);
        if(orderPlacement.isPresent()) {
            OrderPlacement order = orderPlacement.get();
            String orderId = order.getOrderId();
            log.info("Completing order : {} time {}", orderId, Calendar.getInstance().getTime());
            order.setOrderStatus(OrderStatus.COMPLETED.orderStatus);
            orderPlacementRepository.save(order);
            log.info("order completed for order : [ orderID: {} ]", orderId);
        }
    }

}
