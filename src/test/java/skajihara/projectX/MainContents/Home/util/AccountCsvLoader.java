package skajihara.projectX.MainContents.Home.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;
import skajihara.projectX.MainContents.Home.repository.ScheduledTweetRepository;
import skajihara.projectX.MainContents.Home.repository.TweetRepository;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class AccountCsvLoader {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private ScheduledTweetRepository scheduledTweetRepository;

    public void loadAccounts(String filePath) {

        tweetRepository.deleteAll();
        scheduledTweetRepository.deleteAll();
        accountRepository.deleteAll();
        if(filePath.equals("")) {return;}

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                Account account = new Account();
                account.setId(line[0]);
                account.setName(line[1]);
                account.setBio(line[2]);
                account.setIcon(line[3]);
                account.setHeaderPhoto(line[4]);
                account.setLocation(line[5]);
                account.setBirthday(LocalDate.parse(line[6]));
                account.setRegistered(LocalDate.parse(line[7]));
                account.setFollowing(Integer.valueOf(line[8]));
                account.setFollower(Integer.valueOf(line[9]));
                account.setValidFlag(Boolean.valueOf(line[10]));
                account.setDeleteFlag(Boolean.valueOf(line[11]));
                accountRepository.save(account);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
