package org.example.model;

import lombok.*;
import org.hibernate.annotations.Type;

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
    @Type(type="uuid-char")
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid")
    private List<Transaction> transactionList;
}
