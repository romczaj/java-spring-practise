package pl.romczaj.batch.dto;

import java.time.Instant;
import java.util.UUID;


public record TransactionInsertDto(UUID externalTransactionId,
                                   String senderAccount,
                                   String receiverAccount,
                                   Integer amount,
                                   String currency,
                                   String title,
                                   Instant transactionDate) {
}