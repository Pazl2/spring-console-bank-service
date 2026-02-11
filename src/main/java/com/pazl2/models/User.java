package com.pazl2.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    static Integer countOfUsers = 0;

    private Integer id;
    private String login;
    private List<Account> accountList = new ArrayList<>();

    public User(String login){
        this.id = countOfUsers;
        this.login = login;

        countOfUsers++;
    }

    public Integer getId() {
        return id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public static Integer getCountOfUsers() {
        return countOfUsers;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
