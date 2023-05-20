package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.MerchantDto;
import org.example.mapper.MerchantMapper;
import org.example.services.impl.MerchantServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(MerchantController.BASE)
@RequiredArgsConstructor
public class MerchantController {

    public static final String BASE = "/api/payment_system/merchant";

    private final MerchantServiceImpl merchantService;
    private final MerchantMapper mapper;

    @GetMapping
    public ResponseEntity<List<MerchantDto>> getAllMerchants() {
        return ResponseEntity.ok(mapper.toDtos(merchantService.findAllMerchants()));
    }

}
