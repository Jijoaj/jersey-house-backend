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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_SEQUENCE")
    @SequenceGenerator(name = "TEAMS_SEQUENCE", sequenceName = "TEAMS_SEQUENCE", allocationSize = 1)
    @Column(name = "teamId", nullable = false)
    private Integer teamId;
    private String teamName;
    @ManyToOne
    @Setter
    @JoinColumn(name = "league_league_code")
    private League league;
}
