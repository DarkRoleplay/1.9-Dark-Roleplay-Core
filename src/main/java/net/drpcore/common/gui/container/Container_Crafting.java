package net.drpcore.common.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class Container_Crafting extends Container {

	public Container_Crafting(IInventory playerInv) {
		int x0 = 8;
		int y0 = 95;

		int reihe = 0;
		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, x0 + x * 18, y0 + y * 18));
			}
		}
		for(int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, x0 + x * 18, y0 + 3 * 18 + 3));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {

		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(index < 27) {
				if( ! this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if( ! this.mergeItemStack(itemstack1, 0, 27, false)) {
				return null;
			}
			if(itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemstack;
	}
}
