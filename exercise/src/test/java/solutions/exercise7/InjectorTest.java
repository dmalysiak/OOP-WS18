package solutions.exercise7;

import org.junit.Test;

import java.io.InputStream;

public class InjectorTest {

    @Test
    public void injectionShouldSucceed() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldSucceed.txt");
        Exception ex = null;
        World world = null;
        try {
            world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (Exception e) {
            ex = e;
        }
        assert(ex == null);

        assert(world.getE1().getClass().equals(Entity2.class));
        assert(world.getE2().getClass().equals(Entity1.class));
        assert(world.getE3().getClass().equals(Entity2.class));

        assert(world.getE1().getName().equals("somename1"));
        assert(world.getE2().getName().equals("anotherone2"));
        assert(world.getE3().getName().equals("testing3"));
    }

    @Test
    public void injectionShouldFailWithIOOBEIfNoEntriesInResource() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldFailWithIOOBEIfNoEntriesInResource.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (IndexOutOfBoundsException e) {
            ex = e;
        } catch (Exception e) {

        }
        assert(ex != null);
    }

    @Test
    public void injectionShouldFailWithIOOBEIfFewEntriesInResource() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldFailWithIOOBEIfFewEntriesInResource.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (IndexOutOfBoundsException e) {
            ex = e;
        } catch (Exception e) {

        }
        assert(ex != null);
    }

    @Test
    public void injectionShouldFailWithNPEWhenNoRessourceFound() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/---------------.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (NullPointerException e) {
            ex = e;
        } catch (Exception e) {

        }
        assert(ex != null);
    }

    @Test
    public void injectionShouldFailWithCNFEWhenUnknownClassInRessource() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldFailWithCNFEWhenUnknownClassInRessource.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (ClassNotFoundException e) {
            ex = e;
        } catch (Exception e) {

        }
        assert(ex != null);
    }

    @Test
    public void injectionShouldFailWithIAEWhenWrongSeperatorInRessource() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldFailWithIAEWhenWrongSeperatorInRessource.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (IllegalArgumentException e) {
            ex = e;
        } catch (Exception e) {

        }
        assert(ex != null);
    }

    @Test
    public void injectionShouldFailWithCCEWhenNoEntityChildInRessource() {
        InputStream resourceAsStream = Main.class.getResourceAsStream("/exercise7/injectionShouldFailWithCCEWhenNoEntityChildInRessource.txt");
        Exception ex = null;
        try {
            World world = Injector.getInstance().getWorld(resourceAsStream);
        } catch (ClassCastException e) {
            ex = e;
        } catch (Exception e) {
        }
        assert(ex != null);
    }

}
