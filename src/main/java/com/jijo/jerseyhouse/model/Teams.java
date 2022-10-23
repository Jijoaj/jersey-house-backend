package com.jijo.jerseyhouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Teams {
    @Id
    @Column(name = "teamId", nullable = false)
    private Integer teamId;
    @Column(nullable = false)
    private String teamName;
    @Column(nullable = false, length = 3)
    private String shortName;
    private Integer leagueCode;
}
