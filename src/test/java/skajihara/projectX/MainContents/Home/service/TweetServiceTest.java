package skajihara.projectX.MainContents.Home.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import skajihara.projectX.MainContents.Home.exception.TweetException;
import skajihara.projectX.MainContents.Home.util.CsvLoader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TweetServiceTest {

    @Autowired
    TweetService tweetService;

    @Autowired
    private CsvLoader csvLoader;

    @SpyBean
    TweetRepository tweetRepository;

    @SpyBean
    Tweet tweet;

    @Test
    void selectAllTweetsUnitTest() {

        List<Tweet> expected = new ArrayList<>();

        doReturn(new ArrayList<Tweet>()).when(tweetRepository).selectAll();

        List<Tweet> result = tweetService.selectAllTweets();

        verify(tweetRepository, times(1)).selectAll();
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void selectRecentTweetsUnitTest() {

        List<Tweet> expected = new ArrayList<>();

        doReturn(new ArrayList<Tweet>()).when(tweetRepository).selectRecentN(anyInt());

        List<Tweet> result = tweetService.selectRecentTweets(anyInt());

        verify(tweetRepository, times(1)).selectRecentN(anyInt());
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void createTweetUnitTest() {

        doReturn(tweet).when(tweetRepository).save(any(Tweet.class));

        tweetService.createTweet(tweet);

        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    void updateTweetUnitTest() {

        int id = 1;
        Tweet tweet = new Tweet();
        Tweet existingTweet = new Tweet();

        existingTweet.setId(id);
        doReturn(Optional.of(existingTweet)).when(tweetRepository).findById(id);
        doReturn(tweet).when(tweetRepository).save(any(Tweet.class));

        tweetService.updateTweet(id, tweet);

        verify(tweetRepository, times(1)).findById(id);
        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    void updateTweetExceptionUnitTest() {

        int id = 1;
        Tweet tweet = new Tweet();

        doReturn(Optional.empty()).when(tweetRepository).findById(id);

        assertThrows(TweetException.class, () -> tweetService.updateTweet(id, tweet));

        verify(tweetRepository, times(1)).findById(id);
        verify(tweetRepository, times(0)).save(any(Tweet.class));
    }

    @Test
    void deleteTweetUnitTest() {

        int id = 1;
        Tweet tweet = new Tweet();

        doReturn(Optional.of(tweet)).when(tweetRepository).findById(id);
        doNothing().when(tweetRepository).delete(any(Tweet.class));

        tweetService.deleteTweet(id);

        verify(tweetRepository, times(1)).findById(id);
        verify(tweetRepository, times(1)).delete(any(Tweet.class));
    }

    @Test
    void deleteTweetExceptionUnitTest() {

        int id = 1;

        doReturn(Optional.empty()).when(tweetRepository).findById(id);

        assertThrows(TweetException.class, () -> tweetService.deleteTweet(id));

        verify(tweetRepository, times(1)).findById(id);
        verify(tweetRepository, times(0)).delete(any(Tweet.class));
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/selectAllTweetsIntegrationTest.sql"})
    public void selectAllTweetsTestIntegrationTest() {
        List<Tweet> tweets = tweetService.selectAllTweets();
        assertThat(tweets).hasSize(10);
    }

    @Test
    void selectRecentTweetsIntegrationTest() throws ParseException {

        csvLoader.loadTweets("src/test/resources/csv/selectRecentTweetsIntegrationTest.csv");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = dateFormat.parse("2024-03-30 01:04:43");
        Date date2 = dateFormat.parse("2024-03-29 15:30:11");
        Date date3 = dateFormat.parse("2024-03-18 20:10:01");

        List<Tweet> tweets = tweetService.selectRecentTweets(10);
        assertThat(tweets).hasSize(10);

//        for(Tweet tweet:tweets){
//            System.out.print(tweet.getId());
//            System.out.print(", ");
//            System.out.print(tweet.getDatetime());
//            System.out.print(", ");
//            System.out.print(tweet.getAccountId());
//            System.out.print(", ");
//            System.out.print(tweet.getText());
//            System.out.print(", ");
//            System.out.print(tweet.getImage());
//            System.out.print(", ");
//            System.out.println(tweet.getLocation());
//        }

        assertThat(tweets.get(0).getDatetime().getTime()).isEqualTo(date1.getTime());
        assertThat(tweets.get(1).getDatetime().getTime()).isEqualTo(date2.getTime());
        assertThat(tweets.get(2).getDatetime().getTime()).isEqualTo(date3.getTime());
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/createTweetIntegrationTest.sql"})
    void createTweetIntegrationTest() {

        Date date = new Date(System.currentTimeMillis());

        Tweet newTweet = new Tweet();
        newTweet.setAccountId("user_A");
        newTweet.setText("This is a test tweet.");
        newTweet.setImage("/src/assets/images/img01.GIF");
        newTweet.setLikes(999);
        newTweet.setRetweets(999);
        newTweet.setReplies(999);
        newTweet.setViews(999);
        newTweet.setDatetime(date);
        newTweet.setLocation("Test Location.");
        newTweet.setDeleteFlag(false);

        tweetService.createTweet(newTweet);

        List<Tweet> tweets = tweetService.selectRecentTweets(3);
        assertThat(tweets).hasSize(3);
        assertThat(tweets.get(0).getAccountId()).isEqualTo(newTweet.getAccountId());
        assertThat(tweets.get(0).getText()).isEqualTo(newTweet.getText());
        assertThat(tweets.get(0).getImage()).isEqualTo(newTweet.getImage());
        assertThat(tweets.get(0).getLikes()).isEqualTo(newTweet.getLikes());
        assertThat(tweets.get(0).getRetweets()).isEqualTo(newTweet.getRetweets());
        assertThat(tweets.get(0).getReplies()).isEqualTo(newTweet.getReplies());
        assertThat(tweets.get(0).getViews()).isEqualTo(newTweet.getViews());
        assertThat(tweets.get(0).getDatetime().getTime()).isEqualTo(newTweet.getDatetime().getTime());
        assertThat(tweets.get(0).getLocation()).isEqualTo(newTweet.getLocation());
        assertThat(tweets.get(0).isDeleteFlag()).isEqualTo(newTweet.isDeleteFlag());
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/createInvalidTweetIntegrationTest.sql"})
    void createInvalidTweetIntegrationTest() {
        Tweet invalidTweet = new Tweet();
        assertThrows(Exception.class, () -> tweetService.createTweet(invalidTweet));
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/updateTweetIntegrationTest.sql"})
    void updateTweetIntegrationTest() {

        List<Tweet> beforeTweets =tweetService.selectRecentTweets(3);
        assertThat(beforeTweets).hasSize(3);
        beforeTweets.get(0).setText("updated!");

        tweetService.updateTweet(beforeTweets.get(0).getId(), beforeTweets.get(0));

        List<Tweet> afterTweets =tweetService.selectRecentTweets(3);
        assertThat(afterTweets).hasSize(3);
        assertThat(afterTweets.get(0).getAccountId()).isEqualTo(beforeTweets.get(0).getAccountId());
        assertThat(afterTweets.get(0).getText()).isEqualTo(beforeTweets.get(0).getText());
        assertThat(afterTweets.get(0).getImage()).isEqualTo(beforeTweets.get(0).getImage());
        assertThat(afterTweets.get(0).getLikes()).isEqualTo(beforeTweets.get(0).getLikes());
        assertThat(afterTweets.get(0).getRetweets()).isEqualTo(beforeTweets.get(0).getRetweets());
        assertThat(afterTweets.get(0).getReplies()).isEqualTo(beforeTweets.get(0).getReplies());
        assertThat(afterTweets.get(0).getViews()).isEqualTo(beforeTweets.get(0).getViews());
        assertThat(afterTweets.get(0).getDatetime()).isEqualTo(beforeTweets.get(0).getDatetime());
        assertThat(afterTweets.get(0).getLocation()).isEqualTo(beforeTweets.get(0).getLocation());
        assertThat(afterTweets.get(0).isDeleteFlag()).isEqualTo(beforeTweets.get(0).isDeleteFlag());
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/updateTweetExceptionIntegrationTest.sql"})
    void updateTweetExceptionIntegrationTest() {

        List<Tweet> tweets =tweetService.selectRecentTweets(3);
        assertThat(tweets).hasSize(3);
        tweets.get(0).setText("updated!");

        assertThrows(TweetException.class, () -> tweetService.updateTweet(99999, tweets.get(0)));
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/deleteTweetIntegrationTest.sql"})
    public void deleteTweetIntegrationTest() throws Exception {

        List<Tweet> beforeTweets =tweetService.selectRecentTweets(3);
        assertThat(beforeTweets).hasSize(3);

        tweetService.deleteTweet(beforeTweets.get(0).getId());

        List<Tweet> afterTweets =tweetService.selectRecentTweets(3);
        assertThat(afterTweets).hasSize(3);
        assertThat(afterTweets.get(0)).isEqualTo(beforeTweets.get(1));
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/deleteTweetExceptionIntegrationTest.sql"})
    void deleteTweetExceptionIntegrationTest() {
        assertThrows(TweetException.class, () -> tweetService.deleteTweet(99999));
    }

    @Test
    @Sql(scripts = {"classpath:sql/service/performanceTest.sql"})
    void performanceTest() {

        long startTime = System.currentTimeMillis();
        tweetService.selectAllTweets();
        long endTime = System.currentTimeMillis();

        assertTrue((endTime - startTime) < 1000, "Response is too slow");
    }
}

