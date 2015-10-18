package TP1Securite;

//http://stackoverflow.com/questions/12289717/rc4-encryption-java

public class RC4 {

    private String[] S = new String[256];
    private String[] T = new String[256];
    private int keylen;
    private String[] key;

    public RC4() {

        keylen = key.length;
        for (int i = 0; i < 256; i++) {
            S[i] = (String) i;
            T[i] = key[i % keylen];
        }

        int j = 0;
        String tmp;
        
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
        }
    }

    public String encrypt(String message) {

    	String plaintext = bitsManager.stringToBits(message); 
        
        String ciphertext = new String[plaintext.length];
        int i = 0, j = 0, k, t;
        String tmp;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (String) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    public String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
}