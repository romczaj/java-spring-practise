package pl.romczaj.batch.batch.countbalance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CountBalanceJobExecutionListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job finished with status: {}", jobExecution.getStatus());
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job started");
    }
}
