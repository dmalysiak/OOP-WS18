package solutions.exercise4;

public class Container {
    Integer value1 = 0;
    String id;

    public Container(String id) {
        this.id = id;
    }

    @Deprecated
    public void addValue(Integer val) {
        value1 += val;
    }

    public String getId() {
        return id;
    }
}
