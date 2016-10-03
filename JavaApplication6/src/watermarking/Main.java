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
        // Preprocess watermark file
        Scanner input = new Scanner(System.in);
        System.out.print("Enter filename for watermark (BMP/PNG): ");
        String filename = input.nextLine();
        WatermarkFile watermark = new WatermarkFile("waee.png");
        
        watermark.getBits();
    }
}
