package com.jijo.jerseyhouse.mq.consumer;

import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.utilities.constants.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JerseyOrderConsumer {
    @RabbitListener(queues = QueueConstants.orderQueueName)
    public void consumeJerseyOrderFromQueue(JerseyOrderPlacementDto jerseyOrderPlacementDto) throws InterruptedException {
        log.info("Order received : {}", jerseyOrderPlacementDto);
        Thread.sleep(5000);
        // TODO save order
        log.info("Order saved");
    }
}
