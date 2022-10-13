package com.jijo.jerseyhouse.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class LeagueAvailability {
    @Id
    private Integer id;
    @Column(nullable = false)
    private int leagueCode;
    @Column(nullable = false)
    private int CountryCode;
}
