package solutions.exercise6;

import java.util.ArrayList;

public class ContainerA<T> implements ContainerInterface<T>{
    private ArrayList<T> data = new ArrayList<>();

    public int getSize(){return data.size();}

    public T get(int index){return data.get(index);}

    public void add(T val){data.add(val);}

    @Override
    public Iterator<T> getIterator() {
        return new ContainerIterator<T>(this);
    }
}
