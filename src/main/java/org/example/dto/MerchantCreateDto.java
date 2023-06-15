package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.MerchantStatus;
import org.example.xml.facada.TransactionsFileValidation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MerchantCreateDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @Email(message = "Email is not valid", regexp = TransactionsFileValidation.REGEX_EMAIL)
    @NotBlank(message = "Email is required")
    private String email;

    private MerchantStatus status;

    @PositiveOrZero(message = "Sum of transactions must be zero or greater positive number")
    private BigDecimal totalTransactionSum;

    private List<TransactionDto> transactionList;
}
