package skajihara.projectX.MainContents.Home.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import skajihara.projectX.MainContents.Home.entity.BatchHistory;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.BatchHistoryRepository;
import skajihara.projectX.MainContents.Home.repository.ScheduledTweetRepository;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTweetTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTweetTasklet.class);
    private static final int MAX_ATTEMPTS = 3;

    @Autowired
    private ScheduledTweetRepository scheduledTweetRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private BatchHistoryRepository batchHistoryRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Date now = new Date();
        BatchHistory lastHistory = batchHistoryRepository.findTopByOrderByIdDesc();
        int lastProcessedTweetId = (lastHistory != null) ? lastHistory.getLastProcessedTweetId() : 0;

        BatchHistory newHistory = new BatchHistory();
        newHistory.setLastProcessedTweetId(lastProcessedTweetId);
        newHistory.setExecutionStart(now);
        newHistory.setSucceeded(false);
        batchHistoryRepository.save(newHistory);

        boolean success = false;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS && !success) {
            attempts++;
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = transactionManager.getTransaction(def);

            try {
                logger.info("Batch job attempt {} started at {}", attempts, now);
                List<ScheduledTweet> scheduledTweets = scheduledTweetRepository
                        .selectForScheduledTweetBatch(lastProcessedTweetId, now);

                for (ScheduledTweet scheduledTweet : scheduledTweets) {
                    Tweet tweet = new Tweet();
                    tweet.setAccountId(scheduledTweet.getAccountId());
                    tweet.setText(scheduledTweet.getText());
                    tweet.setImage(scheduledTweet.getImage());
                    tweet.setLocation(scheduledTweet.getLocation());
                    tweet.setDatetime(scheduledTweet.getScheduledDatetime());
                    tweetRepository.save(tweet);

                    scheduledTweet.setDeleteFlag(true);
                    scheduledTweetRepository.save(scheduledTweet);

                    lastProcessedTweetId = scheduledTweet.getId();
                }

                newHistory.setLastProcessedTweetId(lastProcessedTweetId);
                newHistory.setSucceeded(true);
                transactionManager.commit(status);
                success = true;
                logger.info("Batch job attempt {} completed successfully", attempts);
            } catch (Exception e) {
                transactionManager.rollback(status);
                logger.error("Batch job attempt {} failed", attempts, e);
                if (attempts >= MAX_ATTEMPTS) {
                    newHistory.setLastProcessedTweetId(lastHistory.getLastProcessedTweetId());
                    newHistory.setSucceeded(false);
                    throw e;
                }
            }
        }

        newHistory.setExecutionEnd(new Date());
        batchHistoryRepository.save(newHistory);
        logger.info("Batch job ended at {}", newHistory.getExecutionEnd());

        return RepeatStatus.FINISHED;
    }
}