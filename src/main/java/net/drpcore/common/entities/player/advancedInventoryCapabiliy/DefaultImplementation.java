package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;

public class DefaultImplementation implements IPlayerInventoryAdvanced{

	private AdvancedPlayerInventory inventory = new AdvancedPlayerInventory();
	
	@Override
	public void setInventory(AdvancedPlayerInventory playerInv) {
		this.inventory = playerInv;
	}

	@Override
	public AdvancedPlayerInventory getInventory() {
		return inventory;
	}

}