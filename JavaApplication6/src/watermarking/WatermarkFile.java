/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author TOSHIBA PC
 */
public class WatermarkFile {
    
    protected String filename = "watermark_new.png";
    protected BufferedImage watermark = null;
    
    public WatermarkFile(String watermarkFile, String imageFile) {
        try {
            BufferedImage original = ImageIO.read(new File(watermarkFile));
            BufferedImage image = ImageIO.read(new File(imageFile));
            
            // Convert to binary image
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            BufferedImage binaryImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            op.filter(original, binaryImage);
            
            // Resize
            BufferedImage croppedImage = binaryImage.getSubimage(0, 0, original.getWidth(), original.getHeight());
            watermark = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            for (int i=0; i<watermark.getWidth(); i++) {
                for (int j=0; j<watermark.getHeight(); j++) {
                    int rgb = croppedImage.getRGB(i % croppedImage.getWidth(), j % croppedImage.getHeight());
                    watermark.setRGB(i, j, rgb);
                }
            }
            
            // Write watermark image file
            ImageIO.write(watermark, "png",new File("watermark_new.png") );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getWatermarkFile() {
        return filename;
    }
    
    public String getBits() {
        int temp = (8 - ((watermark.getWidth() * watermark.getHeight()) % 8)) % 8;
        int size = watermark.getWidth() * watermark.getHeight() + temp;
        //int[] bits = new int[size];
        StringBuilder builder = new StringBuilder(size);
        int k = 0;
        for (int i=0; i<watermark.getWidth(); i++) {
            for (int j=0; j<watermark.getHeight(); j++) {
                int lastBit = watermark.getRGB(j, j) & 1;
                //bits[k] = lastBit;
                builder.append(lastBit);
                k++;
            }
        }
        int lastInserted = k-1;
        
        // Insert bit 0 if (bit % 8 != 0)
        for (int i=0; i<temp; i++) {
            size--;
            builder.append(builder.charAt(lastInserted));
            builder.insert(lastInserted, 0);
            //bits[size] = bits[lastInserted];
            //bits[lastInserted] = 0;
            lastInserted--;
        }
        
        return builder.toString();
    }

    /* Get watermark image, given its array of integer (bits) and image */
    public static BufferedImage getWatermarkImage (int[] bits, BufferedImage image) {
        BufferedImage watermarkImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        int dummySize = bits.length - (image.getWidth() * image.getHeight());
        int k = 0;
        for (int i=0; i<watermarkImage.getWidth(); i++) {
            for (int j=0; j<watermarkImage.getHeight(); j++) {
                watermarkImage.setRGB(i, j, bits[k]);
                if (k == bits.length - 8)
                    k += dummySize;
                else
                    k++;
            }
        }
        return watermarkImage;
    }
}