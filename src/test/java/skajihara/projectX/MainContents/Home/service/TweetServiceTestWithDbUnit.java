package skajihara.projectX.MainContents.Home.service;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import skajihara.projectX.MainContents.Home.entity.Tweet;
//import skajihara.projectX.util.CsvDataSetLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class TweetServiceTestWithDbUnit {
    @Autowired private TweetService tweetService;

    @Test
    public void selectAllTweetTest() {
        List<Tweet> tweetList = tweetService.selectAllTweets();
        assertThat(tweetList).hasSize(10);
    }
}