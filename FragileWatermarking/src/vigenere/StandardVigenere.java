package vigenere;

/**
 *
 * @class StandardVigenere.java
 */
public class StandardVigenere {
    private static final int MAX_KEY_LENGTH = 25;
    private String key, text;
    
    /**
    *
    * constructor
    */
    public StandardVigenere(String text, String key) {
        this.text = new String(text);
        this.key = new String (key.toUpperCase());
    }
    
    /**
    *
    * encrypt text based on input key
    */
    public String encrypt() {
        String result = new String();
        String plaintext = new String(text.toUpperCase());
        int j = 0;
        for (int i=0; i<plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if ((c >= 'A' && c <= 'Z')) { //encrypt the alphabet only
                result += (char) (((int)c + (int)key.charAt(j) - 2 * 'A') % 26 + 'A');
                j = (j+1) % key.length();
            } else if (c==' ') { //to sustain the original format of the text
                result += c;
            }
        }
        return result;
    }
    
    /**
    *
    * decrypt text based on input key
    */
    public String decrypt() {
        String result = new String();
        String ciphertext = new String(text.toUpperCase());
        int j = 0;
        
        for (int i=0; i<ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (c >= 'A' && c <= 'Z') { //encrypt the alphabet only
                result += (char) (((int)c - (int)key.charAt(j) + 26) % 26 + 'A');
                j = (j+1) % key.length();
            } else if (c==' ') {
                result += c;
            }
        }
        return result.toLowerCase();
    }
}
