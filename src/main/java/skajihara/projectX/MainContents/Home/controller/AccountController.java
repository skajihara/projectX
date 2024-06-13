package skajihara.projectX.MainContents.Home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skajihara.projectX.MainContents.Home.entity.Account;
import skajihara.projectX.MainContents.Home.exception.NotFoundException;
import skajihara.projectX.MainContents.Home.service.AccountService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService){this.accountService = accountService;}

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.selectAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id) throws NotFoundException {
        return accountService.selectAccount(id);
    }
}
