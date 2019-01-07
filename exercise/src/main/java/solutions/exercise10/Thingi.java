package solutions.exercise10;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter

public class Thingi {

    String s1;
    String s2;
    Integer i1;
    Integer i2;

    public Thingi(String s1,String s2,Integer i1,Integer i2)
    {
        this.s1 = s1;
        this.s2 = s2;
        this.i1 = i1;
        this.i2 = i2;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        if(s1 != null)
        {
            hash += s1.length();
            byte[] bytes = s1.getBytes();
            for(byte b : bytes)
            {
                hash+=b;
            }
        }
        if(s2 != null)
        {
            hash += s2.length();
            byte[] bytes = s1.getBytes();
            for(byte b : bytes)
            {
                hash+=b;
            }
        }
        if(i1 != null)
        {
            hash += i1;
        }
        if(i2 != null)
        {
            hash += i2;
        }

        return hash;
    }

    @Override
    public boolean equals(Object t)
    {
        Thingi t_ = (Thingi)t;

        if(t_.getI1()+t_.getI2() == i1+i2 &&
                t_.getS1().length() == s1.length()&&
                t_.getS2().length() == s2.length()&&
                t_.hashCode() == hashCode())
        {
            return true;
        }

        return false;
    }
}
