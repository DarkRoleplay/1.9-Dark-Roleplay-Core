package net.dark_roleplay.core.client.gui.crafting.recipe_crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_IncAmount extends GuiButton {
	
	
	private boolean flip;
	
	public Button_IncAmount(int buttonID, int x, int y, boolean flip) {
		super(buttonID, x, y, "");
		this.width = 7;
		this.height = 7;
		this.flip = flip;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int x = 196;
		int y = 0;
			
		if(flip)
			x += this.width;
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