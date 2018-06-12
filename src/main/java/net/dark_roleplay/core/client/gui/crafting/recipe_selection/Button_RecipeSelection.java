package net.dark_roleplay.core.client.gui.crafting.recipe_selection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_RecipeSelection  extends GuiButton {
	
	public Button_RecipeSelection(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 24;
		this.height = 24;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if(this.enabled){
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if(this.hovered) {
				this.drawGradientRect(this.x, this.y, this.x + this.width,this.y + this.height, 0x80FFFFFF, 0x80FFFFFF);
			}
		}
	}
}