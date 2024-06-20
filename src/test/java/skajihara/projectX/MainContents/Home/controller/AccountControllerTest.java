package skajihara.projectX.MainContents.Home.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.service.AccountService;
import skajihara.projectX.MainContents.Home.util.AccountCsvLoader;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    AccountService accountService;

    @Autowired
    private AccountCsvLoader accountCsvLoader;

    @Test
    public void getAccount_UnitTest() throws Exception {

        doReturn(new Account()).when(accountService).selectAccount(anyString());

        mockMvc.perform(get("/api/accounts/q30387")).andExpect(status().isOk());

        verify(accountService, times(1)).selectAccount(anyString());
    }

    @Test
    public void getAccount() throws Exception {

        String response =mockMvc.perform(get("/api/accounts/user_A"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Account account = objectMapper.readValue(response, Account.class);
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
    public void getAccount_MissingData() throws Exception {
        mockMvc.perform(get("/api/accounts/user_exception"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllAccounts_GettingSomedata() throws Exception {

        accountCsvLoader.loadAccounts("src/test/resources/csv/controller/Account/Test01.csv");

        String response = mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Account> accounts = Arrays.asList(objectMapper.readValue(response, Account[].class));
        assertThat(accounts).hasSize(3);
    }

    @Test
    public void getAllAccounts_GettingNoData() throws Exception {

        // cleanup database
        accountCsvLoader.loadAccounts("");

        String response = mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.length()").value(0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Account> accounts = Arrays.asList(objectMapper.readValue(response, Account[].class));
        assertThat(accounts).hasSize(0);
    }
}
