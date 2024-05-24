package pl.romczaj.batch.batch.inserttransaction;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import pl.romczaj.batch.batch.CustomBatchProperties;
import pl.romczaj.batch.domain.transaction.UserTransaction;
import pl.romczaj.batch.dto.TransactionInsertDto;

import java.util.Map;

@Configuration
public class TransactionBatchConfiguration {

    @Bean
    @StepScope
    public InsertTransactionItemReader transactionFileStorageItemReader(
            @Value("#{jobParameters}") Map<String, Object> jobParameters,
            CustomBatchProperties customBatchProperties
    ) {
        return new InsertTransactionItemReader(jobParameters, customBatchProperties);
    }

    @Bean
    public InsertTransactionItemProcessor insertTransactionItemProcessor() {
        return new InsertTransactionItemProcessor();
    }

    @Bean
    public JpaItemWriter<UserTransaction> insertTransactionJpaItermWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<UserTransaction> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step insertTransactionMapStep(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager,
                                         InsertTransactionItemReader insertTransactionItemReader,
                                         JpaItemWriter<UserTransaction> insertTransactionJpaItermWriter,
                                         InsertTransactionItemProcessor insertTransactionItemProcessor,
                                         CustomBatchProperties customBatchProperties) {
        return new StepBuilder("insertTransactionMapStep", jobRepository)
                .<TransactionInsertDto, UserTransaction>chunk(customBatchProperties.chunkSize(), transactionManager)
                .reader(insertTransactionItemReader)
                .processor(insertTransactionItemProcessor)
                .writer(insertTransactionJpaItermWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public InsertTransactionJobExecutionListener insertTransactionJobExecutionListener() {
        return new InsertTransactionJobExecutionListener();
    }

    @Bean
    public Job insertTransactionJob(JobRepository jobRepository, Step insertTransactionMapStep,
                                    InsertTransactionJobExecutionListener insertTransactionJobExecutionListener) {
        return new JobBuilder("insertTransactionJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(insertTransactionJobExecutionListener)
                .flow(insertTransactionMapStep)
                .end()
                .build();
    }


}
