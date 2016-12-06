package net.drpcore.client.gui.craftingOld.buttons;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class changeIngredientType extends GuiButton {

	private boolean isSelected;
	
	public changeIngredientType(int buttonID, int x, int y, boolean selected) {
		super(buttonID, x, y, "");
		this.width = 18;
		this.height = 18;
		this.isSelected = selected;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		mc.getTextureManager().bindTexture(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png"));
		int x = 178;
		int y = 0;
		if(!this.isSelected){
			if(flag && this.enabled) {
				y += (this.height * 2);
			}
		}
		else{
			y += this.height;
		}
		this.drawTexturedModalRect(this.xPosition, this.yPosition, x, y, this.width, this.height);
	}
	
	public void toggle(){
		if(this.isSelected){
			this.isSelected = false;
		}else{
			this.isSelected = true;
		}
	
	}
}
