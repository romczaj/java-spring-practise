package pl.romczaj.batch.domain.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.AccessType.FIELD;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Access(FIELD)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long transactionId;
    private UUID externalTransactionId;
    private String senderAccount;
    private String receiverAccount;
    private BigDecimal amount;
    private String currency;
    private String title;
    private Instant transactionDate;
    @CreationTimestamp
    private Instant creationDate;
}
