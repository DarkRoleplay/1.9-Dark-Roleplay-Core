package net.drpcore.common.capabilities.entities.player.advancedInventory;

import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerAdvancedInventory {

	/**
	 * @return the additional Inventory from the Player
	 */
	public void setInventory(AdvancedPlayerInventory playerInv);

	/**
	 * @return the additional Inventory from the Player
	 */
	public AdvancedPlayerInventory getInventory();

	
	public NBTTagCompound saveNBTData();
	
	public void loadNBTData(NBTTagCompound compound);
	
}
