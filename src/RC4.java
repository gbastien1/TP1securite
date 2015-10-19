package TP1Securite;


//Found at http://stackoverflow.com/questions/12289717/rc4-encryption-java

public class RC4 {

    private int[] S = new int[256];
    private int[] T = new int[256];
    private int keylen;
    private int[] _key;

    public RC4(int[] key) {
        keylen = key.length;
        for (int i = 0; i < 256; i++) {
            S[i] = (int) i;
            T[i] = key[i % keylen];
        }

        int j = 0;
        int tmp;
        
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
        }
    }

    public String encrypt(String message) {
    	byte[] plaintext = message.getBytes();
        
        byte[] ciphertext = new byte[plaintext.length];
        int i = 0, j = 0, k, t;
        int tmp;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            //swap s[i] & s[j]
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            //
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return new String(ciphertext);
    }

    public String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
}