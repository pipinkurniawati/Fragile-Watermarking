/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zulvafachrina
 */
public class ImageFile {
    private String filename;
    private BufferedImage original;
    
    public ImageFile(String filename) {
        try {
            original = ImageIO.read(new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(ImageFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String getBits() {
        int pixel = original.getWidth() * original.getHeight();
        StringBuilder builder = new StringBuilder(pixel * 32); 
        
        for (int i=0; i<original.getWidth(); i++) {
            for (int j=0; j<original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                String bin = Integer.toBinaryString(rgb);
                String format = String.format("%32s", bin).replace(' ', '0');
                builder.append(format);
            }
        }
        
        return builder.toString();
        
    }
    
    public void saveSteganoImage(String bits) throws IOException{

        //Generate ascii string based on binary sequence       
        int[] bytes = new int[bits.length()/32];
        
        String temp;
        
        int k=0;
        for(int i=0; i<bits.length()-32; i+=32){
            long parseLong = Long.parseLong(bits.substring(i,i+32),2);
            int charCode = (int)parseLong;
            bytes[k] = charCode;
            k++;
        }
        
        BufferedImage watermarkImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        k = 0;
        
        for (int i=0; i<watermarkImage.getWidth(); i++) {
            for (int j=0; j<watermarkImage.getHeight(); j++) {
                watermarkImage.setRGB(i, j, bytes[k]);
                k++;
            }
        }   
        
        // Write watermark image file
        ImageIO.write(watermarkImage, "png",new File("stegano_new.png") );
    }
    
    public void saveWatermarkImage(String bits) throws IOException{
        char[] imageBits = bits.toCharArray();
        BufferedImage watermarkImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int dummySize = bits.length() - (original.getWidth() * original.getHeight());
        int k = 0;
        for (int i=0; i<watermarkImage.getWidth(); i++) {
            for (int j=0; j<watermarkImage.getHeight(); j++) {
                if (imageBits[k] == '1') //putih
                    watermarkImage.setRGB(i, j, -1);
                else //hitam
                    watermarkImage.setRGB(i, j, -16777216);
                if (k == bits.length() - 8)
                    k += dummySize;
                else
                    k++;
            }
        }   
        // Write watermark image file
        ImageIO.write(watermarkImage, "png",new File("extracted_watermark.png") );
    }
}
