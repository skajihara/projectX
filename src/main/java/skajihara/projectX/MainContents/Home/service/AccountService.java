package skajihara.projectX.MainContents.Home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.exception.NotFoundException;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> selectAllAccounts() {
        return accountRepository.selectAll();
    }

    public Account selectAccount(String id) throws NotFoundException {
        Account account = accountRepository.selectAccount(id);
        if (account == null) {
            throw new NotFoundException("Account id: " + id + " is not found");
        }
        return account;
    }
}
