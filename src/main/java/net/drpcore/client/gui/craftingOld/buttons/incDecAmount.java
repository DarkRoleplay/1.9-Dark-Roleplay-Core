package net.drpcore.client.gui.craftingOld.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class incDecAmount extends GuiButton {

	private boolean dir;
	private byte type;
	
	public incDecAmount(int buttonID, int x, int y, boolean selected, byte type) {
		super(buttonID, x, y, "");
		switch(type){
			case 0:
			case 1:
				this.width = 9;
				this.height = 7;
				break;
			case 2:
				this.width = 9;
				this.height = 10;
				break;
		}
		this.dir = selected;
		this.type = type;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png"));
		int x = 212;
		int y = 0;
		switch(type){
			case 0:
				y += flag ? 24 : 0;
				break;
			case 1:
				y += flag ? 7 + 24 : 7;
				break;
			case 2:
				y += flag ? 14 + 24 : 14;
				break;
		}
		if(this.dir){
			x += 9;
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
}
