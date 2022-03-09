package com.example.coindesk.presentation.dto.response;

import com.example.coindesk.domain.TradingPair;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoindeskApiResponse {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Bpi bpi;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Bpi {
        @JsonProperty("USD")
        public TradingPair usd;
        @JsonProperty("GBP")
        public TradingPair gbp;
        @JsonProperty("EUR")
        public TradingPair eur;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Time{
        private String updated;
        private Date updatedISO;
        private String updateduk;
    }
}
