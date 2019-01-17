package solutions.exercise12.refactored.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import solutions.exercise11.Thing;
import solutions.exercise12.refactored.model.Bank;

import java.util.ArrayList;
import java.util.List;

public class PersistenceHelper {

    public static void save(Session session, Object entity)
    {
        Transaction tx = null;
        Long id = null;
        try {
            tx = session.beginTransaction();

            id = (Long) session.save(entity);
            System.out.println("Entity saved with id:"+id);
            session.save(entity);

            tx.commit();
        } catch(HibernateException ex) {
            if(tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        }
    }

    public static Bank getBank(Long id, Session session)
    {
        Transaction tx = null;
        Bank bank = null;
        try{
            tx = session.beginTransaction();
            //HQL query, note: HQL uses class names instead of table names, and property names instead of column names.
            Query q = session.createQuery("from Bank where id = :id");
            q.setParameter("id",id);
            List<Bank> list = q.list();
            if(list.size() == 1)
            {
                bank = list.get(0);
            }
            tx.commit();
        } catch(HibernateException e) {
            e.printStackTrace();
        }
        return bank;
    }
}
