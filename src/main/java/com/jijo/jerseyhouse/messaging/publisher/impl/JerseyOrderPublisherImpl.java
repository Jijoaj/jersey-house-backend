package com.jijo.jerseyhouse.messaging.publisher.impl;

import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;
import com.jijo.jerseyhouse.messaging.publisher.JerseyOrderPublisher;
import com.jijo.jerseyhouse.utilities.CommonMethods;
import com.jijo.jerseyhouse.utilities.constants.QueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class JerseyOrderPublisherImpl implements JerseyOrderPublisher {
    private final RabbitTemplate rabbitTemplate;

    public JerseyOrderPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * @param jerseyOrderPlacementDto
     * @return status
     */
    @Override
    public Map<String, String> publishOrder(JerseyOrderPlacementDto jerseyOrderPlacementDto) throws CommonInternalException {
        try {
            rabbitTemplate.convertAndSend(QueueConstants.orderExchangeName, QueueConstants.orderRoutingKey, jerseyOrderPlacementDto);
            return CommonMethods.getPendingMapResponse();
        }catch (Exception e) {
            log.error("Failed to send order to queue", e);
            throw new CommonInternalException("Error while sending order to queue");
        }
    }
}
