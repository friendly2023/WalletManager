package com.example.wallet_manager.controller;

import com.example.wallet_manager.dto.WalletOperationRequest;
import com.example.wallet_manager.entity.Wallet;
import com.example.wallet_manager.enums.OperationType;
import com.example.wallet_manager.service.WalletOperationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    String messageExNotSupp = "Field is not supported";
    String messageExInvalidData = "Invalid data format";
    String messageExNotNull = "must not be null";
    String url = "/api/v1/wallet";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private WalletOperationService walletOperationService;

    @Test
    void getWalletData_shouldReturnWallet_whenRequestIsValid() throws Exception {

        WalletOperationRequest request = new WalletOperationRequest();
        UUID walletId = UUID.randomUUID();

        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(BigDecimal.valueOf(100.0));

        Field field = WalletOperationRequest.class.getDeclaredField("walletId");
        field.setAccessible(true);
        field.set(request, walletId);


        Wallet expectedWallet = new Wallet();
        expectedWallet.setBalance(BigDecimal.valueOf(1100.0));

        Field field1 = Wallet.class.getDeclaredField("id");
        field1.setAccessible(true);
        field1.set(expectedWallet, walletId);


        when(walletOperationService.applyOperation(any(WalletOperationRequest.class))).thenReturn(expectedWallet);

        mockMvc.perform(post("/api/v1/wallet").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andExpect(jsonPath("$.balance").value(1100.0));
    }

    @Test
    void getWalletData_shouldReturnBadRequest_whenWalletIdTypeIsInvalid() throws Exception {

        String invalidJson = """
                        {
                         "walletId": "qwert",
                         "operationType": "DEPOSIT",
                         "amount": 1000
                         }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("walletId"))
                .andExpect(jsonPath("$.violations[0].message").value(messageExInvalidData));
    }

    @Test
    void getWalletData_shouldReturnBadRequest_whenOperationTypeTypeIsInvalid() throws Exception {

        String invalidJson = """
                        {
                         "walletId": "550e8400-e29b-41d4-a716-446655440002",
                         "operationType": "qwert",
                         "amount": 1000
                         }
                """;

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("operationType"))
                .andExpect(jsonPath("$.violations[0].message").value(messageExInvalidData));
    }

    @Test
    void applyOperation_shouldReturnBadRequest_whenAmountIsInvalidJson() throws Exception {

        String invalidJson = """
                    {
                        "walletId": "550e8400-e29b-41d4-a716-446655440002",
                        "operationType": "DEPOSIT",
                        "amount": 1p000
                    }
                """;

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").doesNotExist())
                .andExpect(jsonPath("$.violations[0].message").value(containsString("Unexpected character")));
    }

    @Test
    void applyOperation_shouldReturnBadRequest_whenThereIsNoRequiredFields() throws Exception {

        String invalidJson = """
                    {
                        "walletId": "550e8400-e29b-41d4-a716-446655440002",
                        "operationType": "DEPOSIT"
                    }
                """;
        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("amount"))
                .andExpect(jsonPath("$.violations[0].message").value(messageExNotNull));
    }

    @Test
    void applyOperation_shouldReturnBadRequest_whenThereAreExtraFields() throws Exception {

        String invalidJson = """
                    {
                     "walletId": "550e8400-e29b-41d4-a716-446655440002",
                     "operationType": "DEPOSIT",
                     "amount": 1000,
                     "qwert": 1000
                     }
                """;

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations[0].fieldName").value("qwert"))
                .andExpect(jsonPath("$.violations[0].message").value(messageExNotSupp));
    }
}
