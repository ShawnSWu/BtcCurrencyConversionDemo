package com.example.coindesk.presentation.dto.response;

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
public class CurrencyDto {

    private long id;

    private String code;

    private String symbol;

    private String zhName;

    private String description;
}
