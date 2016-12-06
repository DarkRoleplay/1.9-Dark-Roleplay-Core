package net.drpcore.client.gui.craftingOld.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class StartCraft extends GuiButton {

	public StartCraft(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 19;
		this.height = 19;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png"));
		int x = 230;
		int y = 0;
		if(this.enabled) {
			if( ! flag) {
				y += this.height;
			} else {
				y += (this.height * 2);
			}
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}

