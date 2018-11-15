package solutions.exercise5;

import java.math.BigDecimal;

public class Boardfield<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> {
    protected POS_TYPE positionOnBoard;
    protected POS_TYPE sizeOnBoard;
}
