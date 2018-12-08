package solutions.exercise8;

public class GizmoDecorator1 extends Gizmo {
    private Gizmo objectToDecorate;

    public GizmoDecorator1(Gizmo objectToDecorate) {
        this.objectToDecorate = objectToDecorate;
    }

    @Override
    public void doSomethingFunny() {
        objectToDecorate.name = "nyancat";
        objectToDecorate.doSomethingFunny();
    }
}
