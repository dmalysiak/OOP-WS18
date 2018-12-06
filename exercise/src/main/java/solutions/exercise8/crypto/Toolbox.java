package solutions.exercise8.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */

public class Toolbox {
	
	double ngramDummy;
	
	public char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
		'u','v','w','x','y','z'};
	
	public Map<String,Integer> alphabet_index_map = new HashMap<String,Integer>();
	
	/*The entries represent the occurrences (in descending order) of a,b,c,d,e,f,g,h,i,j,...*/
	public char[] alphabet_language_occurences = {'e','n','i','s','r','a','t',
		'd','h','u','l','c','g','m','o','b','w','f','k','z',
		'p','v','j','y','x','q'};
	
	public char[] alphabet_language_occurences_en = {'e','t','a','o','i','n','s',
		'h','r','d','l','c','u','m','w','f','g','y','p','b',
		'v','k','j','x','q','z'};
	
	public String[] separate_trigrams_de = {"ein",
			"ich","die","und","der","den","das","aus","ist", "wir", 
			"wie", "auf", "sie", "des", "vor", "dem"};
	
	public String[] seperate_digrams_de = {"er", "ei", "es", "an", "ab", "am"};
	
	private int permCounter;
	
	public Toolbox()
	{
		createAlphabetMap();
		
		permCounter = 0;
	}
	
	/**
	 * Prints stats for vigenere ciphertexts.
	 * */
	public void printVigenereStats(String c, int max_key_size)
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
				key_s += alphabet[Toolbox.mod_(key[i],26)];
			}
			System.out.println("Keysize: "+key_size+" - "+key_s);
			System.out.println("");
			key_s ="";
			
		}
	}
	
	/**
	 * Substitutes the strings a...z chars with those from the given lookup table.
	 * */
	public String substitute(String s, Map<String,String> lookupTable)
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
	
	public double calculateNGramHistogramScore(Map<String,Double> table, String s, int n)
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
	public String removeAllSpecialChars(String s)
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
	public void printVigenereStatsShort(String c, int max_key_size)
	{
		Toolbox tb = new Toolbox();
		String[] groups = null;
		
		//print groups and calculate their sorted histograms
		int[] key = null;
		String key_s = "";
		
		//iterate through the various key sizes
		for(int key_size = 1;key_size<max_key_size;++key_size)
		{
			key = new int[key_size];
			groups = tb.createVigenereGroups(c, key_size);
			System.out.println("keysize "+key_size+" groups "+groups.length+" textsize "+c.length());
		
			//iterate over the groups
			for(int i = 0; i < key_size ;++i)
			{
				//System.out.println(i + ","+ groups[i].length()+ ":  " +groups[i]);
				int[] histogram = tb.getHistogram(groups[i]);
				int index = tb.getHighestIndex(histogram, 0);
				
				System.out.print(" "+calculateI_c(groups[i]));
				
				key[i] = index-4;
				key_s += alphabet[Toolbox.mod_(key[i],26)];
			}
			System.out.println(" Keysize: "+key_size+" : "+key_s);
			System.out.println("");
			key_s ="";
		}
	}
	
	
	public String[] createVigenereGroups(String c, int keyLength)
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
	
	public static String encodeStringToBase64String(String s)
	{
		try {
			return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "ERROR";
	}
	
	public static String encodeBytesToBase64String(byte[] b)
	{
		return Base64.getEncoder().encodeToString(b);
	}
	
	public static String encodeIntArrayToBase64String(int[] i)
	{
		byte[] b = new byte[i.length];
		
		for(int j=0;j<i.length;j++)
		{
			b[j] = (byte)i[j];
		}
		
		return Base64.getEncoder().encodeToString(b);
	}
	
	
	public static byte[] encodeStringToBase64Bytes(String s)
	{
		try {
			return Base64.getEncoder().encode(s.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static byte[] encodeBytesToBase64Bytes(byte[] b)
	{
		return Base64.getEncoder().encode(b);
	}
	
	public static byte[] encodeIntArrayToBase64Bytes(int[] i)
	{
		byte[] b = new byte[i.length];
		
		for(int j=0;j<i.length;j++)
		{
			b[j] = (byte)i[j];
		}
		
		return Base64.getEncoder().encode(b);
	}
	
	
	
	public static String decodeStringFromBase64String(String s)
	{
		return new String(Base64.getDecoder().decode(s));
	}
	
	public static byte[] decodeBytesFromBase64Bytes(byte[] b)
	{
		return (Base64.getDecoder().decode(b));
	}
	
	public static int[] decodeIntArrayFromBase64Bytes(byte[] b)
	{
		byte[] out = (Base64.getDecoder().decode(b));
		
		int[] out2 = new int[out.length];
		
		for(int j=0;j<out.length;j++)
		{
			out2[j] = (int)out[j];
		}
		
		return out2;
	}
	
	
	
	public double calculateI_c(String c)
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
	
	public double calculateFriedmanKeyLengthDE(String c)
	{
		double length = c.length();
		double I_s = 0.0732;
		double I_z = 0.0385;
		
		double I_c = calculateI_c(c);System.out.println(I_c);
		
		double l = length*(I_s-I_z) / (I_c*(length-1) + I_s - length*I_z);
		
		return l;
	}
	
	private void createAlphabetMap()
	{
		for(int i=0;i<26;++i)
		{
			alphabet_index_map.put(""+alphabet[i], i);
		}
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
	
	/**
	 * Creates a histogram table for n-grams which are loaded from the given file. Each table 
	 * entry is initially of the form (n-gram, 0). Redundant entries are skipped.
	 * Furthermore we assume that the n-grams are sorted in descending order of occurrence probability, 
	 * each line containing the n-gram followed by a space and an arbitrary amount of metadata.
	 * */
	public Map<String,Double> createNGramHistTable(String file)
	{
		Map<String, Double> table = new HashMap<String, Double>();
				
		double lineCount = 0;
		File fin = new File(file);
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
	    
	    ngramDummy = Math.log10(0.01/lineCount);
	    
	    //System.out.println(table.size());
		return table;
	}
	
	public int getPermCount(int startIndex,int endIndex,int maxAmount)
	{
		return Math.min(maxAmount,factorial(endIndex-startIndex));
	}
	
	/**
	 * Returns an array of integer arrays containing a permutation of the numbers in the range startIndex:endIndex 
	 * from 'input'.
	 * */
	public int[][] getPermutatedIndices(int[] input,int startIndex,int endIndex,int maxAmount)
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
	public void permutate(int[] list, int end, LinkedList<int[]> permutations, int maxPerms) 
	{
		permutate_( list, end, permutations, maxPerms);
		
		permCounter = 0;
		
		return;
	}
	
	private void permutate_(int[] list, int end, LinkedList<int[]> permutations, int maxPerms) 
	{
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
	public String removeSpecialChars(String s)
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
	

	public int[] translateStringToIntArray(String s)
	{
		String s_filtered = removeSpecialChars(s);
		
		int[] out = new int[s_filtered.length()];
		
		for(int i=0;i<s_filtered.length();++i)
		{
			out[i]  = alphabet_index_map.get(""+s_filtered.charAt(i));
		}
		
		return out;
	}
	
	public String translateIntArrayToString(int[] s)
	{	
		String out = "";
		
		for(int i=0;i<s.length;++i)
		{
			out  += alphabet[s[i]];
		}
		
		return out;
	}
	
	
	public boolean isLetter(char l)
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
	
	public int[] getHistogram(String s)
	{
		int[] result = new int[26];
		
		for(int i=0;i<26;i++)
		{
			result[i] = 0;
		}
		
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
	public int getHighestIndex(int[] histogram, int n)
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
	public Map<String,String> getLookupTable(int[] lookupIndices)
	{
		Map<String,String> m = new HashMap<String,String>();
		
		for(int i=0;i<26;++i)
		{
			m.put(""+alphabet[lookupIndices[i]], ""+alphabet_language_occurences[ i ] ); 
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
	
	public static byte[] getBytes(int count)
	{
		byte[] out = new byte[count];
		
		for(int j =0;j<count;++j)
		{
			out[j] = (byte)( -128 + (int)Math.floor( Math.random()*256 ) );
		}
		
		return out;
	}
	
	/**
	 * creates a randomized matrix with entries in the range [low, high].
	 * */
	public static Matrix2Dint getRandomMatrix(int rows, int columns, int low, int high)
	{
		Matrix2Dint out = new Matrix2Dint(rows,columns);
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<rows;++i)
		{
			for(int j =0;j<columns;++j)
			{
				rand = low + (int)Math.floor( Math.random()*range );
				out.setData(i, j, rand);
			}
		}
		
		return out;
	}
	
	/**
	 * creates a randomized matrix with entries in the range of ([low, high] mod n).
	 * */
	public static Matrix2Dint getRandomMatrixMod(int rows, int columns, int low, int high, int n)
	{
		Matrix2Dint out = new Matrix2Dint(rows,columns);
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<rows;++i)
		{
			for(int j =0;j<columns;++j)
			{
				rand = low + (int)Math.floor( Math.random()*range );
				out.setData(i, j, mod_(rand,n));
			}
		}
		
		return out;
	}
	
	/**
	 * creates a randomized array with entries in the range [low, high].
	 * */
	public static int[] getRandomArray(int size, int low, int high)
	{
		int[] out = new int[size];
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<size;++i)
		{
			rand = low + (int)Math.floor( Math.random()*range );
			out[i] = rand;
		}
		
		return out;
	}
	
	/**
	 * creates a random array with entries in the range of ([low, high] mod n).
	 * */
	public static int[] getRandomArrayMod(int size, int low, int high, int n)
	{
		int[] out = new int[size];
		int range = high-low;
		int rand = 0;
		
		for(int i =0;i<size;++i)
		{
			rand = low + (int)Math.floor( Math.random()*range );
			out[i] = mod_(rand,n);
		}
		
		return out;
	}
	
	/**
	 * randomizes the given arrays entries in the range of entry-low, entry+high.
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
	 * randomizes the given arrays entries in the range of entry-low mod n, entry+high mod n.
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
	 * randomizes one randomly chosen array entry in the range of entry-low, entry+high.
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
	 * randomizes one randomly chosen array entry in the range of entry-low mod n, entry+high mod n.
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

}
