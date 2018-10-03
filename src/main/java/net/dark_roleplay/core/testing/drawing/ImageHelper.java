package net.dark_roleplay.core.testing.drawing;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import net.dark_roleplay.library.experimental.data_compression.IntArrayCompression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.ResourceLocation;

public class ImageHelper {

	BufferedImage image;

	int[] palette;
	int[] data;
	int width;
	int height;

	private ResourceLocation loc;

	public ResourceLocation getResource() {
		if (this.loc == null) {
			this.image = this.createIndexedImage(this.data, this.palette, this.width, this.height);
			this.loc = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("simple_image",
					new DynamicTexture(this.image));
		}
		return this.loc;
	}

	public void clear() {
		if (this.loc != null)
			Minecraft.getMinecraft().getTextureManager().deleteTexture(this.loc);
		this.image = null;
	}

	public ImageHelper(int[] palette, int[] data, int width, int height) {
		this.palette = palette;
		this.data = data;
		this.width = width;
		this.height = height;
	}

	public static ImageHelper read(NBTTagCompound compound) {
		int width = compound.getInteger("width");
		int height = compound.getInteger("height");

		NBTTagIntArray paletteArray = (NBTTagIntArray) compound.getTag("palette");

		NBTTagIntArray dataArray = (NBTTagIntArray) compound.getTag("data");
		int compression = compound.getInteger("compression");

		int[] data = IntArrayCompression.decompress(compression, dataArray.getIntArray(), width * height);

		return new ImageHelper(paletteArray.getIntArray(), data, width, height);
	}

	public NBTTagCompound write() {
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInteger("width", this.width);
		comp.setInteger("height", this.height);
		comp.setIntArray("palette", this.palette);
		comp.setInteger("compression", this.log2(this.palette.length));
		comp.setIntArray("data", IntArrayCompression.compress(this.log2(this.palette.length), this.data));

		return comp;
	}

	int log2(int value) {
		return Integer.SIZE - Integer.numberOfLeadingZeros(value);
	}

	public BufferedImage createIndexedImage(int[] imageData, int[] palette, int w, int h) {
		BufferedImage image = null;
		byte[] red = new byte[palette.length];
		byte[] green = new byte[palette.length];
		byte[] blue = new byte[palette.length];
		byte[] alpha = new byte[palette.length];

		for(int i = 0; i < palette.length; i++) {
			blue[i] = (byte) (palette[i] & 0x000000FF);
			green[i] = (byte) ((palette[i] >> 8) & 0x000000FF);
			red[i] = (byte) ((palette[i] >> 16) & 0x000000FF);
			alpha[i] = (byte) ((palette[i] >>> 24) & 0x000000FF);
		}

		IndexColorModel colorModel = new IndexColorModel(this.log2(palette.length), palette.length, red, green, blue, alpha);

		image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED, colorModel);

		BufferedImage unindexed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		WritableRaster raster = image.getRaster();

		raster.setPixels(0, 0, w, h, imageData);

//		for(int y = 0; y < h; y++) {
//			for(int x = 0; x < w; x++) {
//				raster.setPixel(x, y, imageData);
//				unindexed.setRGB(x, y, imageData[x + (w * y)]);
//			}
//		}

		return image;
	}
}
