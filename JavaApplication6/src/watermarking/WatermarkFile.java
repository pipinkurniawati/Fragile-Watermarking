/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package watermarking;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author TOSHIBA PC
 */
public class WatermarkFile {
    
    protected String filename = "watermark_new.png";
    protected BufferedImage watermark = null;
    
    public WatermarkFile(String filename) {
        try {
            BufferedImage original = ImageIO.read(new File(filename));
            
            // Convert to binary image
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            watermark = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            op.filter(original, watermark);
            
            // Resize
            
            // Write watermark image file
            ImageIO.write(watermark, "png",new File("watermark_new.png") );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getWatermarkFile() {
        return filename;
    }
    
    public int[] getBits() {
        
        int[] bits = new int[watermark.getWidth() * watermark.getHeight()];
        Byte b = 0x0;
        int k = 0;
        for (int i=0; i<watermark.getWidth(); i++) {
            for (int j=0; j<watermark.getHeight(); j++) {
                int lastBit = watermark.getRGB(j, j) & 1;
                bits[k] = lastBit;
                k++;
            }
        }
        
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            FileInputStream fis = new FileInputStream(filename);
//            
//            byte[] buf = new byte[1024];
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                bos.write(buf, 0, readNum);
//                System.out.println("read " + readNum + " bytes,");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] bytes = bos.toByteArray();
        
        return bits;
    }
}