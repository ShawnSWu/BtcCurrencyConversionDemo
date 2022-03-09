package com.example.coindesk.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradingPairDetailDto {

    @JsonProperty("code")
    private String code;

    @JsonProperty("zh_name")
    private String zhName;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("updateTime")
    private String updateTime;
}
