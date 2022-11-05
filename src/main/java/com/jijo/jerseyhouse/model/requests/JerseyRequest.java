package com.jijo.jerseyhouse.model.requests;

import com.jijo.jerseyhouse.model.enums.TShirtSize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JerseyRequest {
    List<Integer> Teams;
    List<Integer> seasons;
    List<TShirtSize> size;
}
