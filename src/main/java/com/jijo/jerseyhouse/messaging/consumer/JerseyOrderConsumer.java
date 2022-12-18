package com.jijo.jerseyhouse.messaging.consumer;

import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.repository.OrderPlacementRepository;
import com.jijo.jerseyhouse.transformer.OrderTransformer;
import com.jijo.jerseyhouse.utilities.constants.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.jijo.jerseyhouse.utilities.constants.MessageConstants.ERROR_WHILE_SAVING_ORDER;
import static com.jijo.jerseyhouse.utilities.constants.MessageConstants.ORDER_SAVED;

@Component
@Slf4j
public class JerseyOrderConsumer {


    final OrderPlacementRepository orderPlacementRepository;

    public JerseyOrderConsumer(OrderPlacementRepository orderPlacementRepository) {
        this.orderPlacementRepository = orderPlacementRepository;
    }

    @RabbitListener(queues = QueueConstants.orderQueueName)
    public void consumeJerseyOrderFromQueue(JerseyOrderPlacementDto jerseyOrderPlacementDto) throws InterruptedException, CommonInternalException {
        log.info("Order received : {}", jerseyOrderPlacementDto);
        Thread.sleep(5000);
        try {
            orderPlacementRepository.save(OrderTransformer.createOrder(jerseyOrderPlacementDto));
        }catch (Exception e) {
            throw new CommonInternalException(ERROR_WHILE_SAVING_ORDER);
        }
        log.info(ORDER_SAVED);
    }
}
