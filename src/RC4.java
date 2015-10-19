package TP1Securite;

//http://stackoverflow.com/questions/12289717/rc4-encryption-java

public class RC4 {

    private byte[] S = new byte[256];
    private byte[] T = new byte[256];
    private int keylen;
    private byte[] key;

    public RC4() {

        keylen = key.length;
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i] = key[i % keylen];
        }

        int j = 0;
        byte[] tmp;
        
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
        byte[] tmp;
        for (int counter = 0; counter < plaintext.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        return ciphertext;
    }

    public String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
}