package solutions.exercise7;

import com.sun.org.apache.bcel.internal.util.ClassPath;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) {

        Thingi t = new Thingi();
        t.addObserver(()->System.out.println("observer1"));
        t.addObserver(()->System.out.println("observer2"));
        new Thread(t).start();

        Thingi2 t2 = new Thingi2();
        t2.addObserver((obj,arg)->{System.out.println("observer3");});
        t2.addObserver((obj,arg)->{System.out.println("observer4");});
        new Thread(t2).start();

        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/Config.txt");
        World world = null;
        try {
            world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        world.reportEntities();
    }
}
