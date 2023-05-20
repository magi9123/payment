package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Merchant {

    @Id
    private long id;

    private String name;

    private String description;

    private String email;

    private MerchantStatus status;

    private BigDecimal totalTransactionSum;
}
