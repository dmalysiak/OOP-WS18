package solutions.exercise8.crypto;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */

public class CryptToolbox {
	
	public static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
		'u','v','w','x','y','z'};
	
	
	/*The entries represent the occurrences (in descending order) of a,b,c,d,e,f,g,h,i,j,...*/
	public static char[] alphabet_language_occurences_de = {'e','n','i','s','r','a','t',
		'd','h','u','l','c','g','m','o','b','w','f','k','z',
		'p','v','j','y','x','q'};
	
	public static char[] alphabet_language_occurences_en = {'e','t','a','o','i','n','s',
		'h','r','d','l','c','u','m','w','f','g','y','p','b',
		'v','k','j','x','q','z'};
	
	public static String[] separate_trigrams_de = {"ein",
			"ich","die","und","der","den","das","aus","ist", "wir", 
			"wie", "auf", "sie", "des", "vor", "dem"};
	
	public static String[] seperate_digrams_de = {"er", "ei", "es", "an", "ab", "am"};
	
	public CryptToolbox()
	{
	}
	
	/**
	 * This method does:
	 * 1) calculate the probable key length l according to Friedmanns method.
	 * 2) loop over [l-*key_low_delts, l+key_high_delta]
	 * 3)        calculate the vigenere groups for the key length
	 * 4)        find the letter with the largest occurrence in each vigenere group
	 * 5)        construct a key from these letters
	 * 6)        loop for 'iterations':
	 * 7)               variate the key at random position by changing its value at the position by a delta from [-5,5] (mod 26)
	 * 8)               decrypt the ciphertext with this key and calculate a 4gram score
	 * 9)               update the key if the score was improved and continue with this new key
	 * Returns the found key.
	 * */
	public static int[] vigenereHillClimbCrack(Vigenere v, String s_, int key_low_delta, int key_high_delta, int iterations)
	{
		InputStream resourceAsStream = CryptToolbox.class.getResourceAsStream("/exercise8/german_quadgrams.txt");
		double l = CryptToolbox.calculateFriedmanKeyLengthDE(s_);
		int l_ = (int)Math.ceil(l);
		
		String[] viginere_groups = null;
		
		double bestScore = -500000;
		Map<String,Double> ngrams = CryptToolbox.createNGramHistTable(resourceAsStream);
		double score = 0;
		
		int[] key = null;
		String key_s = "";
		String plaintext = "";

		resourceAsStream = CryptToolbox.class.getResourceAsStream("/exercise8/german_quadgrams.txt");
		double nGramDummy = CryptToolbox.getNGramDummy(resourceAsStream);
		
		for(int key_size = l_-key_low_delta;key_size < l_+key_high_delta;++key_size)
		{
			//create and analyze the groups for 'key_size'
			viginere_groups = CryptToolbox.createVigenereGroups(s_, key_size);
			
			key = new int[key_size];
			key_s = "";
			
			//create the groups and determine the most likely key
			for(int i = 0; i < key_size ;++i)
			{
				int[] histogram = CryptToolbox.getHistogram(viginere_groups[i]);
				int index = CryptToolbox.getHighestIndex(histogram, 0);
				
				key[i] = CryptToolbox.mod_(index,26);
				key_s += CryptToolbox.alphabet[key[i]];
			}
			
			//variate the key for the current key length
			for(int random_its = 0; random_its < iterations; ++random_its)
			{
				int[] key_ = CryptToolbox.randomizeArrayREMod(key,-5,5,26);
				String key_s_ = CryptToolbox.translateIntArrayToString(key_);
				
				plaintext = v.decrypt(s_, key_s_);
				
				score = CryptToolbox.calculateNGramHistogramScore(ngrams,plaintext,4,nGramDummy);
				
				//keep the better indices
				if(score>bestScore)
				{
					key = key_.clone();
					bestScore = score;

					System.out.println(plaintext);
					System.out.println(score);
					System.out.println(key_s_);
					
				}
			}
			
		}
		
		return key;
	}
	
	/**
	 * This method will first calculate a lookup table according to the letter occurrences. Afterwards it will perform 
	 * a iterative search by randomly tranpositioning lookup indices and compare 4gram scores. If the score was improved 
	 * these indices will be kept and used for the next iteration.
	 * Returns the lookup indices.
	 * */
	public static int[] hillclimbCrack(SubstitutionHelper helper, String s_, int iterations)
	{
		String s = s_.toLowerCase();
		
		//check which cipher it might be
		//CryptToolbox.printVigenereStatsShort(CryptToolbox.removeAllSpecialChars(s),20);
		
		int[] histogram = CryptToolbox.getHistogram(s);
		
		//print the histogram
		for(int i=0;i<26;++i)
		{
			System.out.print("("+histogram[i]+","+i+"/"+CryptToolbox.alphabet[i]+") ");
		}
		System.out.println("");
		
		//sort and print the indices
		int[] lookupIndices = new int[26];
		for(int i=0;i<26;++i)
		{
			lookupIndices[i] = CryptToolbox.getHighestIndex(histogram, i);
			System.out.print("("+histogram[ lookupIndices[i] ]+ ","+lookupIndices[i] + "/"+CryptToolbox.alphabet[lookupIndices[i]]+") ");
		}

		
		//do a hill-climbing search
		InputStream resourceAsStream = CryptToolbox.class.getResourceAsStream("/exercise8/german_quadgrams.txt");
		Map<String,Double> ngrams = CryptToolbox.createNGramHistTable(resourceAsStream);//System.exit(0);

		resourceAsStream = CryptToolbox.class.getResourceAsStream("/exercise8/german_quadgrams.txt");
		double nGramDummy = CryptToolbox.getNGramDummy(resourceAsStream);

		double score = CryptToolbox.calculateNGramHistogramScore(ngrams,s,4,nGramDummy);
		
		Map<String,String> lookupTable = CryptToolbox.getLookupTable(lookupIndices);
		
		int[] transposedArray = null;

		double bestScore = -50000;
		
		String p = "";
		
		for(int i=0;i<iterations;++i)
		{
			
			transposedArray = CryptToolbox.randomTransposition(lookupIndices);
			//transposedArray = randomizeArrayREMod(lookupIndices,-5,5,26);
			
			lookupTable = CryptToolbox.getLookupTable(transposedArray);
			p = helper.substitute(s, lookupTable);
			//ngrams = tb.createNGramHistTable("german_quadgrams.txt");
			
			score = CryptToolbox.calculateNGramHistogramScore(ngrams,p,4, nGramDummy);
			
			//keep the better indices
			if(score>bestScore)
			{
				lookupIndices = transposedArray.clone();
				bestScore = score;

				System.out.println(p);
				System.out.println(score);
			}
			
		}
		
		return transposedArray;
	}
	
	/**
	 * Prints stats for vigenere ciphertexts.
	 * */
	public static void printVigenereStats(String c, int max_key_size)
	{
		String[] groups = null;
		
		//print groups and calculate their sorted histograms
		int[] key = null;
		String key_s = "";
		
		//iterate through the various key sizes
		for(int key_size = 1;key_size<max_key_size;++key_size)
		{
			key = new int[key_size];
			groups = createVigenereGroups(c, key_size);
		
			//iterate over the groups
			for(int i = 0; i < key_size ;++i)
			{
				System.out.println(i + ","+ groups[i].length()+ ":  " +groups[i]);
				int[] histogram = getHistogram(groups[i]);
				int index = getHighestIndex(histogram, 0);
				/*System.out.println("Most common letter "+tb.alphabet[index]);
				System.out.println("4->"+index+" => k="+(index-4));
				System.out.println("Count "+histogram[index]);*/
				
				System.out.println(""+calculateI_c(groups[i]));
				
				key[i] = index-4;
				key_s += alphabet[CryptToolbox.mod_(key[i],26)];
			}
			System.out.println("Keysize: "+key_size+" - "+key_s);
			System.out.println("");
			key_s ="";
			
		}
	}
	
	/**
	 * Substitutes the strings a...z chars with those from the given lookup table.
	 * */
	public static String substitute(String s, Map<String,String> lookupTable)
	{
		String p="";
		for(int i=0;i<s.length();++i)
		{
			if(isLetter(s.charAt(i)) == true)
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
	
	public static double calculateNGramHistogramScore(Map<String,Double> table, String s, int n, double ngramDummy)
	{
		s = removeSpecialChars(s);
		
		int length = s.length();
		double out = 0;
		
		for(int i=0; i < length-n; ++i )
		{
			String ngram = s.substring(i, i+n);
			//System.out.println(ngram);
			//count only the existing ngrams
			if(table.get(ngram) != null)
			{
				//System.out.println(""+ table.get(ngram) );
				out += table.get(ngram);
			}
			else
			{
				out += ngramDummy;
			}
		}
		
		return out;
		
	}
	
	/**
	 * Removes all chars from the string which are not in a...z, furthermore it converts
	 * the string to lower case before proceeding.
	 * */
	public static String removeAllSpecialChars(String s)
	{
		int length = s.length();
		String out = "";
		String s_ = s.toLowerCase();
		
		for(int i=0; i < length; ++i )
		{
			if(isLetter(s_.charAt(i)) == true)
			{
				out += s_.charAt(i);
			}
		}
		
		return out;
	}
	
	/**
	 * Transposes the given array at two random points, returns a transpose copy!
	 * */
	public static int[] randomTransposition(int[] array)
	{
		int[] out = array.clone();
		
		int max = array.length;
		int randA = (int)Math.floor( (Math.random() * max ) );
		int randB = (int)Math.floor( (Math.random() * max ) );
		
		if(randA > randB)
		{
			int a = randA;
			randA = randB;
			randB = a;
		}
		
		if(randA == randB)
		{
			return out;
		}
		
		int a = out[randA];
		out[randA] = out[randB];
		out[randB] = a;
		
		return out;
	}
	
	/**
	 * Prints stats for vigenere cipher texts, this time only the coincidence values for all groups.
	 * */
	public static void printVigenereStatsShort(String c, int max_key_size)
	{
		String[] groups = null;
		
		//print groups and calculate their sorted histograms
		int[] key = null;
		String key_s = "";
		
		//iterate through the various key sizes
		for(int key_size = 1;key_size<max_key_size;++key_size)
		{
			key = new int[key_size];
			groups = createVigenereGroups(c, key_size);
			System.out.println("keysize "+key_size+" groups "+groups.length+" textsize "+c.length());
		
			//iterate over the groups
			for(int i = 0; i < key_size ;++i)
			{
				//System.out.println(i + ","+ groups[i].length()+ ":  " +groups[i]);
				int[] histogram = CryptToolbox.getHistogram(groups[i]);
				int index = CryptToolbox.getHighestIndex(histogram, 0);
				
				System.out.print(" "+calculateI_c(groups[i]));
				
				key[i] = index-4;
				key_s += alphabet[CryptToolbox.mod_(key[i],26)];
			}
			System.out.println(" Keysize: "+key_size+" : "+key_s);
			System.out.println("");
			key_s ="";
		}
	}
	
	
	public static String[] createVigenereGroups(String c, int keyLength)
	{
		int groupCount = keyLength;// (int)Math.ceil((double)c.length()/(double)keyLength);
		
		String c_ = c.toLowerCase();
		String[] groups = new String[groupCount];
		
		for(int i=0;i<groupCount;++i)
		{
			int counter = 0;
			String s="";
			
			while(true)//yeah...
			{
				//check if next index is legit for this group
				if(i+counter*keyLength >= c_.length())
				{
					break;
				}
				
				s += c_.charAt( i+counter*keyLength );
				++counter;
			}
			
			groups[i] = s;
		}
		
		return groups;
	}
	
	public static double calculateI_c(String c)
	{
		double ic = 0;
		double l=c.length();
		
		int[] histogram = getHistogram(c.toLowerCase());
		
		for(int i=0;i<26;++i)
		{
			ic += histogram[i]*(histogram[i]-1);
		}

		return (ic/(l*(l-1)));
	}
	
	public static double calculateFriedmanKeyLengthDE(String c)
	{
		double length = c.length();
		double I_s = 0.0732;
		double I_z = 0.0385;
		
		double I_c = calculateI_c(c);
		
		double l = length*(I_s-I_z) / (I_c*(length-1) + I_s - length*I_z);
		
		return l;
	}
	
	public static Map<String,Integer> createAlphabetMap()
	{

		Map<String,Integer> alphabet_index_map = new HashMap<String,Integer>();
		
		for(int i=0;i<26;++i)
		{
			alphabet_index_map.put(""+alphabet[i], i);
		}
		
		return alphabet_index_map;
	}
	
	public static int factorial(int n) 
	{
        int out = 1;
        for(int i = 1; i <= n; ++i) 
        {
            out *= i;
        }
        return out;
    }
	
	public static void printHex(int[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print("0x"+ Integer.toHexString(c[i]) +"\t");
		}System.out.println();
	}
	
	public static void writeHex(int[] c, String file_name)
	{
		File file = new File(file_name);//System.out.println(" "+i);
		 

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter bw= new BufferedWriter(fw);
		
		for(int i=0;i<c.length;++i)
		{
			try {
				bw.write("0x"+ Integer.toHexString(c[i]) +"\t");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeHex(byte[] c, String file_name)
	{
		File file = new File(file_name);//System.out.println(" "+i);
		 

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter bw= new BufferedWriter(fw);
		
		for(int i=0;i<c.length;++i)
		{
			try {
				bw.write("0x"+ Integer.toHexString(c[i]) +"\t");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeHex(char[] c, String file_name)
	{
		File file = new File(file_name);//System.out.println(" "+i);
		 

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter bw= new BufferedWriter(fw);
		
		for(int i=0;i<c.length;++i)
		{
			try {
				bw.write("0x"+ Integer.toHexString(c[i]) +"\t");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void printHex(byte[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print("0x"+ Integer.toHexString(c[i]) +"\t");
		}System.out.println();
	}
	
	public static void printHex(char[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print("0x"+ Integer.toHexString(c[i]) +"\t");
		}System.out.println();
	}
	
	public static void printStringChars(byte[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print(""+ (char)c[i] +"\t");
		}System.out.println();
	}
	
	public static void printStringChars(int[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print(""+ (char)c[i] +"\t");
		}System.out.println();
	}
	
	public static void printStringChars(char[] c)
	{
		for(int i=0;i<c.length;++i)
		{
			System.out.print(""+ (char)c[i] +"\t");
		}System.out.println();
	}
	
	public static void printNumbers(int min, int max)
	{
		int size = max-min;
		for(int i=min;i<min+size;++i)
		{
			System.out.print(""+ i +"\t");
		}System.out.println();
	}
	
	
	public static int nChoosek(int n, int k)
	{
		return (factorial(k))/( factorial(k)*factorial(n-k) );
	}
	
	public static double getNGramDummy(InputStream file)
	{
		double lineCount = 0;
		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));

		try 
		{
			while (reader.readLine() != null) 
			{
				++lineCount;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
		try 
		{
			reader.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Math.log10(0.01/lineCount);
	    
		
	}
	
	/**
	 * Creates a histogram table for n-grams which are loaded from the given file. Each table 
	 * entry is initially of the form (n-gram, 0). Redundant entries are skipped.
	 * Furthermore we assume that the n-grams are sorted in descending order of occurrence probability, 
	 * each line containing the n-gram followed by a space and an arbitrary amount of metadata.
	 * */
	public static Map<String,Double> createNGramHistTable(InputStream file)
	{
		Map<String, Double> table = new HashMap<String, Double>();
				
		double lineCount = 0;
		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
	 
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				table.put(line.toLowerCase().split(" ")[0], Double.valueOf(line.toLowerCase().split(" ")[1]));
				++lineCount;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
		try 
		{
			reader.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//normalize the entries
		Iterator<Entry<String, Double>> it = table.entrySet().iterator();
	    while(it.hasNext()) 
	    {
	        Entry<String,Double> pair = (Entry<String, Double>)it.next();
	        
	        //System.out.println(pair.getKey()+" " +Math.log( pair.getValue()/lineCount));
	        table.put(pair.getKey(), Math.log10( pair.getValue()/lineCount) );
	    }
	    
	    //System.out.println(table.size());
		return table;
	}
	
	public static int getPermCount(int startIndex,int endIndex,int maxAmount)
	{
		return Math.min(maxAmount,factorial(endIndex-startIndex));
	}
	
	/**
	 * Returns an array of integer arrays containing a permutation of the numbers in the range startIndex:endIndex 
	 * from 'input'.
	 * */
	public static int[][] getPermutatedIndices(int[] input,int startIndex,int endIndex,int maxAmount)
	{
		int max = Math.min(maxAmount,factorial(endIndex-startIndex));
		
		int[][] out = new int[max][input.length];
		
		//create the subarray which will hold the permutations
		int[] subArray = new int[ endIndex-startIndex ];
		
		for(int i=0;i<endIndex-startIndex;++i)
		{
			subArray[i] = input[startIndex+i];
		}
		
		LinkedList<int[]> permutations = new LinkedList<int[]>();
		
		permutate(subArray,0,permutations,max);
		
		//copy the permutations back into the main array
		int[] perm_subArray;
		for(int i=0;i<max;++i)
		{
			perm_subArray = permutations.get(i);
			
			out[i] = input.clone();
			
			for(int j=0;j<endIndex-startIndex;++j)
			{
				out[i][startIndex+j] = perm_subArray[j];
			}
		}
		
		return out;
	}
	
	/**
	 * The limitation of permutations only effects the number of inserted elements, not the iteration count!
	 * */
	public static void permutate(int[] list, int end, LinkedList<int[]> permutations, int maxPerms) 
	{
		permutate_( list, end, permutations, maxPerms);
		
		return;
	}
	
	private static void permutate_(int[] list, int end, LinkedList<int[]> permutations, int maxPerms) 
	{
		int permCounter = 0;
		
	    if(end == list.length) {
	       
	    	if(permCounter < maxPerms)
	    	{
		    	permutations.add(list);
		        ++permCounter;
	    	}
	    	
	        return;
	    }
	    for(int i = end; i < list.length; i++) 
	    {
	        int[] permutation = (int[])list.clone();
	        permutation[end] = list[i];
	        permutation[i] = list[end];
	        
	        permutate_(permutation, end + 1, permutations, maxPerms);
	    }
	}
	
	/**
	 * Removes all chars from the string which are not in a...z.
	 * */
	public static String removeSpecialChars(String s)
	{
		String out = "";
		
		for(int i=0;i<s.length();++i)
		{
			if(isLetter(s.charAt(i))==true)
			{
				out += s.charAt(i);
			}
		}
		
		return out;
	}
	

	public static int[] translateStringToIntArray(String s)
	{
		String s_filtered = removeSpecialChars(s);
		
		Map<String, Integer> alphabet_index_map = CryptToolbox.createAlphabetMap();
		
		int[] out = new int[s_filtered.length()];
		
		for(int i=0;i<s_filtered.length();++i)
		{
			out[i]  = alphabet_index_map.get(""+s_filtered.charAt(i));
		}
		
		return out;
	}
	
	public static String translateIntArrayToString(int[] s)
	{	
		String out = "";
		
		for(int i=0;i<s.length;++i)
		{
			out  += alphabet[s[i]];
		}
		
		return out;
	}
	
	public static boolean isLetter(char l)
	{
		boolean out = false;
		
		for(int i=0;i<26;++i)
		{
			if(l == alphabet[i])
			{
				out=true;
				break;
			}
		}
		
		return out;
	}
	
	public static int[] getHistogram(String s)
	{
		int[] result = new int[26];
		
		for(int i=0;i<26;i++)
		{
			result[i] = 0;
		}
		
		Map<String, Integer> alphabet_index_map = CryptToolbox.createAlphabetMap();
		
		for(int i=0;i<s.length();++i)
		{
			//only count letters
			if(isLetter(s.charAt(i)) == true)
			{
				result[ alphabet_index_map.get( ""+s.charAt(i) ) ]++;
			}
		}
		
		return result;
	}
	
	/*
	 * Returns the n-th highest index of the current histogram.
	 * */
	public static int getHighestIndex(int[] histogram, int n)
	{
		//create a 2 dimensional array
		int[][] ex_hist = new int[26][2];
		for(int i=0;i<26;++i)
		{
			ex_hist[i][0] = histogram[i];
			ex_hist[i][1] = i;
		}
		
		Arrays.sort(ex_hist,new IndexedIntegerArrayComparator());
		 
		return ex_hist[n][1]; 
	}
	
	/**
	 * Returns a lookup table in form of an associative array.
	 * This map is indexed via source char and contains the corresponding target char.
	 * */
	public static Map<String,String> getLookupTable(int[] lookupIndices)
	{
		Map<String,String> m = new HashMap<String,String>();
		
		for(int i=0;i<26;++i)
		{
			m.put(""+alphabet[lookupIndices[i]], ""+alphabet_language_occurences_de[ i ] ); 
		}
		
		return m;
	}
	
	public static int[] calculateWordPattern(String s)
	{
		int[] out = new int[s.length()];
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		int symbol = 0;
		
		for(int i=0;i<s.length();++i)
		{
			//check if the key already exists
			if(map.containsKey(""+s.charAt(i))==true)
			{
				out[i] = map.get(""+s.charAt(i));
			}
			else //a new symbol
			{
				++symbol;
				map.put(""+s.charAt(i), symbol);
				out[i] = symbol;
			}
		}
		
		return out;
	}
	
	/**
	 * Returns true in case of matching patterns, false otherwise.
	 * */
	public static boolean compareWordPatterns(int[] a, int[] b)
	{
		boolean out = true;
		
		if(a.length != b.length)
		{
			return false;
		}
		
		for(int i=0;i<a.length;++i)
		{
			if(a[i] != b[i])
			{
				out=false;
				break;
			}
		}
		
		return out; 
	}
	
	/**
	 * Performs a simple search (compares letter patterns) in the dictionary. Returns a list of matched words.
	 * */
	public static void findMatchingWordsFromDictionary(String dictionaryFile, 
			String word, LinkedList<String> matches)
	{
		File fin = new File(dictionaryFile);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//Construct BufferedReader from InputStreamReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		int[] wordPattern = calculateWordPattern(word);
		try 
		{
			while ((line = reader.readLine()) != null) 
			{
				//skip all words with different sizes
				if(line.trim().length() != word.length())
				{
					continue;
				}
				else
				{
					int[] dictPattern = calculateWordPattern(line);
					
					if(compareWordPatterns(dictPattern, wordPattern) == true)
					{
						matches.add(line.trim());
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
		try 
		{
			reader.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int mod_(int i, int m)
	{
		int out = i % m;
		
		if(out<0)
		{
			return out+m;
		}
		
		return out;
	}
	
	/**
	 * randomizes the given arrays entries in the range to entry-low, entry+high.
	 * */
	public static int[] randomizeArray(int[] in, int low, int high)
	{
		int[] out = new int[in.length];
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<in.length;++i)
		{
			rand = low + (int)Math.floor( Math.random()*range );
			out[i] = in[i] + rand;
		}
		
		return out;
	}
	
	/**
	 * randomizes the given arrays entries in the range to entry-low mod n, entry+high mod n.
	 * */
	public static int[] randomizeArrayMod(int[] in, int low, int high, int n)
	{
		int[] out = new int[in.length];
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<in.length;++i)
		{
			rand = low + (int)Math.floor( Math.random()*range );
			out[i] = mod_(in[i] +rand, n);
		}
		
		return out;
	}
	
	/**
	 * RandomEntry randomization.
	 * randomizes one randomly chosen array entry in the range to entry-low, entry+high.
	 * */
	public static int[] randomizeArrayRE(int[] in, int low, int high)
	{
		int[] out = in.clone();
		int range = high-low;
		int rand = 0;
		
		int i = (int)Math.floor( Math.random()*out.length );
		rand = low + (int)Math.floor( Math.random()*range );
		out[i] = in[i] + rand;
		
		return out;
	}
	
	/**
	 * RandomEntry randomization modulo n.
	 * randomizes one randomly chosen array entry in the range to entry-low mod n, entry+high mod n.
	 * */
	public static int[] randomizeArrayREMod(int[] in, int low, int high, int n)
	{
		int[] out = in.clone();
		int range = high-low;
		int rand = 0;
		
		int i = (int)Math.floor( Math.random()*out.length );
		rand = low + (int)Math.floor( Math.random()*range );
		out[i] = mod_(in[i] +rand, n);
		
		
		return out;
	}
	
	/**
	 * Returns a lookup table in form of an associative array.
	 * This map is indexed via source char and contains the corresponding target char.
	 * */
	static Map<String,String> getStaticLookupTable()
	{
		Map<String,String> m = new HashMap<String,String>();
		
		m.put("a", "a");
		m.put("b", "a");
		m.put("c", "a");
		m.put("d", "a");
		m.put("e", "a");
		m.put("f", "t");
		m.put("g", "a");
		m.put("h", "a");
		m.put("i", "s");
		m.put("j", "a");
		m.put("k", "a");
		m.put("l", "a");
		m.put("m", "a");
		m.put("n", "a");
		m.put("o", "s");
		m.put("p", "a");
		m.put("q", "a");
		m.put("r", "a");
		m.put("s", "e");
		m.put("t", "a");
		m.put("u", "a");
		m.put("v", "a");
		m.put("w", "a");
		m.put("x", "a");
		m.put("y", "i");
		m.put("z", "a");
		
		return m;
	}
	

}
