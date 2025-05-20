package com.example.tenpo.controller;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.tenpo.dto.CreateTransactionRequest;
import com.example.tenpo.dto.UpdateTransactionRequest;
import com.example.tenpo.entity.Client;
import com.example.tenpo.entity.Transaction;
import com.example.tenpo.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    Transaction transaction;

    @BeforeEach
    public void setup() {

        transaction = Transaction.builder()
                .id(1)
                .amount(1000)
                .businessSector("Ahorros")
                .client(Client.builder().id(1).name("Pablo Gomez").build())
                .clientName("Pablo Gomez")
                .transactionDate(LocalDateTime.now().minusMinutes(10))
                .build();

    }

    @Test
    @Order(1)
    public void saveTransactionTest() throws Exception {
        // precondition
        given(transactionService.create(any(CreateTransactionRequest.class))).willReturn(transaction);

        // action
        CreateTransactionRequest transactionRequest = CreateTransactionRequest.builder()
                .amount(transaction.getAmount())
                .businessSector(transaction.getBusinessSector())
                .clientName(transaction.getClientName())
                .transactionDate(transaction.getTransactionDate())
                .build();
        ResultActions response = mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionRequest)));

        // verify
        response.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.amount",
                        is(transactionRequest.getAmount())))
                .andExpect(jsonPath("$.businessSector",
                        is(transactionRequest.getBusinessSector())))
                .andExpect(jsonPath("$.clientName",
                        is(transactionRequest.getClientName())))
                .andExpect(jsonPath("$.transactionDate", is(transactionRequest.getTransactionDate().toString())));
    }

    @Test
    @Order(2)
    public void getTransactionsTest() throws Exception {
        // precondition
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList
                .add(Transaction.builder().id(2).amount(4000).businessSector("Compras")
                        .client(Client.builder().id(1).name("Jose Perez").build())
                        .clientName("Jose Perez")
                        .transactionDate(LocalDateTime.now().minusDays(1)).build());
        given(transactionService.findAll()).willReturn(transactionList);

        // action
        ResultActions response = mockMvc.perform(get("/transactions"));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(transactionList.size())));
    }

    @Test
    @Order(4)
    public void updateTransactionTest() throws Exception{
        // precondition
        UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                .amount(5000)
                .businessSector("Ventas")
                .build();
        transaction.setAmount(5000);
        transaction.setBusinessSector("Ventas");
        given(transactionService.update(transaction.getId(), updateTransactionRequest)).willReturn(transaction);

        // action
        ResultActions response = mockMvc.perform(put("/transactions/{id}", transaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.amount", is(updateTransactionRequest.getAmount())))
                .andExpect(jsonPath("$.businessSector", is(updateTransactionRequest.getBusinessSector())));
    }

    @Test
    public void deleteTransactionTest() throws Exception{
        // precondition
        willDoNothing().given(transactionService).delete(transaction.getId());

        // action
        ResultActions response = mockMvc.perform(delete("/transactions/{id}", transaction.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
