package solutions.exercise11;

import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Session session = SessionHelper.getSessionFactory().openSession();

        Pikachu pikachu = new Pikachu();
        PersistenceHelper.saveThing(session,pikachu);
        PersistenceHelper.saveThing(session,new Onix());
        List<Thing> things = PersistenceHelper.listThings(session);
        things.stream().forEach(t -> System.out.println("Obtained1 "+t));

        //check for identical Pikachus
        things.stream().forEach(t -> {
            if(t.getName().compareTo("Pikachu")==0){
                System.out.println("Is the returned Pikachu identical? -> "+t.equals(pikachu));
            }
        });
        /*
        * The returned object is identical to the initial one since hibernate keeps a reference to the initial one within the current session.
        * Hibernate will also update the entities id.
        * */

        session.close();


        checkIdentityAgainWithDifferentSession(pikachu);
    }

    private static void checkIdentityAgainWithDifferentSession(Pikachu pikachu)
    {
        Session session = SessionHelper.getSessionFactory().openSession();

        List<Thing> things = PersistenceHelper.listThings(session);
        things.stream().forEach(t -> System.out.println("Obtained2 "+t));

        //check for identical Pikachus
        things.stream().forEach(t -> {
            if(t.getName().compareTo("Pikachu")==0){
                System.out.println("Is the returned Pikachu identical? -> "+t.equals(pikachu));
            }
        });
        /*
         * The returned object is not identical to the initial one since hibernate uses a new session.
         * */
        session.close();
    }

}
