package solutions.exercise8.crypto;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */


public class Vigenere {
	
	final public String encrypt(String s_, String key_)
	{
		String s = s_.toLowerCase();
		String key = key_.toLowerCase();
		
		int key_length = key.length();
		int[] trans_key = CryptToolbox.translateStringToIntArray(key);
		int[] trans_message = CryptToolbox.translateStringToIntArray(s);
		
		int[] ciphertext_int = new int[trans_message.length];
		
		//encrypt the string
		encryptTheString(key_length, trans_key, trans_message, ciphertext_int);

		String ciphertext_string = CryptToolbox.translateIntArrayToString(ciphertext_int);
		
		return ciphertext_string;
	}

	final public String decrypt(String s_, String key_)
	{
		String s = s_.toLowerCase();
		String key = key_.toLowerCase();
		
		int key_length = key.length();
		int[] trans_key = CryptToolbox.translateStringToIntArray(key);
		int[] trans_message = CryptToolbox.translateStringToIntArray(s);
		
		int[] ciphertext_int = new int[trans_message.length];
		
		//encrypt the string
		decryptTheString(key_length, trans_key, trans_message, ciphertext_int);

		String ciphertext_string = CryptToolbox.translateIntArrayToString(ciphertext_int);
		
		return ciphertext_string;
	}

	protected void encryptTheString(int key_length, int[] trans_key, int[] trans_message, int[] ciphertext_int) {
		for(int i=0;i<trans_message.length;++i)
		{
			ciphertext_int[i] = ( trans_message[i] + trans_key[i % key_length] ) % 26;
		}
	}

	protected void decryptTheString(int key_length, int[] trans_key, int[] trans_message, int[] ciphertext_int) {
		for(int i=0;i<trans_message.length;++i)
		{
			ciphertext_int[i] = CryptToolbox.mod_( ( trans_message[i] - trans_key[i % key_length] ) , 26 );
		}
	}

}
