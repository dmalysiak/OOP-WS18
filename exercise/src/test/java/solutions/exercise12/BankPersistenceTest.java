package solutions.exercise12;

import lombok.NonNull;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.Test;
import solutions.exercise12.refactored.*;
import solutions.exercise12.refactored.repository.PersistenceHelper;
import solutions.exercise12.refactored.repository.SessionHelper;
import solutions.exercise12.refactored.model.Bank;
import solutions.exercise12.refactored.model.Person;

public class BankPersistenceTest {

    @Test
    public void shouldSucceedIfBankWasPersistedCorrectly()
    {
        Bank bank = new Bank(new AccountManagerImpl(),new FraudCheckerImpl());
        PersonManager personManager = new PersonManagerImpl();
        Person person = new Person();
        personManager.addAttribute(person, "rasputin","name");
        personManager.addAttribute(person, "far away","address");
        bank.addAccount(person);

        Session session = SessionHelper.getSessionFactory().openSession();

        PersistenceHelper.save(session,bank);

        session.close();
        session = SessionHelper.getSessionFactory().openSession();

        Bank bank_retrieved = PersistenceHelper.getBank(bank.getId(),session);

        /**
         * We need to stay in the session in order for the proxies to work!
         * */
        assert(bank_retrieved != null);
        assert(bank_retrieved.getId() == bank.getId());
        assert(bank_retrieved.getAccounts().size() == 1);
        assert(bank_retrieved.getAccounts().get(0).getPerson().getPersonAttributes().size()==2);
        assert(bank_retrieved.getAccounts().get(0).getPerson().getPersonAttributes().get(0).getValue().compareTo("rasputin")==0);
        assert(bank_retrieved.getAccounts().get(0).getPerson().getPersonAttributes().get(1).getValue().compareTo("far away")==0);
        assert(bank_retrieved.getFraudChecker()==null);
        assert(bank_retrieved.getAccountManager()==null);

        session.close();

    }

    @Test
    public void shouldSucceedIfFieldsWereLazyOrEagerLoaded()
    {
        Bank bank = new Bank(new AccountManagerImpl(),new FraudCheckerImpl());
        PersonManager personManager = new PersonManagerImpl();
        Person person = new Person();
        personManager.addAttribute(person, "rasputin","name");
        personManager.addAttribute(person, "far away","address");
        bank.addAccount(person);

        Session session = SessionHelper.getSessionFactory().openSession();

        PersistenceHelper.save(session,bank);

        session.close();
        session = SessionHelper.getSessionFactory().openSession();

        Bank bank_retrieved = PersistenceHelper.getBank(bank.getId(),session);

        assert(bank_retrieved != null);
        assert(bank_retrieved.getId() == bank.getId());
        assert(!Hibernate.isInitialized(bank_retrieved.getAccounts()));
        assert(Hibernate.isInitialized(bank_retrieved.getAccounts().get(0).getPerson()));
        assert(!Hibernate.isInitialized(bank_retrieved.getAccounts().get(0).getPerson().getPersonAttributes()));

        session.close();

    }

}
