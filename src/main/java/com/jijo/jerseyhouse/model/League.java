package com.jijo.jerseyhouse.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class League {
    @Id
    private Integer leagueCode;
    @Column(nullable = false, unique = true, length = 15)
    private String leagueName;
}
