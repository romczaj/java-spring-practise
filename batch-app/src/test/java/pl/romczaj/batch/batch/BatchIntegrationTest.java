package pl.romczaj.batch.batch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pl.romczaj.batch.api.BatchController;
import pl.romczaj.batch.domain.accountreport.AccountReport;
import pl.romczaj.batch.domain.accountreport.AccountReportRepository;
import pl.romczaj.batch.domain.transaction.UserTransactionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestContainerSetUp.class)
@TestMethodOrder(OrderAnnotation.class)
class BatchIntegrationTest {

    @Autowired
    private BatchController batchController;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Autowired
    private AccountReportRepository accountReportRepository;

    @Test
    @Order(1)
    void shouldGenerateTransactions() throws JobExecutionException {
        // given
        int generateTransactionSize = 1000;
        // when
        batchController.generateTransactions(generateTransactionSize);
        // then
        Assertions.assertEquals(Long.valueOf(generateTransactionSize), userTransactionRepository.count());
    }

    @Test
    @Order(2)
    void shouldPrepareReport() throws JobExecutionException {

        // when
        batchController.generateReport();
        // then
        List<AccountReport> accountReports = accountReportRepository.findAll();
        assertFalse(accountReports.isEmpty());
        accountReports.forEach(accountReport -> assertNotNull(accountReport.getBalance()));
    }
}
