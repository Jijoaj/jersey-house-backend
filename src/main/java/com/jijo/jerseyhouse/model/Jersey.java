package com.jijo.jerseyhouse.model;

import com.jijo.jerseyhouse.model.enums.TShirtSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jersey")
public class Jersey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jersey_seq")
    @SequenceGenerator(name = "jersey_seq",initialValue = 1,allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "team_code",  nullable = false)
    private Teams teamCode;
    @Column(nullable = false)
    private TShirtSize size;
    @Column(nullable = false)
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "season_code", nullable = false)
    private Season seasonCode;
    private String imageUrl;
}