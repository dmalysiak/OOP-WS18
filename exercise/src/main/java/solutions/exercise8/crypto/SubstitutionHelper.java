package solutions.exercise8.crypto;

import java.util.Map;

public class SubstitutionHelper {

    /**
     * Substitutes the strings a...z chars with those from the given lookup table.
     * */
    public String substitute(String s, Map<String,String> lookupTable)
    {
        String p="";
        for(int i=0;i<s.length();++i)
        {
            if(CryptToolbox.isLetter(s.charAt(i)) == true)
            {
                p += lookupTable.get(""+s.charAt(i));
            }
            else
            {
                p +=  s.charAt(i);
            }
        }

        return p;
    }
}
