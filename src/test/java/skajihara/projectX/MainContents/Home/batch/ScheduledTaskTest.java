package skajihara.projectX.MainContents.Home.batch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import skajihara.projectX.MainContents.Home.entity.BatchHistory;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.BatchHistoryRepository;
import skajihara.projectX.MainContents.Home.repository.ScheduledTweetRepository;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScheduledTaskTest {

    @Autowired
    private ScheduledTweetRepository scheduledTweetRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private BatchHistoryRepository batchHistoryRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job scheduledTweetsJob;

    private ScheduledTask scheduledTask;

    @BeforeEach
    public void setUp() {
        scheduledTask = new ScheduledTask(jobLauncher, scheduledTweetsJob);
    }

    @Test
    @Sql(scripts = "classpath:reset-database.sql")
    public void scheduledTweetsJobRunsAtScheduledTime() throws Exception {

        // テスト用データの設定
        ScheduledTweet scheduledTweet = new ScheduledTweet();
        scheduledTweet.setAccountId("user_A");
        scheduledTweet.setText("This is a scheduled tweet");
        scheduledTweet.setImage("/src/assets/images/img02.jpg");
        scheduledTweet.setLocation("大阪府");
        scheduledTweet.setScheduledDatetime(new Date(System.currentTimeMillis() + 10000));
        scheduledTweet.setCreatedDatetime(new Date(System.currentTimeMillis()));
        scheduledTweet.setDeleteFlag(false);
        scheduledTweetRepository.save(scheduledTweet);

        // 予定日時以前にジョブを手動で実行
        scheduledTask.performScheduledTweetsJob();

        // 最初のジョブ実行結果を検証
        BatchHistory history = batchHistoryRepository.findLatest();
        assertNotNull(history);
        assertEquals(1, history.getId());
        assertEquals(0, history.getLastProcessedTweetId());
        assertEquals(0, history.getProcessedNum());
        assertNotNull(history.getExecutionStart());
        assertNotNull(history.getExecutionEnd());
        assertTrue(history.isSucceeded());

        ScheduledTweet beforeProcessed = scheduledTweetRepository.selectScheduledTweet(scheduledTweet.getId());
        assertNotNull(beforeProcessed);
        assertFalse(beforeProcessed.isDeleteFlag());

        List<Tweet> tweets = tweetRepository.selectAll();
        assertEquals(0, tweets.size());

        // 予定日時以降にジョブを手動で実行
        Thread.sleep(10000);
        scheduledTask.performScheduledTweetsJob();

        // 2回目のジョブ実行結果を検証
        history = batchHistoryRepository.findLatest();
        assertNotNull(history);
        assertEquals(2, history.getId());
        assertEquals(scheduledTweet.getId(), history.getLastProcessedTweetId());
        assertEquals(1, history.getProcessedNum());
        assertNotNull(history.getExecutionStart());
        assertNotNull(history.getExecutionEnd());
        assertTrue(history.isSucceeded());

        ScheduledTweet afterProcessed = scheduledTweetRepository.selectScheduledTweet(scheduledTweet.getId());
        assertNull(afterProcessed);

        tweets = tweetRepository.findAll();
        assertEquals(1, tweets.size());
        Tweet tweet = tweets.get(0);
        assertEquals(tweet.getAccountId(), scheduledTweet.getAccountId());
        assertEquals(tweet.getText(), scheduledTweet.getText());
        assertEquals(tweet.getImage(), scheduledTweet.getImage());
        assertEquals(0, tweet.getLikes());
        assertEquals(0, tweet.getRetweets());
        assertEquals(0, tweet.getReplies());
        assertEquals(0, tweet.getViews());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        assertEquals(dateFormat.format(tweet.getDatetime()), dateFormat.format(scheduledTweet.getScheduledDatetime()));
        assertEquals(tweet.getLocation(), scheduledTweet.getLocation());
        assertFalse(tweet.isDeleteFlag());
    }

    @Test
    public void jobFailureAndRetry() throws Exception {

        ScheduledTweet scheduledTweet = new ScheduledTweet();
        scheduledTweet.setAccountId("user_A");
        scheduledTweet.setText("This is a scheduled tweet");
        scheduledTweet.setImage("/src/assets/images/img02.jpg");
        scheduledTweet.setLocation("大阪府");
        scheduledTweet.setScheduledDatetime(new Date(System.currentTimeMillis() + 10000));
        scheduledTweet.setCreatedDatetime(new Date(System.currentTimeMillis()));
        scheduledTweet.setDeleteFlag(false);
        scheduledTweetRepository.save(scheduledTweet);

        Mockito.doThrow(new RuntimeException("Forced exception"))
                .when(batchHistoryRepository).save(Mockito.any(BatchHistory.class));

        assertThrows(Exception.class, () -> {
            scheduledTask.performScheduledTweetsJob();
        });

        BatchHistory history = batchHistoryRepository.findLatest();
        assertNotNull(history);
        assertEquals(1,history.getId());
        assertEquals(0,history.getLastProcessedTweetId());
        assertEquals(0, history.getProcessedNum());
        assertNotNull(history.getExecutionStart());
        assertNotNull(history.getExecutionEnd());
        assertTrue(history.isSucceeded());

        ScheduledTweet beforeProcessed = scheduledTweetRepository.selectScheduledTweet(scheduledTweet.getId());
        assertNotNull(beforeProcessed);
        assertFalse(beforeProcessed.isDeleteFlag());

        List<Tweet> tweets = tweetRepository.findAll();
        assertEquals(0, tweets.size());
    }

    @Test
    public void jobRetriesAndSucceedsAfterFailure() throws Exception {

        ScheduledTweet scheduledTweet = new ScheduledTweet();
        scheduledTweet.setAccountId("user_A");
        scheduledTweet.setText("This is a scheduled tweet");
        scheduledTweet.setImage("/src/assets/images/img02.jpg");
        scheduledTweet.setLocation("大阪府");
        scheduledTweet.setScheduledDatetime(new Date(System.currentTimeMillis() + 10000));
        scheduledTweet.setCreatedDatetime(new Date(System.currentTimeMillis()));
        scheduledTweet.setDeleteFlag(false);
        scheduledTweetRepository.save(scheduledTweet);

        Mockito.doThrow(new RuntimeException("Forced exception 1"))
                .doThrow(new RuntimeException("Forced exception 2"))
                .when(batchHistoryRepository).save(Mockito.any(BatchHistory.class));

        // 予定日時以前にジョブを手動で実行
        scheduledTask.performScheduledTweetsJob();

        BatchHistory history = batchHistoryRepository.findLatest();
        assertNotNull(history);
        assertEquals(3,history.getId());
        assertEquals(0,history.getLastProcessedTweetId());
        assertEquals(0, history.getProcessedNum());
        assertNotNull(history.getExecutionStart());
        assertNotNull(history.getExecutionEnd());
        assertTrue(history.isSucceeded());

        ScheduledTweet beforeProcessed = scheduledTweetRepository.selectScheduledTweet(scheduledTweet.getId());
        assertNotNull(beforeProcessed);
        assertFalse(beforeProcessed.isDeleteFlag());

        List<Tweet> tweets = tweetRepository.selectAll();
        assertEquals(0, tweets.size());

        // 予定日時以降にジョブを手動で実行
        Thread.sleep(10000);
        scheduledTask.performScheduledTweetsJob();

        history = batchHistoryRepository.findLatest();
        assertNotNull(history);
        assertEquals(6,history.getId());
        assertEquals(1,history.getLastProcessedTweetId());
        assertEquals(1, history.getProcessedNum());
        assertNotNull(history.getExecutionStart());
        assertNotNull(history.getExecutionEnd());
        assertTrue(history.isSucceeded());

        ScheduledTweet afterProcessed = scheduledTweetRepository.selectScheduledTweet(scheduledTweet.getId());
        assertNull(afterProcessed);

        tweets = tweetRepository.findAll();
        assertEquals(1, tweets.size());
        Tweet tweet = tweets.get(0);
        assertEquals(tweet.getAccountId(),scheduledTweet.getAccountId());
        assertEquals(tweet.getText(),scheduledTweet.getText());
        assertEquals(tweet.getImage(),scheduledTweet.getImage());
        assertEquals(tweet.getLikes(),0);
        assertEquals(tweet.getRetweets(),0);
        assertEquals(tweet.getReplies(),0);
        assertEquals(tweet.getViews(),0);
        assertEquals(tweet.getDatetime(),scheduledTweet.getScheduledDatetime());
        assertEquals(tweet.getLocation(),scheduledTweet.getLocation());
        assertFalse(tweet.isDeleteFlag());
    }
}
