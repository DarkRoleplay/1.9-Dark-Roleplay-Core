package net.dark_roleplay.core.testing.expanded_inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

public class ExpandedInventoryContainer extends Container{

//	private ItemStack itemProtector;
	protected int slotCount = 0;

	public ExpandedInventoryContainer(InventoryPlayer playerInventory, IExpandedInventory expInventory) {

//		this.itemProtector = stack;
//
//		if(stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
//			IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

//			this.slotCount = handler.getSlots();

//			int size = handler.getSlots();

//			for(int y = 0; y < Math.ceil(size / 9F); y++) {
//				for(int x = 0; x < Math.min(9, size - (y * 9)); x++) {
//					if(handler.getStackInSlot(x + (y * 9)) == stack)
//				        this.addSlotToContainer(new SlotItemHandler(handler, x + (y * 9), -100, -100));
//					else
//						this.addSlotToContainer(new SlotItemHandler(handler, x + (y * 9), 8 + (x * 18), 8 + (y * 18)));
//				}
//			}
//		}

//		int size = expInventory.getSlots();

		this.addSlotToContainer(new SlotItemHandler(expInventory, 1, 62, 85));


//		for(int y = 0; y < Math.ceil(size / 9F); y++) {
//			for(int x = 0; x < Math.min(9, size - (y * 9)); x++) {
//				this.addSlotToContainer(new SlotItemHandler(expInventory, x + (y * 9), -20 + (x * 18), 8 + (y * 18)));
//			}
//		}

        this.bindPlayerInventory(playerInventory);
	}



    protected void bindPlayerInventory(InventoryPlayer playerInventory){
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 9; x++){
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, x * 18 + 8, 125 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++){
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 183));
        }

        for(int i = 0; i < 4; i++) {
            this.addSlotToContainer(new Slot(playerInventory, 39 - i, 8, 13 + (i * 18)));
        }


        this.addSlotToContainer(new Slot(playerInventory, 40, 8, 85));
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
