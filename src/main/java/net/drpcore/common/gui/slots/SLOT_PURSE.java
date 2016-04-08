package net.drpcore.common.gui.slots;

import net.drpcore.common.items.templates.PurseBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SLOT_PURSE extends Slot{
	
	public SLOT_PURSE(IInventory inventory, int par2, int par3, int par4){
		super(inventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack){
		return itemstack.getItem() instanceof PurseBase;
	}

}
