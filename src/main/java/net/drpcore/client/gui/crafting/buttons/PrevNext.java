package net.drpcore.client.gui.crafting.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class PrevNext  extends GuiButton {

	boolean dir = false;
	int textureX = 0;
	int textureY = 0;
	ResourceLocation textureLoc = null;

	public PrevNext(int buttonID, int x, int y,boolean dir, int width,int height,  int textureX, int textureY, ResourceLocation textureLoc) {
		super(buttonID, x, y, "");
		this.dir = dir;
		this.width = width;
		this.height = height;
		this.textureX = textureX;
		this.textureY = textureY;
		this.textureLoc = textureLoc;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(this.textureLoc);
		int x = this.textureX;
		int y = this.textureY;
		if(this.enabled) {
			if( ! flag) {
				if( ! this.dir) {
					y += this.height;
				} else {
					y += this.height;
					x += this.width;
				}
			} else {
				if( ! this.dir) {
					y += (this.height * 2);
				} else {
					y += (this.height * 2);
					x += this.width;
				}
			}
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}