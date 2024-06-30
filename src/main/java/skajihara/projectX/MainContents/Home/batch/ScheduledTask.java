package skajihara.projectX.MainContents.Home.batch;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private final JobLauncher jobLauncher;
    private final Job scheduledTweetsJob;

//    @Scheduled(cron = "*/10 * * * * *")
    public void performScheduledTweetsJob() throws Exception {
        System.out.println("Scheduled Task running!");
        try {
            logger.info("Scheduled tweets job started at: {}", new Date());
            jobLauncher.run(scheduledTweetsJob, new JobParametersBuilder()
                    .addLong("Job Key", System.currentTimeMillis())
                    .toJobParameters());
        }catch (Exception e) {
            logger.error("Scheduled tweets job failed", e);
            throw e;
        }finally{
            logger.info("Scheduled tweets job ended at: {}", new Date());
        }
    }
}
