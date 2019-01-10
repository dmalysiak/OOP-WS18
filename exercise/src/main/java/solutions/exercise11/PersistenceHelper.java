package solutions.exercise11;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PersistenceHelper {

    public static void saveThing(Session session, Thing t)
    {
        Transaction tx = null;
        Long id = null;
        try {
            tx = session.beginTransaction();

            id = (Long) session.save(t);
            System.out.println("Thing saved with id:"+id);
            session.save(t);

            tx.commit();
        } catch(HibernateException ex) {
            if(tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }
    }


    public static List<Thing> listThings(Session session)
    {
        Transaction tx = null;
        List things = new ArrayList();
        try{
            tx = session.beginTransaction();
            things = (List)session.createQuery("From Thing").list();
            tx.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        }

        return things;
    }

    public static Thing getThing(Long id,Session session)
    {
        Transaction tx = null;
        Thing thing = null;
        try{
            tx = session.beginTransaction();
            //HQL query, note: HQL uses class names instead of table names, and property names instead of column names.
            Query q = session.createQuery("from Thing where id = :id");
            q.setParameter("id",id);
            List<Thing> list = q.list();
            if(list.size() == 1)
            {
                thing = list.get(0);
            }
            tx.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        }

        return thing;
    }
}
