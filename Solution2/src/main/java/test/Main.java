package test;

import test.exercise2.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static int[] getRandomArray(int size)
    {
        int[] out = new int[size];

        Random rand = new Random();
        for(int i=0;i<size;i++)
        {
            out[i]=rand.nextInt(size);
        }

        return out;
    }

    public static void main(String[] args) {

        FieldPositionProjector<BigDecimal,Tuple2Dim<BigDecimal>> projector =
                (Tuple2Dim<BigDecimal> pos) -> {return ""+pos.m1.toString()+","+pos.m2.toString();};

        BoardImpl<BigDecimal,Tuple2Dim<BigDecimal>> board = new BoardImpl<>();
        board.setPositionProjector(projector);

        for(int y=0;y<4;y++)
        {
            for(int x=0;x<4;x++) {
                BoardfieldImpl<BigDecimal,Tuple2Dim<BigDecimal>> field = new BoardfieldImpl<>();
                Tuple2Dim<BigDecimal> position = new Tuple2Dim<>();
                Entity<BigDecimal, Tuple2Dim<BigDecimal>> entity = new Entity();
                entity.boardfield = field;
                position.m1 = new BigDecimal(x);
                position.m2 = new BigDecimal(y);
                field.setPositionOnBoard(position);
                board.addEntityToBoard(entity);
            }
        }

    }
}
