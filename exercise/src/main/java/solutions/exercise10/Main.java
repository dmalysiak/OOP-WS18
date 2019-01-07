package solutions.exercise10;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Person p1 = new Person("Hugo");
        Person p2 = new Person("Hugo");
        PersonVanillaJava p1_ = new PersonVanillaJava("Hugo");
        PersonVanillaJava p2_ = new PersonVanillaJava("Hugo");
        System.out.println("Lombok: "+p1.hashCode()+" <-> "+p2.hashCode());
        System.out.println("Vanilla Java: "+p1_.hashCode()+" <-> "+p2_.hashCode());
    }
}
