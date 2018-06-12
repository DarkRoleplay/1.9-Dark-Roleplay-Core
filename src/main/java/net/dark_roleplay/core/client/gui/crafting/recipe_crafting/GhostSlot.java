package net.dark_roleplay.core.client.gui.crafting.recipe_crafting;

import net.minecraft.item.ItemStack;

public class GhostSlot {

	private int posX, posY;
	
	private int width, height;
	
	private ItemStack stack;
	
	public GhostSlot(ItemStack stack, int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.stack = stack;
	}

	public boolean isMouseOver(int mouseX, int mouseY){
		return mouseX > posX && mouseX < posX + width & mouseY > posY && mouseY < posY + height;
	}
	
	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
}
