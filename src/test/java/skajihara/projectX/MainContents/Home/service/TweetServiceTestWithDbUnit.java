package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skajihara.projectX.MainContents.Home.entity.Tweet;
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