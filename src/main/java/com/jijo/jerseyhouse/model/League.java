package com.jijo.jerseyhouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class League implements Serializable {
    @Id
    private Integer leagueCode;
    @Column(nullable = false, unique = true, length = 15)
    private String leagueName;
}
