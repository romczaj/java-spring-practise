package pl.romczaj.batch.batch.countbalance;

import org.springframework.batch.item.ItemProcessor;
import pl.romczaj.batch.domain.accountreport.AccountReport;
import pl.romczaj.batch.dto.CountBalanceDto;

public class CountBalanceItemProcessor implements ItemProcessor<CountBalanceDto, CountBalanceDto> {


    @Override
    public CountBalanceDto process(CountBalanceDto item) {
        return item.withBalance(item.income().subtract(item.outcome()));
    }
}
