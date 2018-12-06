package solutions.exercise8.crypto;

import java.util.Comparator;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */


/**
 * It is assumed that the indices reside within the second entry
 */
public class IndexedIntegerArrayComparator implements Comparator<int[]>
{
	public IndexedIntegerArrayComparator()
	{}

	public int compare(int[] arg0, int[] arg1) {
		
		if(arg0[0] == arg1[0])
		{
			return 0;
		}
		if(arg0[0] < arg1[0])
		{
			return 1;
		}
		if(arg0[0] > arg1[0])
		{
			return -1;
		}
		return 0;
	}

}