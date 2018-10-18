package solutions.exercise2;

import java.math.BigDecimal;

public interface FieldPositionProjector<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> {
    public abstract String getPositionProjection(POS_TYPE pos);
}
