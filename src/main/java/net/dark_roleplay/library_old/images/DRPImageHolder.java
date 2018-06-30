package net.dark_roleplay.library_old.images;

import java.awt.image.BufferedImage;

import net.minecraft.nbt.NBTTagCompound;

public class DRPImageHolder {

	private BufferedImage img;
	private boolean dirty = false;
	private ImageType imageType = ImageType.TWO_BIT;
	private NBTTagCompound tag = null;
	private int width = 0, height = 0;
	private int[] colors = null;
	private byte[] data = null;

	private static int emptyColor = 0x00FFFFFF;

	public DRPImageHolder(int width, int height, ImageType type) {
		this.width = width;
		this.height = height;
		this.imageType = type;
		this.tag = new NBTTagCompound();
		this.tag.setInteger("width", width);
		this.tag.setInteger("height", width);
		this.tag.setInteger("pallete_size", type.getSize());
		this.colors = new int[type.getSize()];
		for(int i = 0; i < colors.length; i++){
			this.colors[i] = emptyColor;
		}
		this.data = new byte[width * height];
	}
	
	public void setPixel(int posX, int posY, int color){
		byte colorID = getColorID(color);
		if(colorID < 0){
			return;
		}else{
			data[posX + (posY * this.width)] = colorID;
			this.markDirty();
		}
	}
	
	public NBTTagCompound convertToNBT(){
		if(this.dirty){
			byte[] red = new byte[this.colors.length];
			byte[] green = new byte[this.colors.length];
			byte[] blue = new byte[this.colors.length];
			byte[] alpha = new byte[this.colors.length];
			
			for(int i = 0; i < this.colors.length; i++){
				alpha[i] = (byte) (colors[i] >> 24);
				red[i] = (byte) (colors[i] >> 16);
				green[i] = (byte) (colors[i] >> 8);
				blue[i] = (byte) (colors[i]);
			}
			
			tag.setByteArray("red", red);
			tag.setByteArray("green", green);
			tag.setByteArray("blue", blue);
			tag.setByteArray("alpha", alpha);
			tag.setByteArray("data", this.data);
			
			this.dirty = false;
		}
		
		return this.tag;
	}
	
	private byte getColorID(int color){
		for(byte i = 0; i < colors.length; i++){
			if(colors[i] == color){
				return i;
			}
			if(colors[i] == emptyColor){
				colors[i] = color;
				this.markDirty();
				return i;
			}
		}
		return -1;
	}
	
	private void markDirty(){
		this.dirty = true;
	}
	
	public static enum ImageType{
		ONE_BIT(2),
		TWO_BIT(4),
		THREE_BIT(8),
		FOUR_BIT(16),
		FIVE_BIT(32),
		SIX_BIT(64),
		SEVEN_BIT(128),
		EIGHT_BIT(256);
		
		private int size = 0;
		
		private ImageType(int size){
			this.size = size;
		}
		
		public int getSize(){
			return this.size;
		}
	}
}
