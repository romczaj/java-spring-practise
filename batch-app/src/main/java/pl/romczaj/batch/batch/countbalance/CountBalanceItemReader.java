package pl.romczaj.batch.batch.countbalance;

import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.ClassUtils;
import pl.romczaj.batch.domain.transaction.UserTransactionRepository;
import pl.romczaj.batch.dto.CountBalanceDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CountBalanceItemReader extends AbstractItemCountingItemStreamItemReader<CountBalanceDto> {

    private List<CountBalanceDto> countBalanceDtoList;
    private final UserTransactionRepository userTransactionRepository;


    public CountBalanceItemReader(UserTransactionRepository userTransactionRepository) {
        this.userTransactionRepository = userTransactionRepository;
        setName(ClassUtils.getShortName(getClass()));
    }

    @Override
    protected CountBalanceDto doRead() {
        int currentItemCount = getCurrentItemCount();
        if (currentItemCount <= countBalanceDtoList.size()) {
            return countBalanceDtoList.get(currentItemCount - 1);
        }
        return null;
    }

    @Override
    protected void doOpen() {
        List<Tuple> tuples = userTransactionRepository.calculateAccountBalances();
        countBalanceDtoList = tuples.stream().map(tuple -> new CountBalanceDto(
                tuple.get("accountNumber", String.class),
                tuple.get("outcome", BigDecimal.class),
                tuple.get("income", BigDecimal.class),
                null
        )).toList();
    }

    @Override
    protected void doClose() {
        log.info("Closing file reader");
    }
}
