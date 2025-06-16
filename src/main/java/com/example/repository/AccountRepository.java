package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
// create table account (
//     accountId int primary key auto_increment,
//     username varchar(255) not null unique,
//     password varchar(255)
// );

@Query("SELECT a FROM Account a WHERE a.accountId = ?1")
public Optional<Account> getAccountById(Integer id);

@Query("SELECT a FROM Account a WHERE a.username = ?1 AND a.password = ?2")
public Optional<Account> authenticate(String username, String password);

@Query("SELECT a FROM Account a WHERE a.username = ?1")
public Optional<Account> accountWithUsernameExists(String username);

}
