package solutions.exercise3;

import solutions.exercise2.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Stream.of("a", "c", "d", "c", "c")
                .map(s -> {
                    return s.toUpperCase();
                })
                .filter(s -> {
                    return s.startsWith("C");
                })
                .forEach(System.out::println);

        ArrayList<String> a = new ArrayList<>();
        a.add("61");
        a.add("-4");
        a.add("1");
        final List<String> strings = Expression.doSomething(a);
        strings.stream().forEach(System.out::println);

    }

}
