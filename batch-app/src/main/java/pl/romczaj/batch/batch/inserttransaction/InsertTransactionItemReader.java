package pl.romczaj.batch.batch.inserttransaction;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.ClassUtils;
import pl.romczaj.batch.batch.CustomBatchProperties;
import pl.romczaj.batch.dto.TransactionInsertDto;
import pl.romczaj.batch.generator.TransactionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.romczaj.batch.batch.JobParameterConst.GENERATE_TRANSACTION_SIZE;

@Slf4j
public class InsertTransactionItemReader extends AbstractItemCountingItemStreamItemReader<TransactionInsertDto> {

    private List<TransactionInsertDto> transactionInsertDtoList = new ArrayList<>();
    private final TransactionGenerator transactionGenerator = new TransactionGenerator();
    private final Map<String, Object> jobParameters;
    private final CustomBatchProperties customBatchProperties;


    public InsertTransactionItemReader(Map<String, Object> jobParameters, CustomBatchProperties customBatchProperties) {
        setName(ClassUtils.getShortName(getClass()));
        this.jobParameters = jobParameters;
        this.customBatchProperties = customBatchProperties;
    }

    @Override
    protected TransactionInsertDto doRead() {
        int currentItemCount = getCurrentItemCount();
        if (currentItemCount <= transactionInsertDtoList.size()) {
            return transactionInsertDtoList.get(currentItemCount - 1);
        }
        return null;
    }

    @Override
    protected void doOpen() {
        int size = (int) jobParameters.get(GENERATE_TRANSACTION_SIZE);

        Integer generateTransactionSize = Optional.ofNullable(jobParameters.get(GENERATE_TRANSACTION_SIZE))
                .map(v -> (int) v)
                .orElse(customBatchProperties.defaultGenerateTransactionsSize());

        transactionInsertDtoList = transactionGenerator.generate(generateTransactionSize);
        log.info("Generated {} transactions", size);
    }

    @Override
    protected void doClose() {
        log.info("Closing file reader");
    }

}
