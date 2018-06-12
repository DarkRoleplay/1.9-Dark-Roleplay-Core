package net.dark_roleplay.library_old.images;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Work in Progress don't use!
 * @author JTK222
 *
 */
public class ImageNBT {

	//TOIMPLEMENT ImageNBTClass
	public static BufferedImage getAsIndexed(NBTTagCompound tag){
		byte size = tag.getByte("pallete_size");
		int sizeCopy = size;
		int requiredBits = 0;
		while (sizeCopy > 0) {
			requiredBits++;
			sizeCopy = sizeCopy >> 1;
		}
		

		int width = tag.getInteger("width");
		int height = tag.getInteger("height");
		
		byte[] red = tag.getByteArray("red");
		byte[] green = tag.getByteArray("green");
		byte[] blue = tag.getByteArray("blue");
		byte[] alpha = tag.getByteArray("alpha");
		
		byte[] data = tag.getByteArray("data");
		
		IndexColorModel icm = new IndexColorModel(requiredBits, size, red, green, blue, alpha);
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, icm);
		
        DataBuffer dbuf = new DataBufferByte(data, width*height, 0);

        SampleModel sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_BYTE, width, height, new int[]{(byte) 0xFF});
        
        WritableRaster raster = Raster.createWritableRaster(sampleModel, dbuf, null);
        return new BufferedImage(icm, raster, false, null);//new java.util.Hashtable());
	}
	
	public static NBTTagCompound writeToTag(BufferedImage img, NBTTagCompound tag){
		if(tag == null)
			tag = new NBTTagCompound();
		
		if(img.getType() != BufferedImage.TYPE_BYTE_INDEXED)
			return tag;
		
		tag.setInteger("width", img.getWidth());
		tag.setInteger("height", img.getHeight());
		
		IndexColorModel icm = (IndexColorModel) img.getColorModel();
		int pallete_size = icm.getMapSize();
		tag.setInteger("pallete_size", pallete_size);
		
		byte[] red = new byte[pallete_size];
		byte[] green = new byte[pallete_size];
		byte[] blue = new byte[pallete_size];
		byte[] alpha = new byte[pallete_size];
		icm.getReds(red);
		icm.getGreens(green);
		icm.getBlues(blue);
		icm.getAlphas(alpha);
		
		tag.setByteArray("red", red);
		tag.setByteArray("green", green);
		tag.setByteArray("blue", blue);
		tag.setByteArray("alpha", alpha);
		
		DataBufferByte buf = (DataBufferByte) img.getRaster().getDataBuffer();
		tag.setByteArray("data", buf.getData());
		
		return tag;
	}
}
