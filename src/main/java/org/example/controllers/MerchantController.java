package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.MerchantDto;
import org.example.dto.ResponseDto;
import org.example.infrastructure.exception.CodedException;
import org.example.mapper.MerchantMapper;
import org.example.services.MerchantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(MerchantController.BASE)
@RequiredArgsConstructor
public class MerchantController {

    public static final String BASE = "/api/payment_system/merchant";

    private final MerchantService merchantService;
    private final MerchantMapper mapper;

    @GetMapping
    public ResponseDto<List<MerchantDto>> getAllMerchants() {
        return ResponseDto.response(mapper.toDtos(merchantService.findAllMerchants()));
    }


    @PutMapping
    public ResponseDto<MerchantDto> updateMerchant(@RequestBody MerchantDto merchantDto) throws CodedException {
        var merchant = merchantService.updateMerchant(mapper.toEntity(merchantDto));
        return ResponseDto.response(mapper.toDto(merchant));
    }

    @DeleteMapping("/{id}")
    public ResponseDto<MerchantDto> deleteMerchant(@PathVariable("id") String id) throws CodedException {
        return ResponseDto.response(mapper.toDto(merchantService.deleteMerchant(UUID.fromString(id))));
    }
}
