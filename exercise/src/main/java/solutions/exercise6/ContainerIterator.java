package solutions.exercise6;

public class ContainerIterator<T> implements Iterator<T> {
    private ContainerA<T> container;
    private T currentElement;
    private int counter=0;

    public ContainerIterator(ContainerA<T> container)
    {
        this.container = container;
    }

    @Override
    public boolean hasNext() {
        if(counter<container.getSize())
        {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        currentElement = container.get(counter);
        counter++;
        return currentElement;
    }
}
