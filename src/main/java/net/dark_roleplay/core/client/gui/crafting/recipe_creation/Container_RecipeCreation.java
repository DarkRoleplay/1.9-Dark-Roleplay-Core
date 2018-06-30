package net.dark_roleplay.core.client.gui.crafting.recipe_creation;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Container_RecipeCreation extends Container{

	IInventory recipe = new InventoryBasic("", true, 18);
		
	public Container_RecipeCreation(IInventory playerInventory){
		
		this.addPlayerSlots(playerInventory, 18);
		
		//int teSize = this.addStorageSlots();
		for(int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(recipe, i, 5  + (i * 18), 5));
		}
		for(int i = 9; i < 18; i++){
			this.addSlotToContainer(new Slot(recipe, i, 5  + ((i - 9) * 18), 25));
		}
		
	}
	
	private void addPlayerSlots(IInventory inventory, int offset){
		int yPos = (int) (30 + (Math.ceil((offset / 9D)) * 18));
		int xPos = 9;
		// Slots for the main inventor
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(inventory, x + (y * 9) + 9 , xPos, yPos));
                xPos += 18;
            }
            yPos += 18;
            xPos = 9;
        }

        // Slots for the hotbar
        yPos += 4;
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(inventory, x, xPos, yPos));
            xPos += 18;
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//
//            if (index > 46) {
//                if (!this.mergeItemStack(itemstack1, 46, this.inventorySlots.size(), true))
//					return ItemStack.EMPTY;
//            } else if (!this.mergeItemStack(itemstack1, 0, 45, false))
//				return ItemStack.EMPTY;
//
//            if (itemstack1.getCount() == 0) {
//                slot.putStack(ItemStack.EMPTY);
//            } else {
//                slot.onSlotChanged();
//            }
        }

        return itemstack;
    }

	
}
