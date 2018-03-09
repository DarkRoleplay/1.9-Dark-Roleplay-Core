package net.dark_roleplay.library.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageUtil {

	/**
	 * Converts a RGB Image to a Greyscale Image
	 * @param input The RGB Image
	 * @return The greyscaled source image
	 */
	public static BufferedImage toGreyscale(BufferedImage source) {
		WritableRaster raster = source.getRaster();
		
        int[] pixel = new int[4];
		int width = source.getWidth(null);
        int height = source.getHeight(null);
        
		for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
            	pixel = raster.getPixel(x, y, pixel);
            	
            	float luminanceA = 0.2126F * (pixel[0] / 256F) + 0.7152F * (pixel[1] / 256F) + 0.0722F * (pixel[2] / 256F);
            	float luminanceB = (float) (Math.pow(116 * luminanceA, (1/3F)) - 16);
            	
            	pixel[0] = (int) (luminanceB * 256F);
            	pixel[1] = (int) (luminanceB * 256F);
            	pixel[2] = (int) (luminanceB * 256F);
            	
            	raster.setPixel(x, y, pixel);
            }
		}
		source.setData(raster);
		return source;
	}
	
	/**
	 * Copies a Buffered Image
	 * @param source The Image from which to create a copy
	 * @return A Copy of the Source Image
	 */
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics2D g = b.createGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
}
