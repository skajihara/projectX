package skajihara.projectX.MainContents.Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    TweetService tweetService;

    @Test
    public void getAllTweetsUnitTest() throws Exception {

        doReturn(new ArrayList<>()).when(tweetService).selectAllTweets();

        mockMvc.perform(get("/api/tweets")).andExpect(status().isOk());

        verify(tweetService, times(1)).selectAllTweets();
    }

    @Test
    public void getRecentTweetsUnitTest() throws Exception {

        doReturn(new ArrayList<>()).when(tweetService).selectRecentTweets(anyInt());

        mockMvc.perform(get("/api/tweets/recent")).andExpect(status().isOk());

        verify(tweetService, times(1)).selectRecentTweets(anyInt());
    }

    @Test
    public void createTweetUnitTest() throws Exception {

        doNothing().when(tweetService).createTweet(any());

        mockMvc.perform(post("/api/tweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(tweetService, times(1)).createTweet(any());
    }

    @Test
    public void updateTweetUnitTest() throws Exception {

        doNothing().when(tweetService).updateTweet(anyInt(),any());

        mockMvc.perform(put("/api/tweets/{id}",123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(tweetService, times(1)).updateTweet(anyInt(),any());
    }

    @Test
    public void deleteTweetUnitTest() throws Exception {

        doNothing().when(tweetService).deleteTweet(anyInt());

        mockMvc.perform(delete("/api/tweets/{id}",123)).andExpect(status().isOk());

        verify(tweetService, times(1)).deleteTweet(anyInt());
    }

    @Test
    public void getAllTweetsIntegrationTest() throws Exception {

        String response = mockMvc.perform(get("/api/tweets"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(10))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> tweets = Arrays.asList(objectMapper.readValue(response, Tweet[].class));
        assertThat(tweets).hasSize(10);
    }

    @Test
    public void getRecentTweetsIntegrationTest() throws Exception {

        String response =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].datetime").value("2024-03-30T01:04:43.000+00:00"))
                .andExpect(jsonPath("$[1].datetime").value("2024-03-29T15:30:11.000+00:00"))
                .andExpect(jsonPath("$[2].datetime").value("2024-03-18T20:10:01.000+00:00"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> tweets = Arrays.asList(objectMapper.readValue(response, Tweet[].class));
        assertThat(tweets).hasSize(3);
        assertThat(tweets.get(0).getDatetime()).isEqualTo("2024-03-30T01:04:43.000+00:00");
        assertThat(tweets.get(1).getDatetime()).isEqualTo("2024-03-29T15:30:11.000+00:00");
        assertThat(tweets.get(2).getDatetime()).isEqualTo("2024-03-18T20:10:01.000+00:00");
    }
}
