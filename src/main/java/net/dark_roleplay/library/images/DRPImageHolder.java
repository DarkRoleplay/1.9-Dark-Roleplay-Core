package net.dark_roleplay.library.images;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;

public class DRPImageHolder {

	private BufferedImage img;
	private ImageType imageType;
	
	public DRPImageHolder(BufferedImage img) {
		if(img.getType() == BufferedImage.TYPE_BYTE_INDEXED) {
			byte[] imageBytes = ((DataBufferByte) img.getData().getDataBuffer()).getData();

		    IndexColorModel icm = (IndexColorModel) img.getColorModel();
		    int[] data = new int[0];
		    icm.getRGBs(data);
		}

	}
	
	
	public static enum ImageType{
		ONE_BYTE,
		TWO_BYTE,
		THREE_BYTE,
		FOUR_BYTE,
		FIVE_BYTE,
		SIX_BYTE,
		SEVEN_BYTE,
		EIGHT_BYTE;
	}
}
