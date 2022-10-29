package com.jijo.jerseyhouse.model;

import com.jijo.jerseyhouse.model.enums.TShirtSize;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "jersey")
public class Jersey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jersey_seq")
    @SequenceGenerator(name = "jersey_seq",initialValue = 1,allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private Teams teamCode;
    private TShirtSize size;
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "season_code")
    private Season seasonCode;
    private String imageUrl;
}