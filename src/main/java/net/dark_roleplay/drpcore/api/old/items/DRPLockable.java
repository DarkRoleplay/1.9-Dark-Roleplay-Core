package net.dark_roleplay.drpcore.api.old.items;

import net.dark_roleplay.drpcore.api.old.modules.locks.ILock;
import net.dark_roleplay.library.items.DRPItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DRPLockable extends DRPItem implements ILock{

	protected ILock.TYPE type;
	
	public DRPLockable(String name, ILock.TYPE type, int stackSize, String... subNames){
		this(name, null, type, stackSize, subNames);
	}
		
	public DRPLockable(String name, String itemFolder, ILock.TYPE type, int stackSize, String[] subNames) {
		super(name, itemFolder, stackSize, subNames);
		this.type = type;
		this.setCreativeTab(CreativeTabs.FOOD);
	}

	@Override
	public boolean canUnlock(ItemStack lock, ItemStack key) {
		if(lock.getItem() instanceof ILock && key.getItem() instanceof ILock){
			ILock iLock = (ILock) lock.getItem();
			ILock iKey = (ILock) key.getItem();
			if(iLock.getType(lock) != ILock.TYPE.LOCK || iLock.getType(key) != ILock.TYPE.KEY)
				return false;
			return iLock.getLockCode(lock) == iKey.getLockCode(key);
		}
		return false;
	}

	@Override
	public TYPE getType(ItemStack stack) {
		return this.type;
	}
	
	@Override
	public int getLockCode(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.hasKey("lock_code"))
			return nbt.getInteger("lock_code");
		return 0;
	}

	@Override
	public void setLockCode(ItemStack stack, int code) {
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger("lock_code", code);
		stack.setTagCompound(nbt);
	}

}
