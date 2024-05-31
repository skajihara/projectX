package skajihara.projectX.MainContents.Home.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import skajihara.projectX.MainContents.Home.entity.Account;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp(){

        Account account = new Account();
        account.setId("test_id");
        account.setName("test_name");
        account.setBio("これはテストです。");
        account.setIcon("/src/assets/icons/user/kkrn_icon_user_1.svg");
        account.setValidFlag(true);
        account.setDeleteFlag(false);
        accountRepository.save(account);
    }

    @Test
    void selectAccountIntegrationTest() {

        Account account = accountRepository.selectAccount("test_id");
        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo("test_id");
        assertThat(account.getName()).isEqualTo("test_name");
        assertThat(account.getBio()).isEqualTo("これはテストです。");
        assertThat(account.getIcon()).isEqualTo("/src/assets/icons/user/kkrn_icon_user_1.svg");
        assertThat(account.isValidFlag()).isEqualTo(true);
        assertThat(account.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    void selectNonExistentAccountIntegrationTest() {
        Account account = accountRepository.selectAccount("abcdefg");
        assertThat(account).isNull();
    }
}
