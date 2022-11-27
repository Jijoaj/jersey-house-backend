package com.jijo.jerseyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JerseyRequestDto {
    private List<Integer> Teams;
    private List<Integer> seasons;
    private List<String> size;
    private int page;
    private int records;
}
