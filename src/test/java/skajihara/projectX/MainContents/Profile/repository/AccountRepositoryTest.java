package skajihara.projectX.MainContents.Profile.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skajihara.projectX.MainContents.Profile.entity.Account;
import skajihara.projectX.MainContents.Profile.util.AccountCsvLoader;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private AccountCsvLoader accountCsvLoader;

    @BeforeEach
    void setUp(){

        Account account = new Account();
        account.setId("test_id");
        account.setName("test_name");
        account.setBio("これはテストです。");
        account.setIcon("/src/assets/icons/user/kkrn_icon_user_1.svg");
        account.setHeaderPhoto("/src/assets/images/header/default_header.jpg");
        account.setLocation("東京都");
        account.setBirthday(LocalDate.of(2000,01,01));
        account.setRegistered(LocalDate.of(2020,12,31));
        account.setFollowing(100);
        account.setFollower(200);
        account.setValidFlag(true);
        account.setDeleteFlag(false);
        accountRepository.save(account);
    }

    @Test
    void selectAccount() {

        Account account = accountRepository.selectAccount("test_id");
        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo("test_id");
        assertThat(account.getName()).isEqualTo("test_name");
        assertThat(account.getBio()).isEqualTo("これはテストです。");
        assertThat(account.getIcon()).isEqualTo("/src/assets/icons/user/kkrn_icon_user_1.svg");
        assertThat(account.getHeaderPhoto()).isEqualTo("/src/assets/images/header/default_header.jpg");
        assertThat(account.getLocation()).isEqualTo("東京都");
        assertThat(account.getBirthday()).isEqualTo(LocalDate.of(2000,01,01));
        assertThat(account.getRegistered()).isEqualTo(LocalDate.of(2020,12,31));
        assertThat(account.getFollowing()).isEqualTo(100);
        assertThat(account.getFollower()).isEqualTo(200);
        assertThat(account.isValidFlag()).isEqualTo(true);
        assertThat(account.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    void selectAccount_MissingData() {
        Account account = accountRepository.selectAccount("abcdefg");
        assertThat(account).isNull();
    }

    @Test
    void selectAll_GettingSomeData() {
        List<Account> accounts = accountRepository.selectAll();
        assertThat(accounts).hasSize(4);
    }

    @Test
    void selectAll_GettingNoData() {
        accountCsvLoader.loadAccounts("");
        List<Account> accounts = accountRepository.selectAll();
        assertThat(accounts).hasSize(0);
    }
}
