package skajihara.projectX.MainContents.Home.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TweetService tweetService;

    @Test
    public void getAllTweetsUnitTest() throws Exception {
        // tweetService.selectAllTweets()が空のリストを返すようにモックを設定
        when(tweetService.selectAllTweets()).thenReturn(new ArrayList<>());

        // GETリクエストを"/api/tweets"に送信してステータスが200 OKであることを確認
        mockMvc.perform(get("/api/tweets")).andExpect(status().isOk());

        //　selectAllTweetsメソッドが1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).selectAllTweets();
    }

    @Test
    public void getRecentTweetsUnitTest() throws Exception {
        // tweetService.selectRecentTweets()が空のリストを返すようにモックを設定
        when(tweetService.selectRecentTweets(anyInt())).thenReturn(new ArrayList<>());

        // GETリクエストを"/api/tweets/recent"に送信してステータスが200 OKであることを確認
        mockMvc.perform(get("/api/tweets/recent")).andExpect(status().isOk());

        // tweetService.selectRecentTweets(10)が1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).selectRecentTweets(anyInt());
    }

    @Test
    public void createTweetUnitTest() throws Exception {

//        Tweet tweet = new Tweet();
//        tweet.setId(1);
//        tweet.setAccountId("user_A");
//        tweet.setText("富山のホタルイカ、最高");
//        tweet.setImage("/src/assets/images/img02.jpg");
//        tweet.setLikes(100);
//        tweet.setRetweets(30);
//        tweet.setReplies(20);
//        tweet.setViews(10);
//        tweet.setDatetime(null);
//        tweet.setLocation("富山県滑川市");
//        tweet.setDeleteFlag(false);

        // tweetService.createTweet()が何も返さないようにモックを設定
        doNothing().when(tweetService).createTweet(any());

        // POSTリクエストを"/api/tweets"に送信してステータスが200 OKであることを確認
        mockMvc.perform(post("/api/tweets")
                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(tweet)))
                        .content(JSON))
                .andExpect(status().isOk());

        verify(tweetService, times(1)).createTweet(any());
    }
}






