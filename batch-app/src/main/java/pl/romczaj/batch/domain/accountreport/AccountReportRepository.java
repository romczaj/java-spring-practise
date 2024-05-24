package pl.romczaj.batch.domain.accountreport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountReportRepository extends JpaRepository<AccountReport, Long> {

    Optional<AccountReport> findByAccountNumber(String accountNumber);
}
