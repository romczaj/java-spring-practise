package pl.romczaj.batch.batch.countbalance;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import pl.romczaj.batch.domain.accountreport.AccountReport;
import pl.romczaj.batch.domain.accountreport.AccountReportRepository;
import pl.romczaj.batch.domain.transaction.UserTransactionRepository;
import pl.romczaj.batch.dto.CountBalanceDto;

@Configuration
public class CountBalanceConfiguration {

    @Bean
    public CountBalanceItemReader countBalanceItemReader(UserTransactionRepository userTransactionRepository) {
        return new CountBalanceItemReader(userTransactionRepository);
    }


    @Bean
    public CountBalanceItemProcessor countBalanceItemProcessor() {
        return new CountBalanceItemProcessor();
    }

    @Bean
    public CountBalanceItemWriter accountReportItemWriter(AccountReportRepository accountReportRepository) {
        return new CountBalanceItemWriter(accountReportRepository);
    }

    @Bean
    public Step accountReportMapStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                     CountBalanceItemReader countBalanceItemReader,
                                     CountBalanceItemWriter accountReportItemWriter,
                                     CountBalanceItemProcessor countBalanceItemProcessor) {
        return new StepBuilder("accountReportMapStep", jobRepository)
                .<CountBalanceDto, CountBalanceDto>chunk(10, transactionManager)
                .chunk(10)
                .reader(countBalanceItemReader)
                .processor(countBalanceItemProcessor)
                .writer(accountReportItemWriter)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public CountBalanceJobExecutionListener countBalanceJobExecutionListener() {
        return new CountBalanceJobExecutionListener();
    }

    @Bean
    public Job countBalanceJob(JobRepository jobRepository, Step accountReportMapStep,
                               CountBalanceJobExecutionListener countBalanceJobExecutionListener) {
        return new JobBuilder("countBalanceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(countBalanceJobExecutionListener)
                .flow(accountReportMapStep)
                .end()
                .build();
    }


}
