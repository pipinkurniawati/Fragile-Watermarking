/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import vigenere.ExtendedVigenere;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author zulvafachrina
 */
public class FragileWatermark {
    
    private String watermarkBits;
    private String originalBits;
    private String steganoBits;
    private Integer[] locations;
    private String key;
    
    public FragileWatermark(String watermark, String original, String key) {
        this.watermarkBits = watermark;
        this.originalBits = original;
        //this.steganoBits = original;
        this.key = key;
        
        locations = new Integer[watermarkBits.length()];
        //generatePseudoRandom();

        
    }
    
    public void putWatermark(){
        String str = bitsToAscii(watermarkBits);     
        ExtendedVigenere vigenere = new ExtendedVigenere(str, key);
        changeLSB(asciiToBits(vigenere.encrypt()));
    }
    
    public void generatePseudoRandom(){
        //Create seed
        int seed = 0;
        for(int i=0; i < key.length(); i++) {
            seed += (int)key.charAt(i) - 'A';
        }
        
        //Generate sequence of random position
        Random pseudoRandom = new Random(seed);
        for(int i=0; i<watermarkBits.length(); i++){

           int random = pseudoRandom.nextInt(watermarkBits.length());
           while (Arrays.asList(locations).contains(random)) {
               random = pseudoRandom.nextInt(watermarkBits.length());
           }
           locations[i] = random;
        }          
    } 
      
    public String bitsToAscii(String bits) {
        //Generate ascii string based on binary sequence
        StringBuilder str = new StringBuilder(bits.length()/8);      
        for(int i=0; i<bits.length(); i++){
            int charCode = Integer.parseInt(bits.substring(i,i+8),2);
            str.append((char)charCode);
            i+=7;
        }
        
        return str.toString();
    }
    
    public String asciiToBits(String str) {
        
        //Generate bytes of char in string 
        int[] bytes = new int[str.length()];       
        for(int i=0; i< str.length(); i++) {
            bytes[i] = (int)str.charAt(i);
        }
        
        //Create string of binary sequence from bytes
        StringBuilder builder = new StringBuilder(str.length()*8);             
        for(int i=0; i< bytes.length; i++) {
            String bin = Integer.toBinaryString(bytes[i]);
            String format = String.format("%8s", bin).replace(' ', '0');
            builder.append(format);
        }
       
        return builder.toString();
    }
    
    public void changeLSB(String watermark){   
        StringBuilder builder = new StringBuilder(originalBits); 

        for(int i=0; i < watermark.length()-16; i++) {
            int index = i * 32 + 31;
            builder.replace(index, index+1, watermark.substring(i, i+1));
        }
        this.steganoBits = builder.toString();
        
    }
    
    public String getStegano(){
        return steganoBits;
    }
    
    
}
