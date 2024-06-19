package skajihara.projectX.MainContents.Schedule.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skajihara.projectX.MainContents.Schedule.entity.ScheduledTweet;
import skajihara.projectX.MainContents.Schedule.repository.ScheduledTweetRepository;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTweetCsvLoader {

    @Autowired
    private ScheduledTweetRepository scheduledTweetRepository;

    public void loadScheduledTweets(String filePath) {

        scheduledTweetRepository.deleteAll();
        if(filePath.equals("")) {return;}

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                ScheduledTweet scheduledTweet = new ScheduledTweet();
                scheduledTweet.setAccountId(line[0]);
                scheduledTweet.setText(line[1]);
                scheduledTweet.setImage(line[2]);
                scheduledTweet.setLocation(line[3]);
                Date scheduled_datetime = dateFormat.parse(line[4].replace("'", ""));
                scheduledTweet.setScheduledDatetime(scheduled_datetime);
                Date created_datetime = dateFormat.parse(line[5].replace("'", ""));
                scheduledTweet.setCreatedDatetime(created_datetime);
                scheduledTweet.setDeleteFlag(Boolean.valueOf(line[6]));
                scheduledTweetRepository.save(scheduledTweet);
            }
        } catch (IOException | CsvException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
