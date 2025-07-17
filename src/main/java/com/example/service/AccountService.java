package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        return accountRepository.save(account);
    }

    public Account login(Account account){
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username not found");
        }

        Account logInAccount = existing.get();
        if (!logInAccount.getPassword().equals(account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        return logInAccount;
    }

}
