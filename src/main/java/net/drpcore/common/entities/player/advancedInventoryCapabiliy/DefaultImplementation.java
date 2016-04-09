package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.gui.inventories.PlayerInventory;

public class DefaultImplementation implements IPlayerInventoryAdvanced{

	private PlayerInventory inventory = null;
	
	@Override
	public void setInventory(PlayerInventory playerInv) {
		this.inventory = playerInv;
	}

	@Override
	public PlayerInventory getInventory() {
		return inventory;
	}

}
