package solutions.exercise11;

import org.hibernate.Session;
import org.junit.Test;

public class HibernateTest {

    @Test
    public void shouldSucceedIfEntityWasPersisted()
    {
        Session session = SessionHelper.getSessionFactory().openSession();

        Pikachu pikachu = new Pikachu();
        PersistenceHelper.saveThing(session,pikachu);

        session.close();
        session = SessionHelper.getSessionFactory().openSession();

        Thing t = PersistenceHelper.getThing(pikachu.getId(),session);

        session.close();

        assert(t != null);
        assert(t.getId() == pikachu.getId());
    }

    @Test
    public void shouldSucceedIfPersistedEntityDiffersFromOriginalObject()
    {
        Session session = SessionHelper.getSessionFactory().openSession();

        Pikachu pikachu = new Pikachu();
        PersistenceHelper.saveThing(session,pikachu);

        session.close();
        session = SessionHelper.getSessionFactory().openSession();

        Thing t = PersistenceHelper.getThing(pikachu.getId(),session);

        session.close();

        assert(t != pikachu);
        assert(t.equals(pikachu));
    }

}
