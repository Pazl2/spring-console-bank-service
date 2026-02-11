package com.pazl2.services;

import com.pazl2.OperationType;
import com.pazl2.models.Account;
import com.pazl2.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class OperationConsoleListener {

    private final AccountService accountService;
    private final UserService userService;
    private Scanner scanner = new Scanner(System.in);
    private OperationType currentOperationType = OperationType.USER_CREATE;

    public OperationConsoleListener(
            AccountService accountService,
            UserService userService
    ) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public void start(){
        while (currentOperationType != OperationType.EXIT){
            System.out.println("\nPlease enter one of operation type:");
            for(OperationType type : OperationType.values()){
                System.out.printf("-%s\n", type);
            }
            System.out.println();


            try{
                currentOperationType =
                        OperationType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e){
                System.out.println("Enter correct command!");
                continue;
            }

            switch (currentOperationType){
                case EXIT -> System.out.println("BYE-BYE");
                case USER_CREATE -> userCreate();
                case ACCOUNT_CREATE -> accountCreate();
                case SHOW_ALL_USERS -> showAllUsers();
                case ACCOUNT_CLOSE -> accountClose();
                case ACCOUNT_WITHDRAW -> accountWithdraw();
                case ACCOUNT_DEPOSIT -> accountDeposit();
                case ACCOUNT_TRANSFER -> accountTransfer();
            }
        }
    }

    public void userCreate(){
        System.out.println("Enter login for new user:");
        String login = scanner.nextLine();
        userService.createUser(login);
        System.out.print("User created: ");
        System.out.println(userService.findUserById(User.getCountOfUsers() - 1));
    }

    public void accountCreate(){
        System.out.println("Enter the user id for which to create an account:");
        String in = scanner.nextLine();
        Integer userId;

        try{
            userId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }


        User user = userService.getMapUsers().get(userId);
        if(user == null){
            System.out.printf("User with ID: %s, doesn't exist!\n", userId);
            return;
        }

        accountService.createAccount(userId);
        System.out.printf("New account created with ID: %s for user: %s\n",
                user.getAccountList().getLast().getId(),
                user.getLogin());
    }

    public void showAllUsers(){
        System.out.println("List of all users:");
        List<User> users = userService.getAllUsers();
        for(User user : users){
            System.out.println(user);
        }
    }

    public void accountClose(){
        System.out.println("Enter account ID to close:");
        String in = scanner.nextLine();
        Integer accId;

        try{
            accId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        Account account = accountService.getAccounts().get(accId);

        if(account == null){
            System.out.printf("Account with ID: %s, doesn't exist!\n", accId);
            return;
        }

        if(!accountService.closeAccount(accId)){
            System.out.printf("Account with ID: %s, is the first account of user: %s!\n", accId,
                    userService.getMapUsers().get(account.getUser_id()).getLogin());
            return;
        }

        System.out.printf("Account with ID: %s, has been closed!\n", accId);
    }

    public void accountWithdraw(){
        System.out.println("Enter account ID to withdraw from:");
        String in = scanner.nextLine();
        Integer accId;

        try{
            accId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        Account account = accountService.getAccounts().get(accId);

        if(account == null){
            System.out.printf("Account with ID: %s, doesn't exist!\n", accId);
            return;
        }

        System.out.println("Enter amount to withdraw:");
        in = scanner.nextLine();
        Integer moneyAmount;

        try{
            moneyAmount = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        if(!accountService.moneyWithdraw(accId,moneyAmount)){
            System.out.printf("No such money to withdraw from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s",
                    accId, moneyAmount, account.getMoneyAmount());
            return;
        }

        System.out.println("Success!");
    }

    public void accountDeposit(){
        System.out.println("Enter account ID to deposit to:");
        String in = scanner.nextLine();
        Integer accId;

        try{
            accId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        Account account = accountService.getAccounts().get(accId);

        if(account == null){
            System.out.printf("Account with ID: %s, doesn't exist!\n", accId);
            return;
        }

        System.out.println("Enter amount to deposit:");
        in = scanner.nextLine();
        Integer moneyAmount;

        try{
            moneyAmount = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        accountService.moneyDeposit(accId,moneyAmount);
        System.out.println("Success!");
    }

    public void accountTransfer(){
        System.out.println("Enter source account ID::");
        String in = scanner.nextLine();
        Integer sourceAccId;

        try{
            sourceAccId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        Account sourceAccount = accountService.getAccounts().get(sourceAccId);

        if(sourceAccount == null){
            System.out.printf("Account with ID: %s, doesn't exist!\n", sourceAccId);
            return;
        }

        System.out.println("Enter target account ID::");
        in = scanner.nextLine();
        Integer targetAccId;

        try{
            targetAccId = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        Account targetAccount = accountService.getAccounts().get(sourceAccId);

        if(targetAccount == null){
            System.out.printf("Account with ID: %s, doesn't exist!\n", sourceAccId);
            return;
        }

        System.out.println("Enter amount to transfer:");
        in = scanner.nextLine();
        Integer moneyAmount;

        try{
            moneyAmount = Integer.parseInt(in);
        } catch (NumberFormatException e){
            System.out.println("Incorrect input!");
            return;
        }

        if(!accountService.moneyTransfer(sourceAccId,targetAccId,moneyAmount)){
            System.out.printf("No such money to transfer from account: id=%s, moneyAmount=%s, attemptedWithdraw=%s",
                    sourceAccId, moneyAmount, sourceAccount.getMoneyAmount());
            return;
        }

        System.out.printf("Amount %s transferred from account ID %s to account ID %s.", moneyAmount,sourceAccId,targetAccId);
    }


}
