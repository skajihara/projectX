package skajihara.projectX.MainContents.Home.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TweetService tweetService;

    @Test
    public void getAllTweetsUnitTest() throws Exception {
        // tweetService.selectAllTweets()が空のリストを返すようにモックを設定
        doReturn(new ArrayList<>()).when(tweetService).selectAllTweets();

        // GETリクエストを"/api/tweets"に送信してステータスが200 OKであることを確認
        mockMvc.perform(get("/api/tweets")).andExpect(status().isOk());

        //　selectAllTweetsメソッドが1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).selectAllTweets();
    }

    @Test
    public void getRecentTweetsUnitTest() throws Exception {
        // tweetService.selectRecentTweets()が空のリストを返すようにモックを設定
        doReturn(new ArrayList<>()).when(tweetService).selectRecentTweets(anyInt());

        // GETリクエストを"/api/tweets/recent"に送信してステータスが200 OKであることを確認。
        mockMvc.perform(get("/api/tweets/recent")).andExpect(status().isOk());

        // tweetService.selectRecentTweets()が1回だけ呼び出されたことを確認。
        verify(tweetService, times(1)).selectRecentTweets(anyInt());
    }

    @Test
    public void createTweetUnitTest() throws Exception {

        // tweetService.createTweet()が何も返さないようにモックを設定
        doNothing().when(tweetService).createTweet(any());

        // POSTリクエストを"/api/tweets"に送信してステータスが200 OKであることを確認
        mockMvc.perform(post("/api/tweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        // tweetService.createTweet()が1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).createTweet(any());
    }

    @Test
    public void updateTweetUnitTest() throws Exception {

        // tweetService.updateTweet()が何も返さないようにモックを設定
        doNothing().when(tweetService).updateTweet(anyInt(),any());

        // PUTリクエストを"/api/tweets/{id}"に送信してステータスが200 OKであることを確認
        mockMvc.perform(put("/api/tweets/{id}",123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        // tweetService.updateTweet()が1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).updateTweet(anyInt(),any());
    }

    @Test
    public void deleteTweetUnitTest() throws Exception {

        // tweetService.deleteTweet()が何も返さないようにモックを設定
        doNothing().when(tweetService).deleteTweet(anyInt());

        // DELETEリクエストを"/api/tweets/{id}"に送信してステータスが200 OKであることを確認
        mockMvc.perform(delete("/api/tweets/{id}",123)).andExpect(status().isOk());

        // tweetService.deleteTweet()が1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).deleteTweet(anyInt());
    }
}
