package sg.gov.tech.restaurant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import sg.gov.tech.restaurant.model.PredefinedUser;

@Configuration
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<PredefinedUser> reader() {
        FlatFileItemReader<PredefinedUser> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("predefinedUser.csv")); // CSV in resources
        reader.setLinesToSkip(1); // skip header

        DefaultLineMapper<PredefinedUser> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("userId", "username"); // CSV header names
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<PredefinedUser> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PredefinedUser.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JpaItemWriter<PredefinedUser> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<PredefinedUser> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public ItemProcessor<PredefinedUser, PredefinedUser> processor() {
        return user -> {
            logger.info("CSV line read - userid: {}, username: {}", user.getUserId(), user.getUsername());
            return user; // must return entity to write
        };
    }

    @Bean
    public Step importUsersStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                FlatFileItemReader<PredefinedUser> reader,
                                JpaItemWriter<PredefinedUser> writer,
                                ItemProcessor<PredefinedUser, PredefinedUser> processor) {

        return new StepBuilder("importUsersStep", jobRepository)
                .<PredefinedUser, PredefinedUser>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importUsersJob(JobRepository jobRepository, Step importUsersStep) {
        return new JobBuilder("importUsersJob", jobRepository)
                .start(importUsersStep)
                .build();
    }


}
