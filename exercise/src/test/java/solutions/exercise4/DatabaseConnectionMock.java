package solutions.exercise4;

public class DatabaseConnectionMock extends DatabaseConnection {

    int counter = 0;

    @Override
    public void connectToDB() {
    }

    @Override
    public void saveContainer(Container c) throws Exception {

        if (counter == 1) {
            throw new Exception("element already saved");
        }
        counter = (counter + 1) % 2;

    }

}
