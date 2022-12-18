package com.jijo.jerseyhouse.messaging.publisher;

import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;

import java.util.Map;

public interface JerseyOrderPublisher {
    Map<String, String> publishOrder(JerseyOrderPlacementDto jerseyOrderPlacementDto) throws CommonInternalException;
}
