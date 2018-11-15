package solutions.exercise5;

import java.math.BigDecimal;
import java.util.HashMap;

public class BoardImpl<T extends BigDecimal, POS_TYPE extends Tuple2Dim<T>> extends Board<T, POS_TYPE> {

    public void setPositionProjector(FieldPositionProjector<T,POS_TYPE> projector)
    {
        this.positionProjector = projector;
    }

    @Override
    public void addEntityToBoard(Entity<T, POS_TYPE> e) {
        if(this.entities == null)
        {
            this.entities = new HashMap<>();
        }

        String posProj = this.positionProjector.getPositionProjection(e.boardfield.positionOnBoard);
        this.entities.put(posProj,e);
        System.out.println(posProj);
    }
}
