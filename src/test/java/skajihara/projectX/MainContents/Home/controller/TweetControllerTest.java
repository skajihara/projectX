package skajihara.projectX.MainContents.Home.controller;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        when(tweetService.selectAllTweets()).thenReturn(new ArrayList<>());

        // GETリクエストを"/api/tweets"に送信してステータスが200 OKであることを確認
        mockMvc.perform(get("/api/tweets")).andExpect(status().isOk());

        //　selectAllTweetsメソッドが1回だけ呼び出されたことを確認
        verify(tweetService, times(1)).selectAllTweets();
    }

    @Test
    public void getRecentTweetsUnitTest() throws Exception {
        // tweetService.selectRecentTweets(10)が空のリストを返すようにモックを設定
        when(tweetService.selectRecentTweets(anyInt())).thenReturn(new ArrayList<>());

        // GETリクエストを"/api/tweets/recent"に送信してステータスが200 OKであることを確認。
        mockMvc.perform(get("/api/tweets/recent")).andExpect(status().isOk());

        // tweetService.selectRecentTweets(10)が1回だけ呼び出されたことを確認。
        verify(tweetService, times(1)).selectRecentTweets(anyInt());
    }
}






