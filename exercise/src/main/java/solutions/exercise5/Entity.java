package solutions.exercise5;

import java.math.BigDecimal;

public class Entity<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> {
    public Boardfield<T, POS_TYPE> boardfield;
}
