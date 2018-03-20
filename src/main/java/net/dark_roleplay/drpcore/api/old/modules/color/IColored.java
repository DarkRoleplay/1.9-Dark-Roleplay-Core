package net.dark_roleplay.drpcore.api.old.modules.color;

import net.minecraft.item.ItemStack;

public interface IColored {

	public int getColor(ItemStack stack);
	
	public void setColor(ItemStack stack, int color);
	
}
