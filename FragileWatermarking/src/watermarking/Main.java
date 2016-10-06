/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TOSHIBA PC
 */
public class Main {
    public static void main(String[] args) {
        // Get input
        Scanner input = new Scanner(System.in);
        System.out.println("CHOOSE AN ACTION");
        System.out.println("1. Watermark An Image");
        System.out.println("2. Extract Watermark from an Image");
        System.out.print("your input: ");
        String choice = input.nextLine(); System.out.println();
        if (choice.equals("1")) {
            System.out.print("Enter filename for watermark (BMP/PNG): ");
            String watermarkFile = input.nextLine();
            System.out.print("Enter filename for image (BMP/PNG): ");
            String imageFile = input.nextLine();
            System.out.print("Masukkan kunci: ");
            String key = input.nextLine();

            // Preprocess watermark file
            WatermarkFile watermark = new WatermarkFile(watermarkFile, imageFile);
            ImageFile image = new ImageFile(imageFile);

            String watermarkBits = watermark.getBits();
            String imageBits = image.getBits();

            System.out.println("Bits Image " + imageBits.length());
            System.out.println("Bits Watermark " + watermarkBits.length());

            System.out.println("Construct FragileWatermark");
            FragileWatermark fragile = new FragileWatermark(watermarkBits, imageBits, key);
            System.out.println("Put Watermark");
            fragile.putWatermark();

            System.out.println("Bits Stegano: " + fragile.getStegano().length());            
            System.out.println("Save File");

            try {
                image.saveSteganoImage(fragile.getStegano());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter filename of a watermarked image (BMP/PNG): ");
            String imageFile = input.nextLine();
            System.out.print("Type the key: ");
            String key = input.nextLine();
            
            ImageFile watermarkedImage = new ImageFile(imageFile);
            String imageBits = watermarkedImage.getBits();
            
            System.out.println("Bits Watermarked Image " + imageBits.length());

            System.out.println("Extracting a Watermark Image...");
            FragileWatermark fragile = new FragileWatermark(imageBits, key);
            fragile.extractWatermark();

            System.out.println("Bits watermark image: " + fragile.getWatermark().length());            
            System.out.println("Save File");

            try {
                watermarkedImage.saveWatermarkImage(fragile.getWatermark());
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
    }
}
