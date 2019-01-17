package solutions.exercise12.baddesign;

import java.util.List;

public class Bank {
    List<Account> accounts;
    AccountInitializer accountInitializer;

    private void init()
    {
        accounts = accountInitializer.initializeAccounts();
    }

    public void checkFraud(){}
}
