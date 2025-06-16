package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountDAO;

    // Create Account/ Register
    public Account create(Account account) {
        return accountDAO.save(account);
    }

    // Login
    public Account login(String username, String password) {
        Account result = accountDAO.authenticate(username, password).orElse(null);
        return result;
    }

    public boolean usernameExists(String username) {
        if(accountDAO.accountWithUsernameExists(username).isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isValidId(int id){
        return accountDAO.existsById(id);
    }
}
