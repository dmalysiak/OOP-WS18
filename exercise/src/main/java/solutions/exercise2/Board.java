package solutions.exercise2;

import java.math.BigDecimal;
import java.util.Map;

public abstract class Board<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> {
    protected FieldPositionProjector<T, POS_TYPE> positionProjector;
    protected Map<String, Entity<T, POS_TYPE>> entities;
    public abstract void addEntityToBoard(Entity<T,POS_TYPE> e);

}

