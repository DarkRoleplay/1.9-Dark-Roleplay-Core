package net.drpcore.client.gui.crafting.recipeSelection;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SelectRecipeButton extends GuiButton {

	private ItemStack displayItem;
	
	public SelectRecipeButton(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 24;
		this.height = 24;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
		if(flag) {
			this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,this.yPosition + this.height, -2130706433, -2130706433);
		}
		this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width,this.yPosition + this.height, -2130706433, -2130706433);
	}

	public ItemStack getDisplayItem() {
		return displayItem;
	}

	public void setDisplayItem(ItemStack displayItem) {
		this.displayItem = displayItem;
	}
}
