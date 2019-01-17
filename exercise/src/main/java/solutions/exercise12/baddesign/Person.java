package solutions.exercise12.baddesign;

import java.util.List;

public class Person {
    Account acc;
    List<String> attributes;

    public Person(Account acc, String name, Integer age, Integer otherAddress
    , double accountBalance, int streetNumber, boolean house){
        //...
    }
}
