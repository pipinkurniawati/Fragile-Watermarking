/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.util.Scanner;

/**
 *
 * @author TOSHIBA PC
 */
public class Main {
    public static void main(String[] args) {
        // Get input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter filename for watermark (BMP/PNG): ");
        String watermarkFile = input.nextLine();
        System.out.print("Enter filename for image (BMP/PNG): ");
        String imageFile = input.nextLine();
        
        // Preprocess watermark file
        WatermarkFile watermark = new WatermarkFile(watermarkFile, imageFile);
    }
}
