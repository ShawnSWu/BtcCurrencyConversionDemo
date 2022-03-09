package com.example.coindesk.domain;

import com.example.coindesk.domain.entity.CurrencyEntity;
import com.example.coindesk.presentation.dto.response.TradingPairDetailDto;
import com.example.coindesk.presentation.dto.reqest.ModifyCurrencyReq;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public interface CurrencyService {

    void create(CurrencyEntity currencyEntity);

    TradingPairDetailDto find(String code);

    CurrencyEntity update(ModifyCurrencyReq modifyCurrencyReq);

    void remove(long id);
}
