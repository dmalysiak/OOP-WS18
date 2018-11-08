package solutions.exercise4;

public class SaveService {
    DatabaseConnection connection;

    public SaveService(DatabaseConnection connection) {
        this.connection = connection;
    }

    public void saveContainer(Container c) throws Exception {
        connection.saveContainer(c);
    }
}
