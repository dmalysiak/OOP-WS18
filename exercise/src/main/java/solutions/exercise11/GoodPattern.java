package solutions.exercise11;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which represents an example for good
 * coding. One should study its structure.
 * @since       1.0
 * */
public class GoodPattern {
    public final static String KEY1="Object1";
    public final static String KEY2="Object2";

    private Map<String, Object> map = new HashMap<>();

    public GoodPattern(){
        initMembers();
    }

    private void initMembers(){
        map.put(KEY1,new Object());
        map.put(KEY2,new Object());
    }
}
