package skajihara.projectX.MainContents.Home.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import skajihara.projectX.MainContents.Home.entity.Tweet;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class CsvLoader {

    @Autowired
    private TweetRepository tweetRepository;

    public void loadTweets(String filePath) {

        tweetRepository.deleteAll();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                Tweet tweet = new Tweet();
                tweet.setAccountId(line[0]);
                tweet.setText(line[1]);
                tweet.setImage(line[2]);
                tweet.setLikes(Integer.valueOf(line[3]));
                tweet.setRetweets(Integer.valueOf(line[4]));
                tweet.setReplies(Integer.valueOf(line[5]));
                tweet.setViews(Integer.valueOf(line[6]));
                Date datetime = dateFormat.parse(line[7].replace("'", ""));
                tweet.setDatetime(datetime);
                tweet.setLocation(line[8]);
                tweet.setDeleteFlag(Boolean.valueOf(line[9]));
                tweetRepository.save(tweet);
            }
        } catch (IOException | CsvException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}