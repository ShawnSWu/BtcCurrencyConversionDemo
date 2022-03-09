package com.example.coindesk.presentation.dto.reqest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCurrencyReq {

    @JsonProperty("id")
    private long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("zh_name")
    private String zhName;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("description")
    private String description;
}
