package solutions.exercise6;

public class ContainerBuilder {
    private Container c = new Container();
    private ContainerBuilder(){}
    private boolean valid = false;
    public static ContainerBuilder getBuilder()
    {
        ContainerBuilder b = new ContainerBuilder();
        return b;
    }
    public ContainerBuilder setA(int a){c.setA(a);valid = true;return this;}
    public ContainerBuilder setB(String b) throws Exception
    {
        if(!valid)
        {
            throw new Exception("invalid order");
        }
        c.setB(b);
        return this;
    }
    public Container build() {return c;}
}
