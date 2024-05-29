package skajihara.projectX.MainContents.Home.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @SpyBean
    AccountRepository accountRepository;

    @SpyBean
    Account account;

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
    public void selectAccountIntegrationTest() throws Exception {

    }
}
