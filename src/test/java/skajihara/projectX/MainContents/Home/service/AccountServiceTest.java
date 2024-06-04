package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.exception.NotFoundException;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @SpyBean
    AccountRepository accountRepository;

    @Test
    public void selectAccountUnitTest() throws NotFoundException {

        Account expected = new Account();

        doReturn(new Account()).when(accountRepository).selectAccount(anyString());

        Account result = accountService.selectAccount((anyString()));

        verify(accountRepository, times(1)).selectAccount(anyString());
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void selectAccountIntegrationTest() throws NotFoundException {

        Account account = accountService.selectAccount("user_A");
        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo("user_A");
        assertThat(account.getName()).isEqualTo("Test User A");
        assertThat(account.getBio()).isEqualTo("user_a test text.");
        assertThat(account.getIcon()).isEqualTo("/src/assets/icons/user/kkrn_icon_user_1.svg");
        assertThat(account.getHeaderPhoto()).isEqualTo("/src/assets/images/header/h01.jpg");
        assertThat(account.getLocation()).isEqualTo("test prefectureA");
        assertThat(account.getBirthday()).isEqualTo(LocalDate.of(1988,04,10));
        assertThat(account.getRegistered()).isEqualTo(LocalDate.of(2016,05,17));
        assertThat(account.getFollowing()).isEqualTo(5);
        assertThat(account.getFollower()).isEqualTo(7);
        assertThat(account.isValidFlag()).isEqualTo(true);
        assertThat(account.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    public void selectNonExistentAccountIntegrationTest() {
        assertThrows(NotFoundException.class, () -> accountService.selectAccount("user_exception"));
    }
}
