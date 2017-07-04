package net.dark_roleplay.drpcore.client.gui.skills;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_Change extends GuiButton {

	boolean dir = false;

	public Button_Change(int buttonID, int x, int y,boolean dir) {
		super(buttonID, x, y, "");
		this.dir = dir;
		this.width = 10;
		this.height = 11;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int x = 32;
		int y = 0;
		if(this.dir)
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
