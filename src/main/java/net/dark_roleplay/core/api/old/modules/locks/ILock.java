package net.dark_roleplay.core.api.old.modules.locks;

import net.minecraft.item.ItemStack;

public interface ILock {

	public boolean canUnlock(ItemStack lock, ItemStack key);
	
	public TYPE getType(ItemStack stack);

	public void setLockCode(ItemStack stack, int code);
	
	public int getLockCode(ItemStack stack);
	
	public static enum TYPE{
		LOCK,
		KEY;
	}
	
}
