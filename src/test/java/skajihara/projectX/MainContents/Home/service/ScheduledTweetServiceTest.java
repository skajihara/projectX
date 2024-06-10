package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import skajihara.projectX.MainContents.Home.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Home.exception.NotFoundException;
import skajihara.projectX.MainContents.Home.util.ScheduledTweetCsvLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduledTweetServiceTest {

    @Autowired
    ScheduledTweetService scheduledTweetService;

    @Autowired
    private ScheduledTweetCsvLoader scheduledTweetCsvLoader;

    @Test
    public void selectScheduledTweets_IntegrationTest() throws ParseException {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduled_date1 = dateFormat.parse("2024-06-13 01:00:00");
        Date scheduled_date2 = dateFormat.parse("2024-06-13 01:00:00");
        Date scheduled_date3 = dateFormat.parse("2024-06-13 03:00:00");
        Date created_date1 = dateFormat.parse("2024-06-07 12:12:12");
        Date created_date2 = dateFormat.parse("2024-06-07 11:11:11");
        Date created_date3 = dateFormat.parse("2024-06-07 12:12:12");

        List<ScheduledTweet> scheduledTweets = scheduledTweetService.selectScheduledTweets("user_A");
        assertThat(scheduledTweets).hasSize(3);
        assertThat(scheduledTweets.get(0).getScheduledDatetime().getTime()).isEqualTo(scheduled_date1.getTime());
        assertThat(scheduledTweets.get(0).getCreatedDatetime().getTime()).isEqualTo(created_date1.getTime());
        assertThat(scheduledTweets.get(1).getScheduledDatetime().getTime()).isEqualTo(scheduled_date2.getTime());
        assertThat(scheduledTweets.get(1).getCreatedDatetime().getTime()).isEqualTo(created_date2.getTime());
        assertThat(scheduledTweets.get(2).getScheduledDatetime().getTime()).isEqualTo(scheduled_date3.getTime());
        assertThat(scheduledTweets.get(2).getCreatedDatetime().getTime()).isEqualTo(created_date3.getTime());
    }

    @Test
    public void selectScheduledTweets_WithNoData_IntegrationTest() {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        List<ScheduledTweet> scheduledTweets = scheduledTweetService.selectScheduledTweets("user_A");
        assertThat(scheduledTweets).hasSize(0);
    }

    @Test
    void selectScheduledTweet_IntegrationTest() throws ParseException {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduled_datetime = dateFormat.parse("2024-06-13 03:00:00");
        Date created_datetime = dateFormat.parse("2024-06-07 12:12:12");

        ScheduledTweet scheduledTweet = scheduledTweetService.selectScheduledTweet(9);
        assertThat(scheduledTweet).isNotNull();
        assertThat(scheduledTweet.getId()).isEqualTo(9);
        assertThat(scheduledTweet.getAccountId()).isEqualTo("user_A");
        assertThat(scheduledTweet.getText()).isEqualTo("ツイート内容3");
        assertThat(scheduledTweet.getImage()).isEqualTo("/src/assets/images/img03.jpg");
        assertThat(scheduledTweet.getScheduledDatetime().getTime()).isEqualTo(scheduled_datetime.getTime());
        assertThat(scheduledTweet.getCreatedDatetime().getTime()).isEqualTo(created_datetime.getTime());
        assertThat(scheduledTweet.getLocation()).isEqualTo("北海道");
        assertThat(scheduledTweet.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    void selectScheduledTweet_MissingData_IntegrationTest() {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");
        assertThrows(NotFoundException.class, () -> scheduledTweetService.selectScheduledTweet(999));
    }

    @Test
    void createScheduledTweet_IntegrationTest() throws ParseException {

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

        scheduledTweetService.createScheduledTweet(newTweet);

        ScheduledTweet scheduledTweet = scheduledTweetService.selectScheduledTweet(7);
        assertThat(scheduledTweet.getAccountId()).isEqualTo(newTweet.getAccountId());
        assertThat(scheduledTweet.getText()).isEqualTo(newTweet.getText());
        assertThat(scheduledTweet.getImage()).isEqualTo(newTweet.getImage());
        assertThat(scheduledTweet.getLocation()).isEqualTo(newTweet.getLocation());
        assertThat(scheduledTweet.getScheduledDatetime().getTime()).isEqualTo(newTweet.getScheduledDatetime().getTime());
        assertThat(scheduledTweet.getCreatedDatetime().getTime()).isEqualTo(newTweet.getCreatedDatetime().getTime());
        assertThat(scheduledTweet.isDeleteFlag()).isEqualTo(newTweet.isDeleteFlag());
    }

    @Test
    void createScheduledTweet_InvalidData_IntegrationTest() {

        // cleanup database
        scheduledTweetCsvLoader.loadScheduledTweets("");

        ScheduledTweet invalidTweet = new ScheduledTweet();
        assertThrows(Exception.class, () -> scheduledTweetService.createScheduledTweet(invalidTweet));
    }

    @Test
    void updateTweet_IntegrationTest() {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");

        ScheduledTweet beforeTweet = scheduledTweetService.selectScheduledTweet(3);
        beforeTweet.setText("updated!");
        scheduledTweetService.updateScheduledTweet(beforeTweet.getId(), beforeTweet);

        ScheduledTweet afterTweet = scheduledTweetService.selectScheduledTweet(3);
        assertThat(afterTweet.getAccountId()).isEqualTo(beforeTweet.getAccountId());
        assertThat(afterTweet.getText()).isEqualTo(beforeTweet.getText());
        assertThat(afterTweet.getImage()).isEqualTo(beforeTweet.getImage());
        assertThat(afterTweet.getLocation()).isEqualTo(beforeTweet.getLocation());
        assertThat(afterTweet.getScheduledDatetime()).isEqualTo(beforeTweet.getScheduledDatetime());
        assertThat(afterTweet.getCreatedDatetime()).isEqualTo(beforeTweet.getCreatedDatetime());
        assertThat(afterTweet.isDeleteFlag()).isEqualTo(beforeTweet.isDeleteFlag());
    }

    @Test
    void updateScheduledTweet_ThrowsException_IntegrationTest() {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");

        ScheduledTweet scheduledTweet = scheduledTweetService.selectScheduledTweet(7);
        assertThat(scheduledTweet).isNotNull();
        scheduledTweet.setText("updated!");

        assertThrows(NotFoundException.class, () -> scheduledTweetService.updateScheduledTweet(99999, scheduledTweet));
    }

    @Test
    public void deleteScheduledTweet_IntegrationTest() {

        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");

        ScheduledTweet deleteTweet = scheduledTweetService.selectScheduledTweet(7);
        scheduledTweetService.deleteScheduledTweet(deleteTweet.getId());

        assertThrows(NotFoundException.class, () -> scheduledTweetService.selectScheduledTweet(deleteTweet.getId()));
    }

    @Test
    void deleteScheduledTweet_ThrowsException_IntegrationTest() {
        scheduledTweetCsvLoader.loadScheduledTweets("src/test/resources/csv/service/ScheduledTweet/Test01.csv");
        assertThrows(NotFoundException.class, () -> scheduledTweetService.deleteScheduledTweet(99999));
    }
}
