package net.drpcore.client.gui.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class craftIngredientButton extends GuiButton {

	boolean dir;

	public craftIngredientButton(int buttonID, int x, int y, boolean dir) {
		super(buttonID, x, y, "");
		this.dir = dir;
		this.width = 10;
		this.height = 18;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
				&& mouseY < this.yPosition + this.height;
		mc.getTextureManager()
				.bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/GuiCrafting.png"));
		int x = 198; // 178;
		int y = 110; // 110;
		if (this.enabled) {
			if (!flag) {
				if (!this.dir) {
					y += this.height;
				} else {
					y += this.height;
					x += this.width;
				}
			} else {
				if (!this.dir) {
					y += (this.height * 2);
				} else {
					y += (this.height * 2);
					x += this.width;
				}
			}
		} else {
			if (this.dir) {
				x += this.width;
			}
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}
