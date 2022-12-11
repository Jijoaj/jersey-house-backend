package com.jijo.jerseyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
    private Map<String, String> status;
    private String orderID;
}
