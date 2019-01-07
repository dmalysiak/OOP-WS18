package solutions.exercise10;

public class PersonVanillaJava {
    public String name;
    public transient Integer age=0;

    public PersonVanillaJava(String name)
    {
        this.name = name;
    }
}
