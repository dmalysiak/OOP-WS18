package solutions.exercise10;

import org.junit.Test;

public class EqualsAndHashcodeTest {

    @Test
    public void shouldSucceedIfObjectsIdentical()
    {
        Thingi t1 = new Thingi("t1","t2",1,2);
        Thingi t2 = new Thingi("t1","t2",1,2);

        assert(t1.equals(t2));
    }

    @Test
    public void shouldSucceedIfObjectsDiffer()
    {
        Thingi t1 = new Thingi("t2","t2",1,2);
        Thingi t2 = new Thingi("t1","t2",1,2);

        assert(!t1.equals(t2));

        t1 = new Thingi("t1","t2",1,2);
        t2 = new Thingi("t2","t2",1,2);

        assert(!t1.equals(t2));

        t1 = new Thingi("t1","t2",2,1);
        t2 = new Thingi("t1","t2",2,2);

        assert(!t1.equals(t2));
    }

    @Test
    public void shouldSucceedIfHashcodeIsIdenticalForIdenticalFields()
    {
        Thingi t1 = new Thingi("t1","t2",1,2);
        Thingi t2 = new Thingi("t1","t2",1,2);

        assert(t1.hashCode() == t2.hashCode());
    }

    @Test
    public void shouldSucceedIfHashcodeIsNonIdenticalForDifferentFields()
    {
        Thingi t1 = new Thingi("t1","t2",1,2);
        Thingi t2 = new Thingi("t1","t2",1,1);

        assert(t1.hashCode() != t2.hashCode());

        t1 = new Thingi("t1","t2",1,2);
        t2 = new Thingi("t1","t2",1,1);

        assert(t1.hashCode() != t2.hashCode());

        t1 = new Thingi("t2","t2",1,2);
        t2 = new Thingi("t1","t2",1,2);

        assert(t1.hashCode() != t2.hashCode());

        //this one is is a limitation of our approach :-(
        /*t1 = new Thingi("t1","t2",1,2);
        t2 = new Thingi("t2","t1",1,2);

        assert(t1.hashCode() != t2.hashCode());*/
    }


}
