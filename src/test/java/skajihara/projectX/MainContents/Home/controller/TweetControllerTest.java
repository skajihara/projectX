package skajihara.projectX.MainContents.Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.service.TweetService;
import skajihara.projectX.MainContents.Home.util.CsvLoader;

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

    @Autowired
    private CsvLoader csvLoader;

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
    public void getTweetsByAccountIdUnitTest() throws Exception {

        doReturn(new ArrayList<>()).when(tweetService).selectTweetsByAccountId(anyString());

        mockMvc.perform(get("/api/tweets/{account_id}","user_A")).andExpect(status().isOk());

        verify(tweetService, times(1)).selectTweetsByAccountId(anyString());
    }

    @Test
    public void getTweetUnitTest() throws Exception {

        doReturn(new Tweet()).when(tweetService).selectTweet(anyInt());

        mockMvc.perform(get("/api/tweets/tweet/{id}",1)).andExpect(status().isOk());

        verify(tweetService, times(1)).selectTweet(anyInt());
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

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test3.csv");

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

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test3.csv");

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
    public void getTweetsByAccountIdIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test3.csv");

        String response =mockMvc.perform(get("/api/tweets/user_A"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].datetime").value("2024-03-18T20:10:01.000+00:00"))
                .andExpect(jsonPath("$[1].datetime").value("2024-03-01T15:30:00.000+00:00"))
                .andExpect(jsonPath("$[2].datetime").value("2023-07-30T00:51:59.000+00:00"))
                .andExpect(jsonPath("$[3].datetime").value("2023-07-12T23:01:39.000+00:00"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> tweets = Arrays.asList(objectMapper.readValue(response, Tweet[].class));
        assertThat(tweets).hasSize(4);
        assertThat(tweets.get(0).getDatetime()).isEqualTo("2024-03-18T20:10:01.000+00:00");
        assertThat(tweets.get(1).getDatetime()).isEqualTo("2024-03-01T15:30:00.000+00:00");
        assertThat(tweets.get(2).getDatetime()).isEqualTo("2023-07-30T00:51:59.000+00:00");
        assertThat(tweets.get(3).getDatetime()).isEqualTo("2023-07-12T23:01:39.000+00:00");
    }

    @Test
    public void getTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test2.csv");

        String response =mockMvc.perform(get("/api/tweets/tweet/11"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(11))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Tweet tweet = objectMapper.readValue(response, Tweet.class);
        assertThat(tweet).isNotNull();
        assertThat(tweet.getId()).isEqualTo(11);
        assertThat(tweet.getAccountId()).isEqualTo("user_A");
        assertThat(tweet.getText()).isEqualTo("get tweet integration test.");
        assertThat(tweet.getImage()).isEqualTo("/src/assets/images/img02.jpg");
        assertThat(tweet.getLikes()).isEqualTo(9);
        assertThat(tweet.getRetweets()).isEqualTo(23);
        assertThat(tweet.getReplies()).isEqualTo(7);
        assertThat(tweet.getViews()).isEqualTo(14);
        assertThat(tweet.getDatetime()).isEqualTo("2024-03-01T15:30:00.000+00:00");
        assertThat(tweet.getLocation()).isEqualTo("Namegawa City,Toyama Prefecture");
        assertThat(tweet.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    public void createTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test1.csv");

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
        assertThat(tweets.get(0).getAccountId()).isEqualTo(newTweet.getAccountId());
        assertThat(tweets.get(0).getText()).isEqualTo(newTweet.getText());
        assertThat(tweets.get(0).getImage()).isEqualTo(newTweet.getImage());
        assertThat(tweets.get(0).getLikes()).isEqualTo(newTweet.getLikes());
        assertThat(tweets.get(0).getRetweets()).isEqualTo(newTweet.getRetweets());
        assertThat(tweets.get(0).getReplies()).isEqualTo(newTweet.getReplies());
        assertThat(tweets.get(0).getViews()).isEqualTo(newTweet.getViews());
        assertThat(tweets.get(0).getDatetime()).isEqualTo(newTweet.getDatetime());
        assertThat(tweets.get(0).getLocation()).isEqualTo(newTweet.getLocation());
        assertThat(tweets.get(0).isDeleteFlag()).isEqualTo(newTweet.isDeleteFlag());
    }

    @Test
    void updateTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test2.csv");

        String beforeUpdate =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> beforeTweets = Arrays.asList(objectMapper.readValue(beforeUpdate, Tweet[].class));
        beforeTweets.get(0).setText("updated!");

        mockMvc.perform(put("/api/tweets/" + beforeTweets.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beforeTweets.get(0))))
                .andExpect(status().isOk());

        String afterUpdate =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].text").value("updated!"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> afterTweets = Arrays.asList(objectMapper.readValue(afterUpdate, Tweet[].class));

        // debug 文字化け確認用
        // System.out.println(beforeTweets.get(0));
        // System.out.println(afterTweets.get(0));

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
    public void deleteTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test2.csv");

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
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(String.valueOf(beforeTweets.get(1).getId())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Tweet> afterTweets = Arrays.asList(objectMapper.readValue(afterDelete, Tweet[].class));
        assertThat(afterTweets).hasSize(2);
        assertThat(afterTweets.get(0).getId()).isEqualTo(beforeTweets.get(1).getId());
    }

    @Test
    public void getAllTweetsWithNoDataIntegrationTest() throws Exception {

        // cleanup database
        csvLoader.loadTweets("");

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
    public void getRecentTweetsWithNoDataIntegrationTest() throws Exception {

        // cleanup database
        csvLoader.loadTweets("");

        String response =mockMvc.perform(get("/api/tweets/recent").param("num","3"))
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
    public void getTweetsByAccountIdWithNoDataIntegrationTest() throws Exception {

        // cleanup database
        csvLoader.loadTweets("");

        String response =mockMvc.perform(get("/api/tweets/user_A"))
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
    public void getNonExistentTweetIntegrationTest() throws Exception {

        // cleanup database
        csvLoader.loadTweets("");

        mockMvc.perform(get("/api/tweets/tweet/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateNonExistentTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test1.csv");

        Date date = new Date(System.currentTimeMillis());

        Tweet updateTweet = new Tweet();
        updateTweet.setAccountId("user_A");
        updateTweet.setText("This is a test tweet.");
        updateTweet.setImage("/src/assets/images/img01.GIF");
        updateTweet.setLikes(999);
        updateTweet.setRetweets(999);
        updateTweet.setReplies(999);
        updateTweet.setViews(999);
        updateTweet.setDatetime(date);
        updateTweet.setLocation("Test Location.");
        updateTweet.setDeleteFlag(false);

        mockMvc.perform(put("/api/tweets/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTweet)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNonExistentTweetIntegrationTest() throws Exception {

        csvLoader.loadTweets("src/test/resources/csv/controller/Tweet/Test1.csv");

        mockMvc.perform(delete("/api/tweets/99999"))
                .andExpect(status().isNotFound());
    }
}
