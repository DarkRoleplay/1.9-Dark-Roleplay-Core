package net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_RecipeSelection  extends GuiButton {
	
	public Button_RecipeSelection(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 24;
		this.height = 24;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if(this.enabled){
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			if(this.hovered) {
				this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,this.yPosition + this.height, 0x80FFFFFF, 0x80FFFFFF);
			}
		}
	}
}