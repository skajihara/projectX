package skajihara.projectX.MainContents.Home.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.exception.AccountException;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

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
    public void selectAccountUnitTest() {

        Account expected = new Account();

        doReturn(new Account()).when(accountRepository).selectAccount(anyString());

        Account result = accountService.selectAccount((anyString()));

        verify(accountRepository, times(1)).selectAccount(anyString());
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void selectAccountIntegrationTest() {

        Account account = accountService.selectAccount("user_A");
        assertThat(account).isNotNull();
        assertThat(account.getId()).isEqualTo("user_A");
        assertThat(account.getName()).isEqualTo("Test User A");
        assertThat(account.getBio()).isEqualTo("user_a test text.");
        assertThat(account.getIcon()).isEqualTo("/src/assets/icons/user/kkrn_icon_user_1.svg");
        assertThat(account.isValidFlag()).isEqualTo(true);
        assertThat(account.isDeleteFlag()).isEqualTo(false);
    }

    @Test
    public void selectNonExistentAccountIntegrationTest() {
        assertThrows(AccountException.class, () -> accountService.selectAccount("user_exception"));
    }
}
