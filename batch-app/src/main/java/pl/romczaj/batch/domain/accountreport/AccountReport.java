package pl.romczaj.batch.domain.accountreport;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.romczaj.batch.dto.CountBalanceDto;

import java.math.BigDecimal;

import static jakarta.persistence.AccessType.FIELD;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Access(FIELD)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountReport {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long accountReportId;
    @Column(unique = true)
    private String accountNumber;
    private BigDecimal income;
    private BigDecimal outcome;
    private BigDecimal balance;

    public static AccountReport createFrom(CountBalanceDto countBalanceDto){
        return AccountReport.builder()
                .accountNumber(countBalanceDto.accountNumber())
                .income(countBalanceDto.income())
                .outcome(countBalanceDto.outcome())
                .balance(countBalanceDto.income().subtract(countBalanceDto.outcome()))
                .build();
    }

    public void updateFields(CountBalanceDto countBalanceDto){
        this.income = countBalanceDto.income();
        this.outcome = countBalanceDto.outcome();
        this.balance = countBalanceDto.balance();
    }
}
