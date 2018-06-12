package net.dark_roleplay.core.client.gui.crafting.recipe_selection;

import org.lwjgl.util.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_CategorySelect extends GuiButton {
	
	public Button_CategorySelect(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 16;
		this.height = 16;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		if(this.hovered) {
			this.drawGradientRect(this.x, this.y, this.x + this.width,this.y + this.height, 0x80FFFFFF, 0x80FFFFFF);
		}
	}
}