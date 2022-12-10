package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.dto.JerseyOrderDto;

import java.util.Map;

public interface OrderService {
    Map<String, String> orderJersey(JerseyOrderDto jerseyOrderDto, String userId);
}
