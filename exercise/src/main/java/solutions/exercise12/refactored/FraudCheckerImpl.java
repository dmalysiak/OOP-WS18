package solutions.exercise12.refactored;

import solutions.exercise12.refactored.model.Account;

/**
 * An interface providing methods for fraud checking.
 * One could also favor the approach of creating subclasses of bank for
 * different fraud checking.
 *
 * @since 1.0.0
 * */
public class FraudCheckerImpl implements FraudChecker {
    @Override
    public boolean checkFraud(Account account) {
        return false;
    }
}
