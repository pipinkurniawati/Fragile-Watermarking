/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.nio.charset.StandardCharsets;
import vigenere.ExtendedVigenere;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author zulvafachrina
 */
public class FragileWatermark {
    
    private int[] watermarkBits;
    private int[] originalBits;
    private int[] steganoBits;
    private Integer[] locations;
    private String key;
    
    public FragileWatermark(int[] watermark, int [] original, String key) {
        this.watermarkBits = watermark;
        this.originalBits = original;
        this.steganoBits = original;
        this.key = key;
        
        locations = new Integer[watermarkBits.length];
        generatePseudoRandom();
    }
    
    public int[] encryptWatermark(){
        String str = binaryToString(watermarkBits);     
        ExtendedVigenere vigenere = new ExtendedVigenere(str, key);
        return stringToBinary(vigenere.encrypt());
    }
    
    public void generatePseudoRandom(){
        int seed = 0;
        for(int i=0; i < key.length(); i++) {
            seed += (int)key.charAt(i) - 'A';
        }
        
        Random pseudoRandom = new Random(seed);
        for(int i=0; i<watermarkBits.length; i++){
           int random = pseudoRandom.nextInt(watermarkBits.length);
           while (Arrays.asList(locations).contains(random)) {
               random = pseudoRandom.nextInt(watermarkBits.length);
           }
           locations[i] = random;
        }          
    } 
      
    public String binaryToString(int[] bits) {
        
        String stringBits = "";
        for(int i=0; i< bits.length; i++) {
            stringBits += String.valueOf(bits[i]);
        }
             
        StringBuilder str = new StringBuilder(stringBits.length()/8);
               
        for(int i=0; i<stringBits.length(); i++){
            int charCode = Integer.parseInt(stringBits.substring(i,i+8),2);
            str.append((char)charCode);
            i+=7;
        }
        
        return str.toString();
    }
    
    public int[] stringToBinary(String str) {
        int[] bytes = new int[str.length()];
        
        for(int i=0; i< str.length(); i++) {
            bytes[i] = (int)str.charAt(i);
        }
        
        int[] binary = new int[str.length() * 8];
        StringBuilder builder = new StringBuilder(str.length()*8);
        
        
        for(int i=0; i< bytes.length; i++) {
            String bin = Integer.toBinaryString(bytes[i]);
            String format = String.format("%8s", bin).replace(' ', '0');
            builder.append(format);
        }
        
        for(int i=0; i < builder.length(); i++) {
            binary[i] = Integer.parseInt(builder.substring(i,i+1));
        }
        
        return binary;
    }
}
