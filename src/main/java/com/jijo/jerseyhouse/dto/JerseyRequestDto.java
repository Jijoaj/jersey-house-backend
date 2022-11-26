package com.jijo.jerseyhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JerseyRequestDto {
    List<Integer> Teams;
    List<Integer> seasons;
    List<String> size;
}
