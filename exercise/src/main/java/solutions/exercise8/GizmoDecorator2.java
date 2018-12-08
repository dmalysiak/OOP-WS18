package solutions.exercise8;

public class GizmoDecorator2 extends Gizmo {
    private Gizmo objectToDecorate;

    public GizmoDecorator2(Gizmo objectToDecorate) {
        this.objectToDecorate = objectToDecorate;
    }

    @Override
    public void doSomethingFunny() {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        System.out.println("░░░░░░░░░░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░░░░░░░░░");
        System.out.println("░░░░░░░░▄▀░░░░░░░░░░░░▄░░░░░░░▀▄░░░░░░░");
        System.out.println("░░░░░░░░█░░▄░░░░▄░░░░░░░░░░░░░░█░░░░░░");
        System.out.println("░░░░░░░░█░░░░░░░░░░░░▄█▄▄░░▄░░░█░▄▄▄░░░");
        System.out.println("░▄▄▄▄▄░░█░░░░░░▀░░░░▀█░░▀▄░░░░░█▀▀░██░░");
        System.out.println("░██▄▀██▄█░░░▄░░░░░░░██░░░░▀▀▀▀▀░░░░██░░");
        System.out.println("░░▀██▄▀██░░░░░░░░▀░██▀░░░░░░░░░░░░░▀██░");
        System.out.println("░░░░▀████░▀░░░░▄░░░██░░░▄█░░░░▄░▄█░░██░");
        System.out.println("░░░░░░░▀█░░░░▄░░░░░██░░░░▄░░░▄░░▄░░░██░");
        System.out.println("░░░░░░░▄█▄░░░░░░░░░░░▀▄░░▀▀▀▀▀▀▀▀░░▄▀░░");
        System.out.println("░░░░░░█▀▀█████████▀▀▀▀████████████▀░░░░░");
        System.out.println("░░░░░░████▀░░███▀░░░░░░▀███░░▀██▀░░░░░░");
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        objectToDecorate.doSomethingFunny();
    }
}
