package solutions.exercise4;

import org.junit.Test;

public class ContainerTest {

    @Test
    public void testAdditionOfValue() {
        Container c = new Container("c1");
        c.addValue(4);
        assert (c.value1 == 4);

        c.addValue(-4);
        assert (c.value1 == 0);
    }

}
