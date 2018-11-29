package solutions.exercise7;

public class World {
    protected Entity e1;
    protected Entity e2;
    protected Entity e3;

    public void setE1(Entity e1){this.e1 = e1;}
    public void setE2(Entity e2){this.e2 = e2;}
    public void setE3(Entity e3){this.e3 = e3;}

    public Entity getE1(){return this.e1;}
    public Entity getE2(){return this.e2;}
    public Entity getE3(){return this.e3;}

    public void reportEntities()
    {
        System.out.println("Entity 1: Class "+e1.getClass()+", Name "+e1.getName());
        System.out.println("Entity 2: Class "+e2.getClass()+", Name "+e2.getName());
        System.out.println("Entity 3: Class "+e3.getClass()+", Name "+e3.getName());
    }
}
