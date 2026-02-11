package com.pazl2.services;

import com.pazl2.models.Account;
import com.pazl2.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService {

    @Value("${account.default-amount}")
    private Integer defaultMoneyAmount;

    @Value("${account.transfer-commission}")
    private Integer transferCommission;

    private final UserService userService;

    private Map<Integer, Account> accounts = new HashMap<>();

    public AccountService(UserService userService){
        this.userService = userService;
    }

    public void createAccount(Integer user_id){
        Account account = new Account(user_id, defaultMoneyAmount);
        userService.getMapUsers().get(user_id).getAccountList().add(account);
        accounts.put(account.getId(), account);
    }

    public boolean closeAccount(Integer acc_id){
        Account account = accounts.get(acc_id);
        User user = userService.getMapUsers().get(account.getUser_id());

        if(user.getAccountList().size() == 1
                || user.getAccountList().getFirst() == account){
            return false;
        }

        moneyTransfer(acc_id, user.getAccountList().getFirst().getId(), account.getMoneyAmount());

        user.getAccountList().remove(account);
        return true;
    }

    public boolean moneyTransfer(Integer fromAccId, Integer toAccId, Integer moneyAmount){
        Account fromAcc = accounts.get(fromAccId);
        Account toAcc = accounts.get(toAccId);

        if(fromAcc.getMoneyAmount() < moneyAmount){
            return false;
        }

        fromAcc.setMoneyAmount(fromAcc.getMoneyAmount()-moneyAmount);

        if(fromAcc.getUser_id().equals(toAcc.getUser_id())){
            toAcc.setMoneyAmount(toAcc.getMoneyAmount()+moneyAmount);
        } else {
            toAcc.setMoneyAmount(toAcc.getMoneyAmount()
                    + moneyAmount
                    - (moneyAmount * transferCommission)/100);
        }

        return true;
    }

    public boolean moneyDeposit(Integer acc_id, Integer moneyAmount){
        Account account = accounts.get(acc_id);
        account.setMoneyAmount(account.getMoneyAmount()+moneyAmount);
        return true;
    }

    public boolean moneyWithdraw(Integer acc_id, Integer moneyAmount){
        Account account = accounts.get(acc_id);

        if(account.getMoneyAmount() < moneyAmount){return false;}

        account.setMoneyAmount(account.getMoneyAmount()-moneyAmount);
        return true;
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }
}
