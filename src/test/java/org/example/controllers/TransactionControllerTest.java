package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mapper.TransactionMapper;
import org.example.services.impl.MerchantServiceImpl;
import org.example.services.impl.TransactionServiceImpl;
import org.example.testtools.TransactionDtoTest;
import org.example.testtools.TransactionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TransactionController transactionController;
    @MockBean
    private TransactionServiceImpl transactionService;
    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    @WithMockUser
    void shouldGetAllTransactions() throws Exception {
        var uuid = UUID.randomUUID();
        var transactionList = List.of(TransactionTest.create(uuid));
        when(transactionService.findAllTransactions()).thenReturn(transactionList);
        when(transactionMapper.toDtos(transactionList)).thenReturn(List.of(TransactionDtoTest.create(uuid)));
        String json = mapper.writeValueAsString(List.of(TransactionDtoTest.create(uuid)));

        mockMvc.perform(get(TransactionController.BASE)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty());
    }

}