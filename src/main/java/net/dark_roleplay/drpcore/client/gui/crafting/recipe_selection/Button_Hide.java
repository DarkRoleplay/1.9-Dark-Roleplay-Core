package net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_Hide extends GuiButton {
	
	boolean active;
	
	public Button_Hide(int buttonID, int x, int y, boolean active) {
		super(buttonID, x, y, "");
		this.width = 14;
		this.height = 7;
		this.active = active;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		int x = 202;
		int y = 0;
		
		if(!this.active) {
			y += 7;
		}

		this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	
}