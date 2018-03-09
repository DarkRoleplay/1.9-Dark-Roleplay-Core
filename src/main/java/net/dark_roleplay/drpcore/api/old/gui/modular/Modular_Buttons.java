package net.dark_roleplay.drpcore.api.old.gui.modular;

import net.minecraft.util.ResourceLocation;

public class Modular_Buttons {
	
	private ResourceLocation pathDisabled;
	private ResourceLocation pathEnabled;
	private ResourceLocation pathHovered;
	
	private int left, right, top, bottom, width, height;
	
	public Modular_Buttons(ResourceLocation pathDisabled, ResourceLocation pathEnabled, ResourceLocation pathHovered, int left, int right, int top, int bottom, int width, int height){
		this.pathDisabled = pathDisabled;
		this.pathEnabled = pathEnabled;
		this.pathHovered = pathHovered;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}
	
	public ResourceLocation getPathDisabled() {
		return pathDisabled;
	}
	
	public ResourceLocation getPathEnabled() {
		return pathEnabled;
	}
	
	public ResourceLocation getPathHovered() {
		return pathHovered;
	}
	
	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public int getBottom() {
		return bottom;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
