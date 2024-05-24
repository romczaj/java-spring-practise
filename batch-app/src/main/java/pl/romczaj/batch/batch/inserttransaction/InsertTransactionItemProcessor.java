package pl.romczaj.batch.batch.inserttransaction;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import pl.romczaj.batch.dto.TransactionInsertDto;
import pl.romczaj.batch.domain.transaction.UserTransaction;

import java.math.BigDecimal;

@AllArgsConstructor
public class InsertTransactionItemProcessor implements ItemProcessor<TransactionInsertDto, UserTransaction> {

    @Override
    public UserTransaction process(TransactionInsertDto transactionInsertDto) {
        return UserTransaction.builder()
                .externalTransactionId(transactionInsertDto.externalTransactionId())
                .senderAccount(transactionInsertDto.senderAccount())
                .receiverAccount(transactionInsertDto.receiverAccount())
                .amount(BigDecimal.valueOf(transactionInsertDto.amount()))
                .currency(transactionInsertDto.currency())
                .title(transactionInsertDto.title())
                .transactionDate(transactionInsertDto.transactionDate())
                .build();
    }
}
