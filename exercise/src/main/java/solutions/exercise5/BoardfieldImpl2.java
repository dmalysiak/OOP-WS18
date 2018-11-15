package solutions.exercise5;

import java.math.BigDecimal;

public class BoardfieldImpl2 extends BoardfieldImpl<BigDecimal, Tuple2Dim<BigDecimal>> {

    //Invariant resp. parameter
    public void setPositionOnBoard(Tuple2Dim<BigDecimal> pos)
    {
        this.positionOnBoard = pos;
    }

    //Covariant resp. parameter
    public void setPositionOnBoard(Tuple3Dim<BigDecimal> pos)
    {
        this.positionOnBoard = pos;
    }

    //Contravariant resp. parameter
    public void setPositionOnBoardByObject(Object pos)
    {
        this.positionOnBoard = (Tuple2Dim<BigDecimal>)pos;
    }

    //Covariant resp. return type
    public Tuple3Dim<BigDecimal> getPositionOnBoard()
    {
        return (Tuple3Dim<BigDecimal>)this.positionOnBoard;
    }

}
