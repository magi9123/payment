package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Merchant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String email;

    @Column(nullable = false)
    private MerchantStatus status;

    @Column
    private BigDecimal totalTransactionSum;

    @OneToMany(mappedBy = "uuid")
    private List<Transaction> transactionList;
}
