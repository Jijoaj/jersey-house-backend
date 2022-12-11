package com.jijo.jerseyhouse.service;

import com.jijo.jerseyhouse.dto.JerseyOrderDto;
import com.jijo.jerseyhouse.exception.CommonInternalException;

import java.util.Map;

public interface OrderService {
    Map<String, String> orderJersey(JerseyOrderDto jerseyOrderDto, String userId) throws CommonInternalException;
}
