package com.jijo.jerseyhouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne
    @Setter
    @JoinColumn(name = "league_league_code")
    private League league;
}
