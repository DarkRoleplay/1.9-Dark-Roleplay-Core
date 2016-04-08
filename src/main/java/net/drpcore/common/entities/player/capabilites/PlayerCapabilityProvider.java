package net.drpcore.common.entities.player.capabilites;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class PlayerCapabilityProvider implements ICapabilityProvider , IPlayerCapability {
	
	private Entity entity;

	public PlayerCapabilityProvider(Entity entity)
    {
        this.entity = entity;
    }

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return DarkRoleplayCore.getDrpcoreInv() != null && capability == DarkRoleplayCore.getDrpcoreInv();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (DarkRoleplayCore.getDrpcoreInv() != null && capability == DarkRoleplayCore.getDrpcoreInv())
			return (T) this;
		return null;
	}
	
	@Override
	public String getOwnerType() {
		return entity.getClass().getName();
	}

	@Override
	public PlayerInventory getInventory() {
		return inventory;
	}
}
