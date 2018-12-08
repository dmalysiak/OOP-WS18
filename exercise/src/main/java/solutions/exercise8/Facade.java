package solutions.exercise8;

public class Facade {
    Senpai s = new Senpai();
    Gizmo g;
    public void setup(String name)
    {
        g = new GizmoImpl();
        g.name = name;
    }

    public void getSenpaisOpinion()
    {
        s.do5(g);
    }
}
