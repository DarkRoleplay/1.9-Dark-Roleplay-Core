package net.dark_roleplay.drpcore.client;

import net.minecraft.util.ResourceLocation;

public class ModularGui_Template {

	private String name;
	
	private ResourceLocation background;
	private ResourceLocation backgroundHollow;

	private int left, right, top, bottom;
	
	public ModularGui_Template(String name, ResourceLocation background, ResourceLocation backgroundHollow, int left, int right, int top, int bottom){
		this.name = name;
		this.background = background;
		this.backgroundHollow = backgroundHollow;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public String getName() {
		return name;
	}

	public ResourceLocation getBackground() {
		return background;
	}

	public ResourceLocation getBackgroundHollow() {
		return backgroundHollow;
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

	
}
