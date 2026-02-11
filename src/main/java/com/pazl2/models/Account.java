package com.pazl2.models;

public class Account {

    private static Integer countOfAccount = 0;

    private Integer id;
    private Integer user_id;
    private Integer moneyAmount;

    public Account(Integer user_id, Integer moneyAmount){
        this.id = countOfAccount;
        this.user_id = user_id;
        this.moneyAmount = moneyAmount;

        countOfAccount++;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Integer moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
