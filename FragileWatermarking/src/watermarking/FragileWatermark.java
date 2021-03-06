/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.util.ArrayList;
import vigenere.ExtendedVigenere;
import java.util.Arrays;
import java.util.List;
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
    
    public FragileWatermark(String watermarkedImage, String key) {
        this.originalBits = watermarkedImage;
        this.key = key;
    }
    
    public FragileWatermark(String watermark, String original, String key) {
        this.watermarkBits = watermark;
        this.originalBits = original;
        this.key = key;
        
        locations = new Integer[watermarkBits.length()];
        generatePseudoRandom(watermarkBits);
    }
    
    public void putWatermark(){
        String str = bitsToAscii(watermarkBits);     
        ExtendedVigenere vigenere = new ExtendedVigenere(str, key);
        changeLSB(asciiToBits(vigenere.encrypt()));
    }
    
    public void extractWatermark() {        
        extractSteganoLSB(originalBits);
        
        char[] extractedBits = new char[steganoBits.length()];  
        locations = new Integer[steganoBits.length()];
        generatePseudoRandom(steganoBits);
        
        for(int i=0; i<steganoBits.length(); i++) {
            extractedBits[locations[i]] = steganoBits.charAt(i);
        }
        
        ExtendedVigenere vigenere = new ExtendedVigenere(bitsToAscii(String.valueOf(extractedBits)), key);
        this.watermarkBits = asciiToBits(vigenere.decrypt());
    }
    
    public void generatePseudoRandom(String bits){
        //Create seed
        int seed = 0;
        for(int i=0; i < key.length(); i++) {
            seed += (int)key.charAt(i) - 'A';
        }
        
        //Generate sequence of random position
        Random pseudoRandom = new Random(seed);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<bits.length(); i++){
            list.add(i);
        }
        for(int i=0; i<bits.length(); i++){
           int random = pseudoRandom.nextInt(bits.length());
           int idx = random % list.size();
           locations[i] = list.get(idx);
           list.remove(idx);
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
        
        char[] stegano = originalBits.toCharArray();
        
        for(int i=0; i < watermark.length(); i++) {
            int index = i * 32 + 31;
            stegano[index] = watermark.charAt(locations[i]);
        }
        
        this.steganoBits = String.valueOf(stegano);
        
    }
    
    public void extractSteganoLSB(String watermarkedImage) {
        List<Character> watermarkBits = new ArrayList<Character>();
        for(int i=0; i<watermarkedImage.length(); i++) {
            if (i % 32 == 31) {
                watermarkBits.add(watermarkedImage.charAt(i));
            }
        }
        char[] temp = new char[watermarkBits.size()];
        for(int i=0; i<watermarkBits.size(); i++) temp[i] = watermarkBits.get(i);
        this.steganoBits = String.valueOf(temp);
    }
    
    public String getStegano(){
        return steganoBits;
    }
    
    public String getWatermark() {
        return watermarkBits;
    }
}
