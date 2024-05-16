package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TweetServiceTest {

    @Autowired
    TweetService tweetService;

    @MockBean
    TweetRepository tweetRepository;

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
}



