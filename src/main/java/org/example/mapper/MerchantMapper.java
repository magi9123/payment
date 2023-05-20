package org.example.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.MerchantDto;
import org.example.model.Merchant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MerchantMapper {

    private final ObjectMapper mapper;

    public MerchantDto toDto(Merchant merchant) {
        return mapper.convertValue(merchant, MerchantDto.class);
    }

    public List<MerchantDto> toDtos(List<Merchant> merchantList) {
        return merchantList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
