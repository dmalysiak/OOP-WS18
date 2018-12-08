package solutions.exercise8;

import solutions.exercise8.crypto.CryptToolbox;
import solutions.exercise8.crypto.Vigenere;

public class VigenereImproved extends Vigenere {
    protected void encryptTheString(int key_length, int[] trans_key, int[] trans_message, int[] ciphertext_int) {
        for(int i=0;i<trans_message.length / 2; i+=2)
        {
            ciphertext_int[i] = ( trans_message[i] + trans_key[i % key_length] ) % 26;
            ciphertext_int[i+1] = ( trans_message[i+1] + trans_key[(i+2) % key_length] ) % 26;
        }

        if(trans_message.length % 2 != 0)
        {
            int i = trans_message.length - 1;
            ciphertext_int[i] = ( trans_message[i] + trans_key[i % key_length] ) % 26;
        }
    }

    protected void decryptTheString(int key_length, int[] trans_key, int[] trans_message, int[] ciphertext_int) {
        for(int i=0;i<trans_message.length / 2;i+=2)
        {
            ciphertext_int[i] = CryptToolbox.mod_( ( trans_message[i] - trans_key[i % key_length] ) , 26 );
            ciphertext_int[i+1] = CryptToolbox.mod_( ( trans_message[i+1] - trans_key[(i+1) % key_length] ) , 26 );
        }

        if(trans_message.length % 2 != 0)
        {
            int i = trans_message.length - 1;
            ciphertext_int[i] = CryptToolbox.mod_( ( trans_message[i] - trans_key[i % key_length] ) , 26 );
        }
    }
}
