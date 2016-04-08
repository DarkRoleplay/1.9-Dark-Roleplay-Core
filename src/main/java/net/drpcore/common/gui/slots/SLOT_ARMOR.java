package net.drpcore.common.gui.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SLOT_ARMOR extends Slot{
    final EntityEquipmentSlot armorType = null;
	
	final EntityPlayer player;
	
	public SLOT_ARMOR(EntityPlayer player, IInventory inventory, int par3, int par4, int par5, int par6){
		super(inventory, par3, par4, par5);
		this.player = player;
		//this.armorType = par6;
	}
	
	public int getSlotStackLimit(){
		return 1;
	}
	
	public boolean isItemValid(ItemStack itemstack){
		Item item = (itemstack == null ? null : itemstack.getItem());
		return item != null && item.isValidArmor(itemstack, armorType, player);
	}

}
