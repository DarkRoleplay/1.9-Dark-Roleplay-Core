package net.dark_roleplay.drpcore.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_SwitchExtended extends GuiButton {
	
	public Button_SwitchExtended(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 22;
		this.height = 22;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int x = 0;
		int y = 162;
			
		if(this.enabled) {
			if(!this.hovered) {
				x += this.width;
			} else {
				x += (this.width * 2);
			}
		}
		this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
	}
}

