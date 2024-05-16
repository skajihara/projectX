package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import skajihara.projectX.MainContents.Home.exception.TweetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TweetServiceTest {

    @Autowired
    TweetService tweetService;

    @MockBean
    TweetRepository tweetRepository;

    @MockBean
    Tweet tweet;

    @Test
    void selectAllTweetsUnitTest() {

        List<Tweet> expected = new ArrayList<>();

        // tweetRepository.selectAllメソッドが空のTweet型リストを返すようにモックを設定
        doReturn(new ArrayList<Tweet>()).when(tweetRepository).selectAll();

        // tweetService.selectAllTweetsメソッドの呼び出し
        List<Tweet> result = tweetService.selectAllTweets();

        //　tweetRepository.selectAllメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).selectAll();
        // tweetService.selectAllTweetsメソッドがTweet型リストを返すことを確認
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void selectRecentTweetsUnitTest() {

        List<Tweet> expected = new ArrayList<>();

        // tweetRepository.selectRecentNメソッドが空のTweet型リストを返すようにモックを設定
        doReturn(new ArrayList<Tweet>()).when(tweetRepository).selectRecentN(anyInt());

        // tweetService.selectRecentTweetsメソッドの呼び出し
        List<Tweet> result = tweetService.selectRecentTweets(anyInt());

        //　tweetRepository.selectRecentNメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).selectRecentN(anyInt());
        // tweetService.selectRecentTweetsメソッドがTweet型リストを返すことを確認
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void createTweetUnitTest() {

        // tweetRepository.saveがTweetオブジェクトを返すようにモックを設定
        doReturn(tweet).when(tweetRepository).save(any(Tweet.class));

        // tweetService.createTweetsメソッドの呼び出し
        tweetService.createTweet(tweet);

        //　tweetRepository.saveメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    void updateTweetUnitTest() {

        int id = 1;
        Tweet tweet = new Tweet();
        Tweet existingTweet = new Tweet();

        // tweetRepository.findById(id)がOptional<Tweet>オブジェクトを返すようにモックを設定
        existingTweet.setId(id);
        doReturn(Optional.of(existingTweet)).when(tweetRepository).findById(id);
        // tweetRepository.saveがTweetオブジェクトを返すようにモックを設定
        doReturn(tweet).when(tweetRepository).save(any(Tweet.class));

        // tweetService.updateTweet()の呼び出し
        tweetService.updateTweet(id, tweet);

        // tweetRepository.findByIdメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).findById(id);
        // tweetRepository.saveメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    void updateTweetNotFoundUnitTest() {

        int id = 1;
        Tweet tweet = new Tweet();

        // tweetRepository.findById(id)がOptional.empty()を返すようにモックを設定
        doReturn(Optional.empty()).when(tweetRepository).findById(id);

        // tweetService.updateTweet()がTweetExceptionをスローすることを確認
        assertThrows(TweetException.class, () -> tweetService.updateTweet(id, tweet));

        // tweetRepository.findByIdメソッドが1回だけ呼び出されたことを確認
        verify(tweetRepository, times(1)).findById(id);
        // tweetRepository.saveメソッドは呼び出されないことを確認
        verify(tweetRepository, times(0)).save(any(Tweet.class));
    }
}

