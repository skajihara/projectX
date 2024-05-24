package skajihara.projectX.MainContents.Home.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
//import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.entity.Tweet;
//import skajihara.projectX.MainContents.Home.repository.AccountRepository;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class CsvLoader {

//    @Autowired
//    private AccountRepository accountRepository;

    @Autowired
    private TweetRepository tweetRepository;

//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//    public void loadAccounts(String filePath) throws IOException, CsvException {
//        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
//            List<String[]> lines = reader.readAll();
//            for (String[] line : lines) {
//                Account account = new Account();
//                account.setId(Long.parseLong(line[0]));
//                account.setUsername(line[1]);
//                account.setEmail(line[2]);
//                account.setPassword(line[3]);
//                accountRepository.save(account);
//            }
//        }
//    }

    public void loadTweets(String filePath){
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                tweet.setDatetime(dateFormat.parse(line[7]));
                tweet.setLocation(line[8]);
                tweet.setDeleteFlag(Boolean.valueOf(line[9]));
                tweetRepository.save(tweet);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}