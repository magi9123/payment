package org.example.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue
    private UUID uuid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column
    private String customerEmail;

    @Column
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "reference_id", nullable = false)
    private Merchant referenceId;
}
