package pl.romczaj.batch.api;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static pl.romczaj.batch.batch.JobParameterConst.GENERATE_TRANSACTION_SIZE;

@RequiredArgsConstructor
@RestController
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job insertTransactionJob;
    private final Job countBalanceJob;


    @PostMapping("/generate-transactions")
    public void generateTransactions(@RequestParam int generateTransactionSize) throws JobExecutionException {
        JobParameters parameters = new JobParametersBuilder()
                .addJobParameter(GENERATE_TRANSACTION_SIZE, generateTransactionSize, Integer.class)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(insertTransactionJob, parameters);
    }

    @PostMapping("/generate-report")
    public void generateReport() throws JobExecutionException{
        JobParameters parameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(countBalanceJob, parameters);
    }


}
