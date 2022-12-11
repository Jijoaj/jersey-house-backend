package com.jijo.jerseyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JerseyOrderDto {
    private int teamId;
    private int seasonCode;
    private String size;
    private int quantity;

    public JerseyOrderDto(JerseyOrderDto jerseyOrderDto) {
        this.teamId = jerseyOrderDto.getTeamId();
        this.seasonCode = jerseyOrderDto.getSeasonCode();
        this.size = jerseyOrderDto.getSize();
        this.quantity = jerseyOrderDto.getQuantity();
    }
}
