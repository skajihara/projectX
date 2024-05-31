package skajihara.projectX.MainContents.Home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.exception.AccountException;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account selectAccount(String id) {
        Account account = accountRepository.selectAccount(id);
        if (account == null) {
            throw new AccountException("Account id: " + id + " is not found");
        }
        return account;
    }
}
