package com.example.coindesk.presentation;

import com.example.coindesk.application.CoinDeskService;
import com.example.coindesk.domain.CurrencyService;
import com.example.coindesk.domain.entity.CurrencyEntity;
import com.example.coindesk.exception.InvalidRequestException;
import com.example.coindesk.presentation.dto.response.CoindeskApiResponse;
import com.example.coindesk.presentation.dto.response.CurrencyDto;
import com.example.coindesk.presentation.dto.response.TradingPairDetailDto;
import com.example.coindesk.presentation.dto.reqest.CreateCurrencyReq;
import com.example.coindesk.presentation.dto.reqest.ModifyCurrencyReq;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@RestController
@RequestMapping("/v1/currency")
public class CurrencyApi {

    private final CurrencyService currencyService;

    public CurrencyApi(CurrencyService currencyService, com.example.coindesk.application.CoinDeskService coinDeskService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createCurrency(@RequestBody @Valid CreateCurrencyReq createCurrencyReq, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult.getFieldErrors());
        }

        currencyService.create(convertToEntity(createCurrencyReq));
        return ResponseEntity.ok().build();
    }

    private CurrencyEntity convertToEntity(CreateCurrencyReq createCurrencyReq) {
        return CurrencyEntity.builder()
                .code(createCurrencyReq.getCode())
                .zhName(createCurrencyReq.getZhName())
                .symbol(createCurrencyReq.getSymbol())
                .description(createCurrencyReq.getDescription())
                .build();
    }


    @GetMapping("")
    public ResponseEntity<TradingPairDetailDto> getCurrencyDetail(@RequestParam("code") String code) {
        TradingPairDetailDto tpd = currencyService.find(code);
        return ResponseEntity.ok(tpd);
    }

    @PutMapping("")
    public ResponseEntity<CurrencyDto> modifyCurrencyDetail(@RequestBody ModifyCurrencyReq modifyCurrencyReq) {
        CurrencyEntity updated = currencyService.update(modifyCurrencyReq);

        CurrencyDto currencyDto = CurrencyDto.builder()
                .id(updated.getId())
                .code(updated.getCode())
                .zhName(updated.getZhName())
                .symbol(updated.getSymbol())
                .description(updated.getDescription())
                .build();
        return ResponseEntity.ok(currencyDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurrencyDetail(@PathVariable String id) {
        currencyService.remove(Long.parseLong(id));
        return ResponseEntity.ok().build();
    }

}
