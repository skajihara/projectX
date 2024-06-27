package skajihara.projectX.MainContents.Home.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

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
    public void scheduledTweetsJobTest() {
        Job job = batchConfig.scheduledTweetsJob(jobRepository, platformTransactionManager);
        assertNotNull(job, "The scheduledTweetsJob bean should not be null");
    }

    @Test
    public void stepForAllProcessesTest() {
        Step step = batchConfig.stepForAllProcesses(jobRepository, platformTransactionManager);
        assertNotNull(step, "The stepForAllProcesses bean should not be null");
    }
}
