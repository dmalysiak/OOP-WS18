package solutions.exercise6;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void doSomething(List<? super Number> numbers){}

    public static void main(String[] args) {
        try {
            Container c = ContainerBuilder.getBuilder().setA(1).setB("Test").build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ContainerA<Integer> c2 = new ContainerA<>();
        c2.add(2);c2.add(3);c2.add(4);
        Iterator<Integer> iterator = c2.getIterator();
        while(iterator.hasNext())
        {
            Integer next = iterator.next();
            System.out.println(next);
        }
    }
}
