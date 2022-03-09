package com.example.coindesk.domain;

import lombok.*;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradingPair {
    private String code;
    private String symbol;
    private String rate;
    private String description;
    private float rate_float;
    private String updateTime;
}
