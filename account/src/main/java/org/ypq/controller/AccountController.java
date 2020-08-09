package org.ypq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ypq.persistence.Account;
import org.ypq.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/deduct")
    public String deduct(String userId, int num) {
        Account account = accountService.deduct(userId, num);
        if (account == null) {
            return "money not enough " + num ;
        }
        return userId + "'s balance is " + account.getMoney();
    }

}
