package com.jijo.jerseyhouse.dto;

import com.jijo.jerseyhouse.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    private OrderStatus status;
    private String orderID;
}
