package net.drpcore.common.gui.container;

import net.drpcore.client.gui.slots.SLOT_CURRENCY;
import net.drpcore.common.gui.inventories.PurseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_Purse extends Container {

	public final PurseInventory inventory;

	private int INV_START = 0, INV_END = 0, HOTBAR_START = 0, HOTBAR_END = 0;

	public Container_Purse(EntityPlayer par1Player, InventoryPlayer inventoryPlayer, PurseInventory PurseInventory) {

		this.INV_START = PurseInventory.INV_SIZE - 1;

		this.INV_END = this.INV_START + 26;
		this.HOTBAR_START = this.INV_END + 1;
		this.HOTBAR_END = this.HOTBAR_START + 8;

		int i;

		this.inventory = PurseInventory;

		int index = 0;

		for (i = 0; i < PurseInventory.INV_SIZE; ++i) {
			this.addSlotToContainer(new SLOT_CURRENCY(this.inventory, index++, 62 + i * 18, 8));
		}

		// PLAYER INVENTORY - uses default locations for standard inventory
		// texture file
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 39 + i * 18));
			}
		}

		// PLAYER ACTION BAR - uses default locations for standard action bar
		// texture file
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 97));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < INV_START) {
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else {
				/*
				 * If your inventory only stores certain instances of Items, you
				 * can implement shift-clicking to your inventory like this:
				 * 
				 * // Check that the item is the right type if
				 * (itemstack1.getItem() instanceof ItemCustom) { // Try to
				 * merge into your custom inventory slots // We use
				 * 'InventoryLeatherPurse.INV_SIZE' instead of INV_START just in
				 * case // you also add armor or other custom slots if
				 * (!this.mergeItemStack(itemstack1, 0,
				 * InventoryLeatherPurse.INV_SIZE, false)) { return null; } } //
				 * If you added armor slots, check them here as well: // Item
				 * being shift-clicked is armor - try to put in armor slot if
				 * (itemstack1.getItem() instanceof ItemArmor) { int type =
				 * ((ItemArmor) itemstack1.getItem()).armorType; if
				 * (!this.mergeItemStack(itemstack1, ARMOR_START + type,
				 * ARMOR_START + type + 1, false)) { return null; } } Otherwise,
				 * you have basically 2 choices: 1. shift-clicking between
				 * player inventory and custom inventory 2. shift-clicking
				 * between action bar and inventory
				 * 
				 * Be sure to choose only ONE of the following
				 * implementations!!!
				 */
				if (index >= INV_START) {
					// place in custom inventory
					// if (!this.mergeItemStack(itemstack1, 0, INV_START,
					// false)) {
					return null;
					// }
				}

				if (index >= INV_START && index < HOTBAR_START) {
					// place in action bar
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
						return null;
					}
				}

				else if (index >= HOTBAR_START && index < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	/*@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null) {
			return super.retrySlotClick(slot, button, flag, player);
		} else {
			return null;
		}
	}*/
}
