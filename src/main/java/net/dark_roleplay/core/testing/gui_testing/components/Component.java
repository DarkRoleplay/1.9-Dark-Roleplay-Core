package net.dark_roleplay.core.testing.gui_testing.components;

import java.io.IOException;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public abstract class Component extends Gui{

	protected int posX = 0;
	protected int posY = 0;
	
	protected int width = 0;
	protected int height = 0;
	
	protected int minWidth = 0;
	protected int minHeight = 0;
	
	protected int maxWidth = -1;
	protected int maxHeight = -1;
	
	public Component() {}
	
	public Component(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		this.width = minWidth;
		this.height = minHeight;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}
	
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setSize(int width, int height) {
		if(width < this.minWidth)
			this.width = this.minWidth;
		else if(this.maxWidth > -1 && width > this.maxWidth)
			this.width = this.maxWidth;
		else
			this.width = width;
		
		if(height < this.minHeight)
			this.height = this.minHeight;
		else if(this.maxHeight > -1 && height > this.maxHeight)
			this.height = this.maxHeight;
		else
			this.height = height;
	}
	
	public void setWidth(int width) {
		if(width < this.minWidth)
			this.width = this.minWidth;
		else if(this.maxWidth > -1 && width > this.maxWidth)
			this.width = this.maxWidth;
		else
			this.width = width;
	}
	
	public void setHeight(int height) {
		if(height < this.minHeight)
			this.height = this.minHeight;
		else if(this.maxHeight > -1 && height > this.maxHeight)
			this.height = this.maxHeight;
		else
			this.height = height;
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + this.height;
	}
	
	public abstract ResourceLocation getTexture();
	
	public abstract void render(int mouseX, int mouseY, float partialTicks);
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException { return false; }
	
	public void keyTyped(char typedChar, int keyCode) throws IOException {}
	
	public void onFocusGain() {}
	
	public void onFocusLost() {}
	
}
