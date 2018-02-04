package net.dark_roleplay.drpcore.modules.color;

import net.minecraft.item.ItemStack;

public interface IColored {

	public int getColor(ItemStack stack);
	
	public void setColor(ItemStack stack, int color);
	
}
