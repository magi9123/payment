package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.MerchantDto;
import org.example.dto.ResponseDto;
import org.example.infrastructure.CodedException;
import org.example.mapper.MerchantMapper;
import org.example.services.impl.MerchantServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(MerchantController.BASE)
@RequiredArgsConstructor
public class MerchantController {

    public static final String BASE = "/api/payment_system/merchant";

    private final MerchantServiceImpl merchantService;
    private final MerchantMapper mapper;

    @GetMapping
    public ResponseDto<List<MerchantDto>> getAllMerchants() {
        return ResponseDto.response(mapper.toDtos(merchantService.findAllMerchants()));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<MerchantDto> deleteMerchant(@PathVariable("id") String id) throws CodedException {
        return ResponseDto.response(mapper.toDto(merchantService.deleteMerchant(UUID.fromString(id))));
    }
}
