package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mapper.MerchantMapper;
import org.example.services.impl.MerchantServiceImpl;
import org.example.testtools.MerchantDtoTest;
import org.example.testtools.MerchantTest;
import org.hamcrest.Matchers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MerchantControllerTest {

    public static final UUID ID = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MerchantController marchantController;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MerchantMapper merchantMapper;

    @MockBean
    private MerchantServiceImpl merchantServiceImpl;

    @Test
    @WithMockUser
    void shouldGetAllMerchants() throws Exception {
        var merchantDtos = List.of(MerchantDtoTest.create(ID));
        when(merchantServiceImpl.findAllMerchants()).thenReturn(List.of(MerchantTest.create(ID)));
        when(merchantMapper.toDtos(List.of(MerchantTest.create(ID)))).thenReturn(merchantDtos);
        String json = mapper.writeValueAsString(merchantDtos);

        mockMvc.perform(get(MerchantController.BASE)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", Matchers.hasSize(1)));

    }

    @Test
    @WithMockUser
    void shouldUpdateMerchant() throws Exception {
        var merchant = MerchantTest.create(ID);
        var merchantDto = MerchantDtoTest.create(ID);
        when(merchantMapper.toEntity(any())).thenReturn(merchant);
        when(merchantServiceImpl.updateMerchant(merchant)).thenReturn(merchant);
        when(merchantMapper.toDto(merchant)).thenReturn(merchantDto);
        String json = mapper.writeValueAsString(merchantDto);

        mockMvc.perform(put(MerchantController.BASE)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.response.name").value(merchant.getName()));
    }

    @Test
    @WithMockUser
    void shouldDeleteMerchant() throws Exception {
        var merchant = MerchantDtoTest.create(ID);
        when(merchantServiceImpl.deleteMerchant(ID)).thenReturn(MerchantTest.create(ID));
        when(merchantMapper.toDto(MerchantTest.create(ID))).thenReturn(merchant);
        String json = mapper.writeValueAsString(merchant);

        mockMvc.perform(delete(MerchantController.BASE)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value(merchant.getName()));
    }
}