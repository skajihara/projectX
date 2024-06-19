package skajihara.projectX.MainContents.Home.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ScheduledTweetTasklet scheduledTweetTasklet;

    @Bean
    public Job scheduledTweetJob() {
        JobBuilder jobBuilder = new JobBuilder("scheduledTweetJob", jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        StepBuilder stepBuilder = new StepBuilder("step1", jobRepository);
        return stepBuilder
                .tasklet(scheduledTweetTasklet, transactionManager)
                .build();
    }

    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional
    public void perform() throws Exception {
        logger.info("Scheduled Tweet batch job triggered");
        try {
            jobLauncher.run(scheduledTweetJob(), new JobParameters());
        } catch (Exception e) {
            logger.error("Scheduled Tweet batch job failed", e);
        }
    }
}