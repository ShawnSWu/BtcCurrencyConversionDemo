package com.example.coindesk.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Entity(name = "Currency")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "zh_name")
    private String zhName;

    @Column(name = "description")
    private String description;


}
