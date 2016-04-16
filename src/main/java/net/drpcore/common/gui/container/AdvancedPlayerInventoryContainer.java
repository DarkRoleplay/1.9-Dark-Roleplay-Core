package net.drpcore.common.gui.container;

import net.drpcore.common.gui.inventories.InventoryCustom;
import net.drpcore.client.gui.slots.SLOT_AMMUNITIONCONTAINER;
import net.drpcore.client.gui.slots.SLOT_ARMOR;
import net.drpcore.client.gui.slots.SLOT_BACKPACK;
import net.drpcore.client.gui.slots.SLOT_BELT;
import net.drpcore.client.gui.slots.SLOT_NECKLACE;
import net.drpcore.client.gui.slots.SLOT_PURSE;
import net.drpcore.client.gui.slots.SLOT_RING;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.items.templates.AmmunitionBase;
import net.drpcore.common.items.templates.NecklaceBase;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.items.templates.RingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdvancedPlayerInventoryContainer extends Container{

	private final InventoryCustom inventory2;
	
	private static final int HOTBAR_START = 0;
	private static final int HOTBAR_END = HOTBAR_START + 8;
	
	private static final int INV_START = HOTBAR_END +1;
	private static final int INV_END = INV_START+26;
	
	private static final int ARMOR_START = INV_END + 1;
	private static final int ARMOR_END = ARMOR_START +3;
	
	private static final int OFFHAND = ARMOR_END + 1;
	
	private static final int SPECIAL_NECKLACE = OFFHAND + 1;
	private static final int SPECIAL_RING_LEFT = OFFHAND + 2;
	private static final int SPECIAL_RING_RIGHT = OFFHAND + 3;
	private static final int SPECIAL_BELT = OFFHAND + 4;
	private static final int SPECIAL_PURSE = OFFHAND + 5;
	private static final int SPECIAL_BACKPACK = OFFHAND + 6;
	private static final int SPECIAL_AMMUNITIONCONTAINER = OFFHAND + 7;
	private static final int SPECIAL_COSMETIC_ARMOR_START = SPECIAL_AMMUNITIONCONTAINER + 1;
	private static final int SPECIAL_COSMETIC_ARMOR_END = SPECIAL_COSMETIC_ARMOR_START + 3;
	
	private final EntityPlayer thePlayer;

	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	public AdvancedPlayerInventoryContainer(EntityPlayer player, InventoryPlayer inventoryPlayer, AdvancedPlayerInventory inventoryCustom){
		this.thePlayer = player;
		
		//Hotbar
		for (int i = 0; i < 9; ++i){
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 141));
		}
			
		// Add vanilla PLAYER INVENTORY
		for (int i = 0; i < 3; ++i){
			for (int j = 0; j < 9; ++j){
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 83 + i * 18));
			}
		}
		
		// Armor Slots
		for (int i = 0; i < 4; ++i){
			this.addSlotToContainer(new SLOT_ARMOR(player, inventoryPlayer, inventoryPlayer.getSizeInventory() - 2 - i, 44, 9 + i * 18, i));
		}
		
		// Offhand
		this.addSlotToContainer(new Slot(inventoryPlayer, 40, 134, 63)
        {
            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            public boolean isItemValid(ItemStack stack)
            {
                return super.isItemValid(stack);
            }
            @SideOnly(Side.CLIENT)
            public String getSlotTexture()
            {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });
		
		//custom Slots
		addSlotToContainer(new SLOT_NECKLACE(inventoryCustom, 0, 116, 9));
		addSlotToContainer(new SLOT_RING(inventoryCustom, 1, 116, 27));
		addSlotToContainer(new SLOT_RING(inventoryCustom, 2, 116, 45));
		addSlotToContainer(new SLOT_BELT(inventoryCustom, 3, 116, 63));
		
		addSlotToContainer(new SLOT_BACKPACK(inventoryCustom, 5, 134, 9));
		addSlotToContainer(new SLOT_PURSE(inventoryCustom, 4, 134, 27));
		addSlotToContainer(new SLOT_AMMUNITIONCONTAINER(inventoryCustom,6,134,45));

		//Cosmetic Armor Slots
	
		for (int i = 0; i < 4; ++i){
			this.addSlotToContainer(new SLOT_ARMOR(player, inventoryCustom, inventoryCustom.getSizeInventory() - 4 + i, 26, 9 + i * 18, i));
		}
		
		inventory2 = inventoryCustom;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int klicked){
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(klicked);
		if (slot != null && slot.getHasStack()){
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			// Item is in inventory / hotbar, try to place either in custom or armor slots
			if(klicked > HOTBAR_START -1 && klicked <= INV_END){
				// if item is our custom item
				if(itemstack1.getItem() instanceof NecklaceBase){
					if(!this.mergeItemStack(itemstack1, SPECIAL_NECKLACE, SPECIAL_NECKLACE + 1, false))return null;
				}
				else if(itemstack1.getItem() instanceof RingBase){
					if(!this.mergeItemStack(itemstack1, SPECIAL_RING_LEFT, SPECIAL_RING_RIGHT + 1, false))return null;
				}
				else if(itemstack1.getItem() instanceof PurseBase){
					if (!this.mergeItemStack(itemstack1, SPECIAL_PURSE, SPECIAL_PURSE + 1, false))return null;
				}else if (itemstack1.getItem() instanceof AmmunitionBase){
					if(!this.mergeItemStack(itemstack1, SPECIAL_AMMUNITIONCONTAINER, SPECIAL_AMMUNITIONCONTAINER + 1, false))return null;
				}
				// if item is armor
				/**else if (itemstack1.getItem() instanceof ItemArmor){
					int type = ((ItemArmor) itemstack1.getItem()).armorType;
					if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START +1 + type, false))return null;
				}**/else if (klicked > HOTBAR_START -1 && klicked <= HOTBAR_END){
					if(!this.mergeItemStack(itemstack1,  INV_START, INV_END +1, false)) return null;
				}else if(klicked > INV_START-1 && klicked <= INV_END){
					if(!this.mergeItemStack(itemstack1, HOTBAR_START, INV_END + 1, false))return null;
				}
			}else if(klicked > ARMOR_START - 1 && klicked <= SPECIAL_BACKPACK){
				if(!this.mergeItemStack(itemstack1, HOTBAR_START, INV_END +1, false)){
					return null;
				}
			}
			if (itemstack1.stackSize == 0){
				slot.putStack((ItemStack) null);
			}
			else{
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize){
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards){
		boolean flag1 = false;
		int k = (backwards ? end - 1 : start);
		Slot slot;
		ItemStack itemstack1;

		if (stack.isStackable()){
			while (stack.stackSize > 0 && (!backwards && k < end || backwards && k >= start)){
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (!slot.isItemValid(stack)) {
					k += (backwards ? -1 : 1);
					continue;
				}

				if (itemstack1 != null && itemstack1.getItem() == stack.getItem() &&
						(!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) &&
						ItemStack.areItemStackTagsEqual(stack, itemstack1))
				{
					int l = itemstack1.stackSize + stack.stackSize;

					if (l <= stack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
						stack.stackSize = 0;
						itemstack1.stackSize = l;
						inventory2.markDirty();
						flag1 = true;
					} else if (itemstack1.stackSize < stack.getMaxStackSize() && l < slot.getSlotStackLimit()) {
						stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = stack.getMaxStackSize();
						inventory2.markDirty();
						flag1 = true;
					}
				}

				k += (backwards ? -1 : 1);
			}
		}

		if (stack.stackSize > 0)
		{
			k = (backwards ? end - 1 : start);

			while (!backwards && k < end|| backwards && k >= start) {
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (!slot.isItemValid(stack)) {
					k += (backwards ? -1 : 1);
					continue;
				}

				if (itemstack1 == null) {
					int l = stack.stackSize;

					if (l <= slot.getSlotStackLimit()) {
						slot.putStack(stack.copy());
						stack.stackSize = 0;
						inventory2.markDirty();
						flag1 = true;
						break;
					} else {
						putStackInSlot(k, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
						stack.stackSize -= slot.getSlotStackLimit();
						inventory2.markDirty();
						flag1 = true;
					}
				}

				k += (backwards ? -1 : 1);
			}
		}

		return flag1;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
    {
       // InventoryPlayer inventoryplayer = player.inventory;
        
       // if (inventoryplayer.getItemStack() != null)
      //  {
       //     player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
       //     inventoryplayer.setItemStack((ItemStack)null);
       // }   
    }
}
