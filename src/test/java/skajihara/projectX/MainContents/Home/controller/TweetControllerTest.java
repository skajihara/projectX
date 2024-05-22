package skajihara.projectX.MainContents.Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TweetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    @Test
    public void createTweetIntegrationTest() throws Exception {

        Date date = new Date(System.currentTimeMillis());

        Tweet newTweet = new Tweet();
        newTweet.setAccountId("user_A");
        newTweet.setText("これはテストツイートです。");
        newTweet.setImage("/src/assets/images/img01.GIF");
        newTweet.setLikes(999);
        newTweet.setRetweets(999);
        newTweet.setReplies(999);
        newTweet.setViews(999);
        newTweet.setDatetime(date);
        newTweet.setLocation("テストロケーション");
        newTweet.setDeleteFlag(false);

        mockMvc.perform(post("/api/tweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTweet)))
                .andExpect(status().isOk());

        String response =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> tweets = Arrays.asList(objectMapper.readValue(response, Tweet[].class));
        assertThat(tweets).hasSize(3);
        assertThat(tweets.get(0).getDatetime()).isEqualTo(date);
    }

    @Test
    void updateTweetIntegrationTest() throws Exception {

        String beforeUpdate =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> beforeTweets = Arrays.asList(objectMapper.readValue(beforeUpdate, Tweet[].class));
        Tweet targetTweet = beforeTweets.get(0);
        targetTweet.setText("updated!");

        mockMvc.perform(put("/api/tweets/" + targetTweet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(targetTweet)))
                .andExpect(status().isOk());

        String afterResponse =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("updated!"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> afterTweets = Arrays.asList(objectMapper.readValue(afterResponse, Tweet[].class));
        assertThat(afterTweets).hasSize(3);
        assertThat(afterTweets.get(0).getText()).isEqualTo("updated!");
    }

    @Test
    public void deleteTweetIntegrationTest() throws Exception {

        String beforeDelete =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> beforeTweets = Arrays.asList(objectMapper.readValue(beforeDelete, Tweet[].class));
        Tweet targetTweet = beforeTweets.get(0);

        mockMvc.perform(delete("/api/tweets/"+targetTweet.getId()))
                .andExpect(status().isOk());

        String afterDelete =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(String.valueOf(beforeTweets.get(1).getId())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> afterTweets = Arrays.asList(objectMapper.readValue(afterDelete, Tweet[].class));
        assertThat(afterTweets).hasSize(3);
        assertThat(afterTweets.get(0).getId()).isEqualTo(beforeTweets.get(1).getId());
    }

    @Test
    @Sql(scripts = {"classpath:delete.sql"})
    public void getAllTweetsWithNoDataIntegrationTest() throws Exception {

        String response = mockMvc.perform(get("/api/tweets"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> tweets = Arrays.asList(objectMapper.readValue(response, Tweet[].class));
        assertThat(tweets).hasSize(0);
    }

    @Test
    @Sql(scripts = {"classpath:delete.sql"})
    public void getRecentTweetsWithNoDataIntegrationTest() throws Exception {

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
        assertThat(tweets).hasSize(0);
    }

    @Test
    public void updateNonExistentTweetIntegrationTest() throws Exception {

        Date date = new Date(System.currentTimeMillis());

        Tweet updateTweet = new Tweet();
        updateTweet.setAccountId("user_A");
        updateTweet.setText("これはテストツイートです。");
        updateTweet.setImage("/src/assets/images/img01.GIF");
        updateTweet.setLikes(999);
        updateTweet.setRetweets(999);
        updateTweet.setReplies(999);
        updateTweet.setViews(999);
        updateTweet.setDatetime(date);
        updateTweet.setLocation("テストロケーション");
        updateTweet.setDeleteFlag(false);

        mockMvc.perform(put("/api/tweets/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTweet)))
                .andExpect(status().isNotFound());
    }
}
