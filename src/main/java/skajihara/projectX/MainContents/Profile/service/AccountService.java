package skajihara.projectX.MainContents.Profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Profile.entity.Account;
import skajihara.projectX.MainContents.Exception.NotFoundException;
import skajihara.projectX.MainContents.Profile.repository.AccountRepository;

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
