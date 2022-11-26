package com.jijo.jerseyhouse.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JerseyViewDto {
    private int teamId;
    private String teamName;
    private Long stock;
    private boolean hasStock;
    private String imageUrl;
    private String Season;
    private int SeasonCode;
}
