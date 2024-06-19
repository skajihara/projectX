package skajihara.projectX.MainContents.Schedule.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skajihara.projectX.MainContents.Schedule.entity.ScheduledTweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ScheduledTweetRepositoryTest {

    @Autowired
    ScheduledTweetRepository scheduledTweetRepository;

    @Test
    void selectScheduledTweets_IntegrationTest() {

        List<ScheduledTweet> scheduledTweets = scheduledTweetRepository.selectScheduledTweets("user_A");
        assertThat(scheduledTweets).hasSize(3);
    }

    @Test
    void selectScheduledTweet_IntegrationTest() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date scheduled_datetime = dateFormat.parse("2024-06-13 03:00:00");
        Date created_datetime = dateFormat.parse("2024-06-07 12:12:12");

        ScheduledTweet scheduledTweet = scheduledTweetRepository.selectScheduledTweet(3);
        assertThat(scheduledTweet.getId()).isEqualTo(3);
        assertThat(scheduledTweet.getAccountId()).isEqualTo("user_A");
        assertThat(scheduledTweet.getText()).isEqualTo("ツイート内容3");
        assertThat(scheduledTweet.getImage()).isEqualTo("/src/assets/images/img03.jpg");
        assertThat(scheduledTweet.getScheduledDatetime().getTime()).isEqualTo(scheduled_datetime.getTime());
        assertThat(scheduledTweet.getCreatedDatetime().getTime()).isEqualTo(created_datetime.getTime());
        assertThat(scheduledTweet.getLocation()).isEqualTo("北海道");
        assertThat(scheduledTweet.isDeleteFlag()).isEqualTo(false);
    }
}
