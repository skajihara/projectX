package skajihara.projectX.MainContents.Home.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SimpleScheduledBatchConfig {

    private final JobLauncher jobLauncher;
    private final Job myjob;

    @Scheduled(fixedRate = 10000)
    public void performJob() throws Exception {
        System.out.println("Scheduled task running");
        jobLauncher.run(myjob, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters());
    }
}
