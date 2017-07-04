package net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_Craft extends GuiButton {
	
	public Button_Craft(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 19;
		this.height = 19;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int x = 237;
		int y = 0;
			
		if(this.enabled) {
			if(!this.hovered) {
				y += this.height;
			} else {
				y += (this.height * 2);
			}
		}
		this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
	}
}
