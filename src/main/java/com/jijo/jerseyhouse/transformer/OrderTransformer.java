package com.jijo.jerseyhouse.transformer;

import com.jijo.jerseyhouse.dto.JerseyOrderPlacementDto;
import com.jijo.jerseyhouse.model.OrderPlacement;
import com.jijo.jerseyhouse.model.enums.OrderStatus;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class OrderTransformer {
    public static OrderPlacement createOrder(JerseyOrderPlacementDto jerseyOrderPlacementDto) {
        return OrderPlacement.builder()
                .orderId(UUID.randomUUID().toString())
                .teamId(jerseyOrderPlacementDto.getTeamId())
                .orderDate(jerseyOrderPlacementDto.getOrderDate())
                .size(jerseyOrderPlacementDto.getSize())
                .seasonCode(jerseyOrderPlacementDto.getSeasonCode())
                .userId(Base64Utils.encodeToString(jerseyOrderPlacementDto.getUserId().getBytes(StandardCharsets.UTF_8)))
                .quantity(jerseyOrderPlacementDto.getQuantity())
                .orderStatus(OrderStatus.CREATED.orderStatus)
                .build();
    }
}
