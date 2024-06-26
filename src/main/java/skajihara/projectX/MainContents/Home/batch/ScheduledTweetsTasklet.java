package skajihara.projectX.MainContents.Home.batch;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import skajihara.projectX.MainContents.Home.entity.BatchHistory;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.BatchHistoryRepository;
import skajihara.projectX.MainContents.Home.repository.ScheduledTweetRepository;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTweetsTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTweetsTasklet.class);

    @Autowired
    private ScheduledTweetRepository scheduledTweetRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private BatchHistoryRepository batchHistoryRepository;

    @Override
    @Retryable(retryFor= Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        Date now = new Date();
        BatchHistory lastHistory = batchHistoryRepository.findLatest();
        int lastProcessedTweetId = (lastHistory != null) ? lastHistory.getLastProcessedTweetId() : 0;

        BatchHistory newHistory = new BatchHistory();
        newHistory.setLastProcessedTweetId(lastProcessedTweetId);
        newHistory.setExecutionStart(now);
        newHistory.setSucceeded(false);
        batchHistoryRepository.save(newHistory);

        try {
            logger.info("Scheduled tweets task started at: {}", now);
            List<ScheduledTweet> scheduledTweets = scheduledTweetRepository
                    .selectForScheduledTweetBatch(lastProcessedTweetId, now);

            for (ScheduledTweet scheduledTweet : scheduledTweets) {
                Tweet tweet = new Tweet();
                tweet.setAccountId(scheduledTweet.getAccountId());
                tweet.setText(scheduledTweet.getText());
                tweet.setImage(scheduledTweet.getImage());
                tweet.setLocation(scheduledTweet.getLocation());
                tweet.setDatetime(scheduledTweet.getScheduledDatetime());
                tweet.setDeleteFlag(false);
                tweetRepository.save(tweet);

                scheduledTweet.setDeleteFlag(true);
                scheduledTweetRepository.save(scheduledTweet);

                lastProcessedTweetId = scheduledTweet.getId();
            }

            newHistory.setLastProcessedTweetId(lastProcessedTweetId);
            newHistory.setSucceeded(true);
            logger.info("Scheduled tweets task completed successfully");

        } catch (Exception e) {
            newHistory.setLastProcessedTweetId(lastHistory.getLastProcessedTweetId());
            newHistory.setSucceeded(false);
            logger.error("Scheduled tweets task failed", e);
            throw e;
        } finally {
            newHistory.setExecutionEnd(new Date());
            batchHistoryRepository.save(newHistory);
            logger.info("Scheduled tweets task ended at: {}", newHistory.getExecutionEnd());
        }

        return RepeatStatus.FINISHED;
    }

    @Recover
    public RepeatStatus recover(Exception e) {
        logger.info(e.getMessage());
        return RepeatStatus.FINISHED;
    }
}
