package net.drpcore.client.gui.crafting.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class InfoButton extends GuiButton {

	byte state;
	
	public InfoButton(int buttonID, int x, int y, byte state) {
		super(buttonID, x, y, "");
		this.state = state;
		this.width = 9;
		this.height = 9;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png"));
		int x = 178;
		int y = 54;
		if(this.enabled) {
			switch(state){
				case 3:
					y += this.height;
				case 2:
					y += this.height;
				case 1:
					y += this.height;
					break;
			}
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}


