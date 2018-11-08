package solutions.exercise4;

import org.junit.Test;

public class DatabaseConnectionTest {

    @Test
    public void savingShouldSucceed() {
        Container c = new Container("c1");
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String exceptionString = "";
        Exception ex = null;

        try {
            databaseConnection.saveContainer(c);
        } catch (Exception e) {
            exceptionString = e.getMessage();
        }

        assert (ex == null);
        assert (exceptionString.equals(""));
    }

    @Test
    public void redundantSavingShouldFail() {
        Container c = new Container("c1");
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String exceptionString = "";
        Exception ex = null;

        try {
            databaseConnection.saveContainer(c);
        } catch (Exception e) {
            ex = e;
            exceptionString = e.getMessage();
        }

        assert (ex == null);
        assert (exceptionString.equals(""));

        try {
            databaseConnection.saveContainer(c);
        } catch (Exception e) {
            ex = e;
            exceptionString = e.getMessage();
        }

        assert (ex != null);
        assert (exceptionString.equals("element already saved"));
    }

}
