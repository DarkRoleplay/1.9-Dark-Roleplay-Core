package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.gui.inventories.PlayerInventory;

public interface  IPlayerInventoryAdvanced {
	/**
	 * @return the additional Inventory from the Player
	 */
	public void setInventory(PlayerInventory playerInv);
	
	/**
	 * @return the additional Inventory from the Player
	 */
	public PlayerInventory getInventory();
}
