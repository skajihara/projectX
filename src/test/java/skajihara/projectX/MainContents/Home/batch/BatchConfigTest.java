package skajihara.projectX.MainContents.Home.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BatchConfigTest {

    @Autowired
    private BatchConfig batchConfig;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Test
    public void testScheduledTweetsJobBean() {
        Job job = batchConfig.scheduledTweetsJob(jobRepository, platformTransactionManager);
        assertNotNull(job, "The scheduledTweetsJob bean should not be null");
    }

    @Test
    public void testStepForAllProcessesBean() {
        Step step = batchConfig.stepForAllProcesses(jobRepository, platformTransactionManager);
        assertNotNull(step, "The stepForAllProcesses bean should not be null");
    }

    @Test
    public void testJobNames() {
        List<String> jobNames = jobRepository.getJobNames();
        System.out.println("Registered job names: " + jobNames);
        // 期待するジョブが登録されているかを確認
    }
}
