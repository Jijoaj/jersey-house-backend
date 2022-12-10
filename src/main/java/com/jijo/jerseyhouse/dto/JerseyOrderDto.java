package com.jijo.jerseyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JerseyOrderDto {
    private int teamId;
    private int SeasonCode;
    private String size;
    private int quantity;
}
