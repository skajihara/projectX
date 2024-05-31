package skajihara.projectX.MainContents.Home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account selectAccount(String id) throws NotFoundException {
        Account account = accountRepository.selectAccount(id);
        if (account == null) {
            throw new NotFoundException();
        }
        return account;
    }
}
