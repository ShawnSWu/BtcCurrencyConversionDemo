package com.example.coindesk.domain;

import com.example.coindesk.application.CoinDeskService;
import com.example.coindesk.domain.entity.CurrencyEntity;
import com.example.coindesk.exception.InvalidRequestException;
import com.example.coindesk.presentation.dto.response.TradingPairDetailDto;
import com.example.coindesk.domain.repository.CurrencyRepository;
import com.example.coindesk.exception.NotFoundCurrencyException;
import com.example.coindesk.presentation.dto.response.CoindeskApiResponse;
import com.example.coindesk.presentation.dto.reqest.ModifyCurrencyReq;
import com.example.coindesk.utils.DateTimeUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CoinDeskService coinDeskService;

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CoinDeskService coinDeskService, CurrencyRepository currencyRepository) {
        this.coinDeskService = coinDeskService;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void create(CurrencyEntity currencyEntity) {
        try {
            currencyRepository.save(currencyEntity);
        } catch (Exception e) {
            throw new InvalidRequestException("", "", "Can not insert duplicate data into a unique column.");
        }
    }


    private TradingPair getBtcPriceByCode(String code) {
        CoindeskApiResponse cdr = coinDeskService.callCoinDeskApi();
        Map<String, TradingPair> tradingPairMap = getTradingPairMap(cdr);
        return tradingPairMap.get(code.toLowerCase());
    }

    //回傳所有幣別的資料，若未來有新增新的幣別也可兼容
    @SneakyThrows
    private Map<String, TradingPair> getTradingPairMap(CoindeskApiResponse coindeskApiResponse) {
        Map<String, TradingPair> tradingPairMap = new HashMap<>();

        CoindeskApiResponse.Bpi bpi = coindeskApiResponse.getBpi();
        Field[] fields = bpi.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == TradingPair.class) {
                TradingPair tradingPair = (TradingPair) field.get(bpi);
                tradingPair.setUpdateTime(DateTimeUtils.convertDateText(coindeskApiResponse.getTime().getUpdatedISO()));
                tradingPairMap.put(field.getName(), tradingPair);
            }
        }
        return tradingPairMap;
    }


    @Override
    public TradingPairDetailDto find(String code) {
        //取得特定交易對匯率
        TradingPair tp = getBtcPriceByCode(code);
        if (null == tp) {
            throw new NotFoundCurrencyException("沒有對應的幣別匯率");
        }

        //取得幣別資料
        Optional<CurrencyEntity> currency = currencyRepository.findByCode(code);
        if (currency.isEmpty()) {
            throw new NotFoundCurrencyException("資料庫中沒有對應的幣別資料");
        }

        CurrencyEntity c = currency.get();

        return TradingPairDetailDto.builder()
                .code(c.getCode())
                .symbol(c.getSymbol())
                .zhName(c.getZhName())
                .rate(tp.getRate())
                .updateTime(tp.getUpdateTime())
                .build();
    }

    @Override
    public CurrencyEntity update(ModifyCurrencyReq modifyCurrencyReq) {

        Optional<CurrencyEntity> currency = currencyRepository.findById(modifyCurrencyReq.getId());
        if (currency.isEmpty()) {
            throw new NotFoundCurrencyException("沒有對應的幣別資料");
        }

        CurrencyEntity ce = convertToEntity(modifyCurrencyReq);
        return currencyRepository.save(ce);
    }

    private CurrencyEntity convertToEntity(ModifyCurrencyReq createCurrencyReq) {
        return CurrencyEntity.builder()
                .id(createCurrencyReq.getId())
                .code(createCurrencyReq.getCode())
                .zhName(createCurrencyReq.getZhName())
                .symbol(createCurrencyReq.getSymbol())
                .description(createCurrencyReq.getDescription())
                .build();
    }

    @Override
    public void remove(long id) {

        currencyRepository.findById(id)
                .orElseThrow(()-> new NotFoundCurrencyException("not fond id."));

        CurrencyEntity ce = CurrencyEntity.builder().id(id).build();
        currencyRepository.delete(ce);
    }

}
