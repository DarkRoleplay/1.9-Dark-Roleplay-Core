package net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection;

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
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		if(this.hovered) {
			this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,this.yPosition + this.height, 0x80FFFFFF, 0x80FFFFFF);
		}
	}
}