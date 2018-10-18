package solutions.exercise2;

import java.math.BigDecimal;

public class BoardfieldImpl<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> extends Boardfield<T, POS_TYPE> {

    public void setPositionOnBoard(POS_TYPE pos)
    {
        this.positionOnBoard = pos;
    }

}
