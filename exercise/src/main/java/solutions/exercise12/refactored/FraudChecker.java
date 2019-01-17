package solutions.exercise12.refactored;

import solutions.exercise12.refactored.model.Account;
import solutions.exercise12.refactored.model.Person;

/**
 * An interface providing methods for fraud checking.
 * One could also favor the approach of creating subclasses of bank for
 * different fraud checking.
 *
 * @since 1.0.0
 * */
public interface FraudChecker {
    boolean checkFraud(Account account);
}
