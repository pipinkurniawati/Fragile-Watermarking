/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 *
 * @author TOSHIBA PC
 */
public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        int [] w = {0,1,1,1,1,0,1,0,0,1,1,1,0,1,0,1,0,1,1,0,1,1,0,0,0,1,1,1,0,1,1,0,0,1,1,0,0,0,0,1};
        int [] f = {1};
        int [] result;

        String key = "stegano";
        FragileWatermark test = new FragileWatermark(w, f, key);
        result = test.encryptWatermark();
        for(int i=0; i< result.length; i++) {
            System.out.print(result[i]);
        }
        System.out.println();
        
        // Preprocess watermark file
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter filename for watermark (BMP/PNG): ");
//        String filename = input.nextLine();
//        WatermarkFile watermark = new WatermarkFile("waee.png");
//        
//        watermark.getBits();
        
        
    }
}
