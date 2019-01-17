package solutions.exercise12.refactored;

import lombok.RequiredArgsConstructor;
import solutions.exercise12.refactored.model.Account;
import solutions.exercise12.refactored.model.Person;

/**
 * An interface providing methods for account management.
 * This interface provides the benefits of:
 * - The bank does not require to implement already existing fraud checks (reinvention of the wheel).
 * - The account does not need to implement any logic (data - logic decoupling).
 *
 * @since 1.0.0
 * */
@RequiredArgsConstructor
public class AccountManagerImpl implements AccountManager {

    /**
     * Creates an empty account for the given person.
     *
     * @param person for which the account will be created.
     * @return a new account which is associated with the provided person.
     * */
    @Override
    public Account createAccount(Person person) {
        return new Account(person);
    }
}
