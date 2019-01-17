package solutions.exercise12.refactored.model;

import lombok.*;
import solutions.exercise12.refactored.AccountManager;
import solutions.exercise12.refactored.FraudChecker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The bank is not responsible for creation of users, only for creation of accounts and management of
 * balances.
 * These tasks in turn are delegated to the appropriate bank facilities. A decoupling of logic and data
 * makes not much sense since there is no relation to any top facility, module or class.
 * Although opinions differ in this case; some might argue in a prophylactic manner to create an interface,
 * e.g. 'IBank' with the methods 'addAccount' (note that as usual the private method is irrelevant to the
 * potential >public< interface).
 * */

/**
 * A class representing an account.
 *
 * @since 1.0.0
 * */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor //required by hibernate
@Setter
@Getter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "account_id")
    List<Account> accounts = new ArrayList<>();

    @NonNull
    @Transient
    AccountManager accountManager;

    @NonNull
    @Transient
    FraudChecker fraudChecker;

    public void addAccount(Person person){
        accounts.add(accountManager.createAccount(person));
    }

    private boolean checkFraud(Account account)
    {
        return fraudChecker.checkFraud(account);
    }
}
