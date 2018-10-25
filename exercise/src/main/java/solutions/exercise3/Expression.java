package solutions.exercise3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression {

    public static <T extends String> List<T> doSomething(List<T> a)
    {
        List<T> l = a.size() != 0 ? new ArrayList<>() : null;

        a.stream().forEach( ii ->
                l.add( (T) a.stream().map(Integer::parseInt)
                .map(e -> {return e;})
                .flatMap( e -> Arrays.stream(new Integer[]{e,1}))
                .reduce( (i,j) -> i>j ? i+j : j )
                .get().toString() ) );

        return l;
    }

}
