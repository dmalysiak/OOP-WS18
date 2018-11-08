package solutions.exercise4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConnection {

    List<Container> database = new ArrayList<>();

    public void connectToDB() {
    }

    public void saveContainer(Container c) throws Exception {
        boolean saved = checkIfSaved(c);
        if (saved) {
            throw new Exception("element already saved");
        }
        database.add(c);
    }

    private boolean checkIfSaved(Container c) {
        List<Container> collect = database.stream().filter(e -> {
            if (e.getId().equals(c.getId())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        if (collect.size() != 0) {
            return true;
        }

        return false;
    }
}
