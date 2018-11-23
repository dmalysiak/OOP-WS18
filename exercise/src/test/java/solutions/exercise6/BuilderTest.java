package solutions.exercise6;

import org.junit.Test;

public class BuilderTest {

    @Test
    public void shouldFailIfWrongOrder()
    {
        Exception ex = null;
        try {
            Container c = ContainerBuilder.getBuilder().setB("Test").setA(1).build();
        } catch (Exception e) {
             ex = e;
        }

        assert(ex != null);
        assert(ex.getMessage().equals("invalid order"));
    }

    @Test
    public void shouldSucceedIfRightOrder()
    {
        Exception ex = null;
        try {
            Container c = ContainerBuilder.getBuilder().setA(1).setB("Test").build();
        } catch (Exception e) {
            ex = e;
        }

        assert(ex == null);
    }

}
