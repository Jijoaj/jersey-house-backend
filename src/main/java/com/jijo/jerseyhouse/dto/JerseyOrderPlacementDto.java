package com.jijo.jerseyhouse.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class JerseyOrderPlacementDto extends JerseyOrderDto{
    private Date orderDate;
    private String userId;

    public JerseyOrderPlacementDto(JerseyOrderDto jerseyOrderDto, String userId) {
        super(jerseyOrderDto);
        this.userId = userId;
        this.orderDate = new Date(System.currentTimeMillis());
    }
}
