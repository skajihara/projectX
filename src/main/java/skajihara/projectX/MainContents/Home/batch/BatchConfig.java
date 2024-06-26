package skajihara.projectX.MainContents.Home.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public Job scheduledTweetsJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("scheduledTweetsJob メソッドを実行");
        return new JobBuilder("scheduledTweetsJob", jobRepository)
                .start(stepForAllProcesses(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step stepForAllProcesses(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("stepForAllProcesses メソッドを実行");
        return new StepBuilder("stepForAllProcesses", jobRepository)
                .tasklet(new ScheduledTweetsTasklet("change here!"), platformTransactionManager)
                .build();
    }
}
