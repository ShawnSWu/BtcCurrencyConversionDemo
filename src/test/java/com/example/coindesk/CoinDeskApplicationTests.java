package com.example.coindesk;

import com.example.coindesk.presentation.CurrencyApi;
import com.example.coindesk.presentation.dto.reqest.CreateCurrencyReq;
import com.example.coindesk.presentation.dto.reqest.ModifyCurrencyReq;
import com.example.coindesk.presentation.dto.response.CoindeskApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = CoindeskApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
@Sql(scripts = {"classpath:schema.sql", "classpath:testData.sql"}, executionPhase = BEFORE_TEST_METHOD)
class CoinDeskApplicationTests {

    private final String URL_PREFIX = "/v1/currency/";

    @Resource
    protected ObjectMapper objectMapper;

    @Autowired
    private CurrencyApi currencyApi;

    private MockMvc mockMvc;

    @Value("${coindesk_api}")
    private String coinDeskApi;

    @Resource
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyApi).build();
    }

    //測試呼叫新增幣別對應表資料 API
    @Test
    void test_create_currency_when_given_currency_entity_should_return_http_200() throws Exception {
        String code = "TWD";
        String symbol = "NT$";
        String zhName = "新台幣";
        String description = "New Taiwan Dollar";

        CreateCurrencyReq req = CreateCurrencyReq.builder()
                .code(code)
                .symbol(symbol)
                .zhName(zhName)
                .description(description)
                .build();

        mockMvc.perform(jsonRequest(post(URL_PREFIX), req))
                .andExpect(status().isOk());
    }

    //測試呼叫資料轉換的 API,並顯示其內容。
    //測試呼叫查詢幣別對應表資料 API,並顯示其內容。
    @Test
    void test_find_currency_when_given_currency_code_should_return_currency() throws Exception {
        String paramKey = "code";
        String code = "EUR";
        String zhName = "歐元";
        String symbol = "&euro;";

        MvcResult mvcResult = mockMvc.perform(
                        get(URL_PREFIX).param(paramKey, code))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(code))
                .andExpect(jsonPath("zh_name").value(zhName))
                .andExpect(jsonPath("symbol").value(symbol))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }


    //測試呼叫刪除幣別對應表資料 API。
    @Test
    void test_remove_currency_when_given_currency_id_should_return_http_200() throws Exception {
        String currencyId = "2";
        String url = URL_PREFIX + currencyId;

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    //測試呼叫更新幣別對應表資料 API,並顯示其內容。
    @Test
    void test_update_currency_when_given_currency_should_return_updated_currency() throws Exception {
        long id = 1;
        String code = "TWZ";
        String symbol = "NT2$";
        String zhName = "新台幣2";
        String description = "New Taiwan Dollar2";

        ModifyCurrencyReq mc = ModifyCurrencyReq.builder()
                .id(id)
                .code(code)
                .symbol(symbol)
                .zhName(zhName)
                .description(description)
                .build();

        MvcResult mvcResult = mockMvc.perform(jsonRequest(put(URL_PREFIX), mc))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value(code))
                .andExpect(jsonPath("zhName").value(zhName))
                .andExpect(jsonPath("symbol").value(symbol))
                .andExpect(jsonPath("description").value(description))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }

    //測試呼叫coindesk API,並顯示其內容。
    @Test
    public void test_call_coindesk_api_should_return_DTO() throws Exception {
        CoindeskApiResponse response = restTemplate.getForEntity(coinDeskApi, CoindeskApiResponse.class).getBody();

        Assertions.assertNotNull(response);

        System.out.println(objectMapper.writeValueAsString(response));
    }

    protected MockHttpServletRequestBuilder jsonRequest(MockHttpServletRequestBuilder requestBuilder, Object object)
            throws JsonProcessingException {
        return requestBuilder.content(toJson(object))
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
