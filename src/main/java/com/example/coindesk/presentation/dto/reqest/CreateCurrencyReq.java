package com.example.coindesk.presentation.dto.reqest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCurrencyReq {

    @JsonProperty("code")
    @NotBlank(message = "Can not be empty.")
    private String code;

    @JsonProperty("zh_name")
    @NotBlank(message = "Can not be empty.")
    private String zhName;

    @JsonProperty("symbol")
    @NotBlank(message = "Can not be empty.")
    private String symbol;

    @JsonProperty("description")
    @NotBlank(message = "Can not be empty.")
    private String description;
}
