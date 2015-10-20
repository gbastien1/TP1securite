package TP1Securite;


//Found at http://stackoverflow.com/questions/12289717/rc4-encryption-java

public class RC4 {

    private int[] S = new int[256];
    private int[] T = new int[256];
    private int keylen;
    private int[] _key;

    public RC4(int[] key) {
        keylen = key.length;
        //initialisation des vecteurs S et T
        for (int i = 0; i < 256; i++) {
            S[i] = (int) i;
            T[i] = key[i % keylen];
        }

        int j = 0;
        int tmp;
        
        //permutations dans S
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
        }
    }

    /**
     * chiffrement du message avec RC4
     * @param  message message à chiffrer
     * @return         message chiffré
     */
    public String encrypt(String message) {
    	byte[] plaintext = message.getBytes();
        
        byte[] ciphertext = new byte[plaintext.length];
        int i = 0, j = 0, k, t;
        int tmp;
        //chiffrement de flux = chiffrer donnée par donnée, donc caractère par caractère
        for (int counter = 0; counter < plaintext.length; counter++) {
            // & 0xFF => modulo 256
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            //swap s[i] & s[j]
            tmp = S[j];
            S[j] = S[i];
            S[i] = tmp;
            //t permet d'aller chercher une valeur dans la clé
            t = (S[i] + S[j]) & 0xFF;
            k = S[t];
            //xor entre un byte du message et la clé de 8 bits
            ciphertext[counter] = (byte) (plaintext[counter] ^ k);
        }
        //transforme byte[] en String
        return new String(ciphertext);
    }

    /**
     * idem à chiffrement pour déchiffrer
     * @param  ciphertext message à déchiffrer
     * @return            message déchiffré
     */
    public String decrypt(String ciphertext) {
        return encrypt(ciphertext);
    }
}