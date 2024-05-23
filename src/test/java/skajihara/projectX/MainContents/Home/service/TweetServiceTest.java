package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import skajihara.projectX.MainContents.Home.exception.TweetException;

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
    public void selectAllTweetsTestIntegrationTest() {
        List<Tweet> tweets = tweetService.selectAllTweets();
        assertThat(tweets).hasSize(10);
    }

    @Test
    void selectRecentTweetsIntegrationTest() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = dateFormat.parse("2024-03-30 01:04:43");
        Date date2 = dateFormat.parse("2024-03-29 15:30:11");
        Date date3 = dateFormat.parse("2024-03-18 20:10:01");

        List<Tweet> tweets = tweetService.selectRecentTweets(3);
        assertThat(tweets).hasSize(3);
        assertThat(tweets.get(0).getDatetime().getTime()).isEqualTo(date1.getTime());
        assertThat(tweets.get(1).getDatetime().getTime()).isEqualTo(date2.getTime());
        assertThat(tweets.get(2).getDatetime().getTime()).isEqualTo(date3.getTime());
    }

    @Test
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
}

