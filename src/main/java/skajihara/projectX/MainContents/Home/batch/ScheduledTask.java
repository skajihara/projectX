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
public class ScheduledTask {

    private final JobLauncher jobLauncher;
    private final Job scheduledTweetsJob;

    @Scheduled(cron = "*/10 * * * * *")
    public void performJob() throws Exception {
        System.out.println("Scheduled tweets task running!");
        jobLauncher.run(scheduledTweetsJob, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters());
    }
}
