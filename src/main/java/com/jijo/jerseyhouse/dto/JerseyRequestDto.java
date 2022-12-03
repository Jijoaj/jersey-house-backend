package com.jijo.jerseyhouse.dto;

import com.jijo.jerseyhouse.model.enums.JerseyViewSortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JerseyRequestDto {
    private List<Integer> Teams;
    private List<Integer> seasons;
    private List<String> size;
    private int page;
    private int records;
    private JerseyViewSortBy sortBy;
    private String sortByOrder;
}
