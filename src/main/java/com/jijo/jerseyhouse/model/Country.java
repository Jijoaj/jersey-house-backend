package com.jijo.jerseyhouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name = "COUNTRY")
@Entity
@Getter
@AllArgsConstructor
public class Country {
    @Column(nullable = false)
    @Id
    private int countryCode;

    @Column(nullable = false, length = 20)
    private String countryName;
}
