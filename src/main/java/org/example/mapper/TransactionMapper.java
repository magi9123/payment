package org.example.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.dto.TransactionDto;
import org.example.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final ObjectMapper mapper;

    public TransactionDto toDto(Transaction merchant) {
        return mapper.convertValue(merchant, TransactionDto.class);
    }

    public List<TransactionDto> toDtos(List<Transaction> merchantList) {
        return merchantList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
