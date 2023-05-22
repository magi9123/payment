package org.example.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue
    private UUID uuid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column
    private String customerEmail;

    @Column
    private String customerPhone;

    @Column
    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "uuid",referencedColumnName = "id",nullable = false,insertable = false,updatable = false)
    private Merchant referenceId;
}
