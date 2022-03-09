package com.example.coindesk.application;

import com.example.coindesk.presentation.dto.response.CoindeskApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Service
public class CoinDeskService {

    private final RestTemplate restTemplate;

    @Value("${coindesk_api}")
    private String coinDeskApi;

    public CoinDeskService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CoindeskApiResponse callCoinDeskApi() {
        return restTemplate.getForEntity(coinDeskApi, CoindeskApiResponse.class).getBody();
    }
}
