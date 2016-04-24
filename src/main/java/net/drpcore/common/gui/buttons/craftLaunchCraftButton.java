package net.drpcore.common.gui.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class craftLaunchCraftButton extends GuiButton {

	public craftLaunchCraftButton(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 19;
		this.height = 19;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {

		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/GuiCrafting.png"));
		int x = 178; // 178;
		int y = 25; // 110;
		if(this.enabled){
			if(!flag){
				x += width;
			}
			else{
				x += width * 2;
			}
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}

}