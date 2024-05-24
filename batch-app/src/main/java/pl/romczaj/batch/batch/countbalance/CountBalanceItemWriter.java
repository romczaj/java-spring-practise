package pl.romczaj.batch.batch.countbalance;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import pl.romczaj.batch.domain.accountreport.AccountReport;
import pl.romczaj.batch.domain.accountreport.AccountReportRepository;
import pl.romczaj.batch.dto.CountBalanceDto;

@AllArgsConstructor
public class CountBalanceItemWriter implements ItemWriter<CountBalanceDto> {

    private final AccountReportRepository accountReportRepository;

    @Override
    public void write(Chunk<? extends CountBalanceDto> chunk) {
        chunk.getItems().forEach(this::saveOne);
        accountReportRepository.flush();
    }

    private void saveOne(CountBalanceDto countBalanceDto) {
        AccountReport toSave = accountReportRepository.findByAccountNumber(countBalanceDto.accountNumber())
                .map(accountReport -> {
                    accountReport.updateFields(countBalanceDto);
                    return accountReport;
                }).orElse(AccountReport.createFrom(countBalanceDto));

        accountReportRepository.save(toSave);
    }
}
