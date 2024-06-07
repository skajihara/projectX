package skajihara.projectX.MainContents.Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Home.util.ScheduledTweetCsvLoader;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduledTweetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduledTweetCsvLoader scheduledTweetCsvLoader;

    @Test
    public void getScheduledTweetsIntegrationTest() throws Exception {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/controller/ScheduledTweet/Test01.csv");

        String response = mockMvc.perform(get("/api/tweets/scheduled/user_A"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ScheduledTweet> scheduledTweets = Arrays.asList(objectMapper.readValue(response, ScheduledTweet[].class));
        assertThat(scheduledTweets).hasSize(3);
    }

    @Test
    public void getScheduledTweetsWithNoDataIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        String response = mockMvc.perform(get("/api/tweets/scheduled/user_A"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ScheduledTweet> scheduledTweets = Arrays.asList(objectMapper.readValue(response, ScheduledTweet[].class));
        assertThat(scheduledTweets).hasSize(0);
    }

    @Test
    public void getTweetIntegrationTest() throws Exception {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/controller/ScheduledTweet/Test01.csv");

        String response = mockMvc.perform(get("/api/tweets/scheduled/user_A/7"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduled_datetime = dateFormat.parse("2024-06-13 01:00:00");
        Date created_datetime = dateFormat.parse("2024-06-07 11:11:11");

        ScheduledTweet scheduledTweet = objectMapper.readValue(response, ScheduledTweet.class);
        assertThat(scheduledTweet).isNotNull();
        assertThat(scheduledTweet.getId()).isEqualTo(7);
        assertThat(scheduledTweet.getAccountId()).isEqualTo("user_A");
        assertThat(scheduledTweet.getText()).isEqualTo("tweet content 1");
        assertThat(scheduledTweet.getImage()).isEqualTo("/src/assets/images/img01.GIF");
        assertThat(scheduledTweet.getScheduledDatetime().getTime()).isEqualTo(scheduled_datetime.getTime());
        assertThat(scheduledTweet.getCreatedDatetime().getTime()).isEqualTo(created_datetime.getTime());
        assertThat(scheduledTweet.getLocation()).isEqualTo("Tokyo");
        assertThat(scheduledTweet.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    public void getNonExistentScheduledTweetIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        mockMvc.perform(get("/api/tweets/scheduled/user_A/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createScheduledTweetIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduled_datetime = dateFormat.parse("2024-06-13 12:00:00");
        Date created_datetime = new Date(System.currentTimeMillis());

        ScheduledTweet newTweet = new ScheduledTweet();
        newTweet.setAccountId("user_A");
        newTweet.setText("This is a test tweet.");
        newTweet.setImage("/src/assets/images/test.jpg");
        newTweet.setLocation("Test Location.");
        newTweet.setScheduledDatetime(scheduled_datetime);
        newTweet.setCreatedDatetime(created_datetime);
        newTweet.setDeleteFlag(false);

        mockMvc.perform(post("/api/tweets/scheduled/" + newTweet.getAccountId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTweet)))
                .andExpect(status().isOk());

        String response = mockMvc.perform(get("/api/tweets/scheduled/" + newTweet.getAccountId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ScheduledTweet> tweets = Arrays.asList(objectMapper.readValue(response, ScheduledTweet[].class));
        assertThat(tweets).hasSize(1);
        assertThat(tweets.get(0).getAccountId()).isEqualTo(newTweet.getAccountId());
        assertThat(tweets.get(0).getText()).isEqualTo(newTweet.getText());
        assertThat(tweets.get(0).getImage()).isEqualTo(newTweet.getImage());
        assertThat(tweets.get(0).getLocation()).isEqualTo(newTweet.getLocation());
        assertThat(tweets.get(0).getScheduledDatetime()).isEqualTo(newTweet.getScheduledDatetime());
        assertThat(tweets.get(0).getCreatedDatetime()).isEqualTo(newTweet.getCreatedDatetime());
        assertThat(tweets.get(0).isDeleteFlag()).isEqualTo(newTweet.isDeleteFlag());
    }

    @Test
    void createInvalidScheduledTweetIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        ScheduledTweet invalidTweet = new ScheduledTweet();
        invalidTweet.setAccountId("user_A");
        mockMvc.perform(post("/api/tweets/scheduled/" + invalidTweet.getAccountId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTweetIntegrationTest() throws Exception {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/controller/ScheduledTweet/Test01.csv");

        String beforeUpdate =mockMvc.perform(get("/api/tweets/scheduled/user_A/7"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ScheduledTweet beforeTweet = objectMapper.readValue(beforeUpdate, ScheduledTweet.class);
        beforeTweet.setText("updated!");

        mockMvc.perform(put("/api/tweets/scheduled/user_A/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beforeTweet)))
                .andExpect(status().isOk());

        String afterUpdate = mockMvc.perform(get("/api/tweets/scheduled/user_A/7"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.text").value("updated!"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ScheduledTweet afterTweet = objectMapper.readValue(afterUpdate, ScheduledTweet.class);
        assertThat(afterTweet.getAccountId()).isEqualTo(beforeTweet.getAccountId());
        assertThat(afterTweet.getText()).isEqualTo(beforeTweet.getText());
        assertThat(afterTweet.getImage()).isEqualTo(beforeTweet.getImage());
        assertThat(afterTweet.getLocation()).isEqualTo(beforeTweet.getLocation());
        assertThat(afterTweet.getScheduledDatetime()).isEqualTo(beforeTweet.getScheduledDatetime());
        assertThat(afterTweet.getCreatedDatetime()).isEqualTo(beforeTweet.getCreatedDatetime());
        assertThat(afterTweet.isDeleteFlag()).isEqualTo(beforeTweet.isDeleteFlag());
    }

    @Test
    public void updateNonExistentScheduledTweetIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        Date date = new Date(System.currentTimeMillis());

        ScheduledTweet updateTweet = new ScheduledTweet();
        updateTweet.setAccountId("user_A");
        updateTweet.setText("This is a test tweet.");
        updateTweet.setImage("/src/assets/images/img01.GIF");
        updateTweet.setLocation("Test Location.");
        updateTweet.setScheduledDatetime(date);
        updateTweet.setCreatedDatetime(date);
        updateTweet.setDeleteFlag(false);

        mockMvc.perform(put("/api/tweets/scheduled/user_A/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTweet)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteScheduledTweetIntegrationTest() throws Exception {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/controller/ScheduledTweet/Test01.csv");

        mockMvc.perform(get("/api/tweets/scheduled/user_A/4"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"));

        mockMvc.perform(delete("/api/tweets/scheduled/user_A/4"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/tweets/scheduled/user_A/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNonExistentScheduledTweetIntegrationTest() throws Exception {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        mockMvc.perform(delete("/api/tweets/scheduled/user_A/99999"))
                .andExpect(status().isNotFound());
    }
}
