package net.drpcore.common.entities.player.capabilites;

import net.drpcore.common.gui.inventories.PlayerInventory;

public class DefaultImplementation implements IPlayerCapability{

	@Override
    public String getOwnerType(){
        return "Default Implementation!";
    }

	@Override
	public PlayerInventory getInventory() {
		return null;
	}

}
