package net.drpcore.common.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class Container_Crafting extends Container {

	public Container_Crafting(IInventory playerInv) {
		int x0 = 8;
		int y0 = 107;
		int x;
		int y;
		int reihe = 0;
		int spalte = 0;
		for(y = 0; y < 3; ++y) {
			for(x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, x0 + x * 18, y0 + y * 18));
			}
			reihe++ ;
		}
		for(x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, x0 + x * 18, y0 + reihe * 18 + 3));
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
	/*
	 * @Override protected boolean mergeItemStack(ItemStack stack, int start,
	 * int end, boolean backwards){ boolean flag1 = false; int k = (backwards ?
	 * end - 1 : start); Slot slot; ItemStack itemstack1; if
	 * (stack.isStackable()){ while (stack.stackSize > 0 && (!backwards && k <
	 * end || backwards && k >= start)){ slot = (Slot) inventorySlots.get(k);
	 * itemstack1 = slot.getStack(); if (!slot.isItemValid(stack)) { k +=
	 * (backwards ? -1 : 1); continue; } if (itemstack1 != null &&
	 * itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() ||
	 * stack.getItemDamage() == itemstack1.getItemDamage()) &&
	 * ItemStack.areItemStackTagsEqual(stack, itemstack1)) { int l =
	 * itemstack1.stackSize + stack.stackSize; if (l <= stack.getMaxStackSize()
	 * && l <= slot.getSlotStackLimit()) { stack.stackSize = 0;
	 * itemstack1.stackSize = l; //inventory2.markDirty(); flag1 = true; } else
	 * if (itemstack1.stackSize < stack.getMaxStackSize() && l <
	 * slot.getSlotStackLimit()) { stack.stackSize -= stack.getMaxStackSize() -
	 * itemstack1.stackSize; itemstack1.stackSize = stack.getMaxStackSize();
	 * //inventory2.markDirty(); flag1 = true; } } k += (backwards ? -1 : 1); }
	 * } if (stack.stackSize > 0) { k = (backwards ? end - 1 : start); while
	 * (!backwards && k < end|| backwards && k >= start) { slot = (Slot)
	 * inventorySlots.get(k); itemstack1 = slot.getStack(); if
	 * (!slot.isItemValid(stack)) { k += (backwards ? -1 : 1); continue; } if
	 * (itemstack1 == null) { int l = stack.stackSize; if (l <=
	 * slot.getSlotStackLimit()) { slot.putStack(stack.copy()); stack.stackSize
	 * = 0; //inventory2.markDirty(); flag1 = true; break; } else {
	 * putStackInSlot(k, new ItemStack(stack.getItem(),
	 * slot.getSlotStackLimit(), stack.getItemDamage())); stack.stackSize -=
	 * slot.getSlotStackLimit(); //inventory2.markDirty(); flag1 = true; } } k
	 * += (backwards ? -1 : 1); } } return flag1; }
	 */
}
