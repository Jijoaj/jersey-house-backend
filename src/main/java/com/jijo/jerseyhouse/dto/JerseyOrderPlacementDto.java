package com.jijo.jerseyhouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
public class JerseyOrderPlacementDto extends JerseyOrderDto{
    private Date orderDate;
    private String userId;

    public JerseyOrderPlacementDto(JerseyOrderDto jerseyOrderDto, String userId) {
        super(jerseyOrderDto);
        this.userId = userId;
        this.orderDate = new Date(System.currentTimeMillis());
    }
}
