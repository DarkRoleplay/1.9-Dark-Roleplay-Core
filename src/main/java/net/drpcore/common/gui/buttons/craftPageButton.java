package net.drpcore.common.gui.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;


public class craftPageButton extends GuiButton {

	boolean dir;

	public craftPageButton(int buttonID, int x, int y, boolean dir) {
		super(buttonID, x, y, "");
		this.dir = dir;
		this.width = 30;
		this.height = 10;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {

		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/GuiCrafting.png"));
		int x = 218; // 178;
		int y = 110; // 110;
		if(this.enabled) {
			if( ! flag) {
				if( ! this.dir) {
					y += this.height * 2;
				} else {
					y += this.height * 3;
				}
			} else {
				if( ! this.dir) {
					y += (this.height * 4);
				} else {
					y += (this.height * 5);
				}
			}
		} else {
			if(this.dir)
				y += this.height;
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}
