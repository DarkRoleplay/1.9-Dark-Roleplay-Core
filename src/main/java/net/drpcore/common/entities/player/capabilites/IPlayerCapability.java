package net.drpcore.common.entities.player.capabilites;

import net.drpcore.common.gui.inventories.PlayerInventory;

public interface  IPlayerCapability {
	
	public final PlayerInventory inventory = new PlayerInventory();
	
	String getOwnerType();
	
	public PlayerInventory getInventory();
}
