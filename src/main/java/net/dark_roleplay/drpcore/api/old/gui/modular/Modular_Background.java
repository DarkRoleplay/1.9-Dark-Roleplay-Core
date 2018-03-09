package net.dark_roleplay.drpcore.api.old.gui.modular;

import net.minecraft.util.ResourceLocation;

public class Modular_Background {

	private ResourceLocation path;
	private ResourceLocation pathHollow;

	private int left, right, top, bottom, width, height;
	
	public Modular_Background(ResourceLocation path, ResourceLocation pathHollow, int left, int right, int top, int bottom, int width, int height){
		this.path = path;
		this.pathHollow = pathHollow;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}
	
	public ResourceLocation getPath() {
		return this.path;
	}

	public ResourceLocation getPathHollow() {
		return this.pathHollow;
	}

	public int getLeft() {
		return this.left;
	}

	public int getRight() {
		return this.right;
	}

	public int getTop() {
		return this.top;
	}

	public int getBottom() {
		return this.bottom;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
