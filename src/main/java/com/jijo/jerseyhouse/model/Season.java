package com.jijo.jerseyhouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Season {
    @Id
    private Integer seasonCode;
    @Column(nullable = false, unique = true)
    private Integer startYear;
    @Column(nullable = false, unique = true)
    private Integer endYear;
}
