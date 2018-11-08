package solutions.exercise4;

import org.junit.Test;
import solutions.exercise4.Container;

public class SaveServiceTest {

    @Test
    public void testSaving() {
        Container c = new Container("c1");
        SaveService s = new SaveService(new DatabaseConnectionMock());
        String exceptionString = "";
        Exception ex = null;

        try {
            s.saveContainer(c);
        } catch (Exception e) {
            ex = e;
            exceptionString = e.getMessage();
        }

        assert (ex == null);
        assert (exceptionString.equals(""));

        try {
            s.saveContainer(c);
        } catch (Exception e) {
            ex = e;
            exceptionString = e.getMessage();
        }

        assert (ex != null);
        assert (exceptionString.equals("element already saved"));
    }

}
