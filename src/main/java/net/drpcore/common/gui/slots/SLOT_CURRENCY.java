package net.drpcore.common.gui.slots;

import net.drpcore.common.items.templates.CurrencyBase;
import net.drpcore.common.items.templates.PurseBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SLOT_CURRENCY extends Slot{

	public SLOT_CURRENCY(IInventory inv, int index, int xPos, int yPos)
	{
		super(inv, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		// Everything returns true except an instance of our Item
		return !(itemstack.getItem() instanceof PurseBase) && itemstack.getItem() instanceof CurrencyBase;
	}
	
}
