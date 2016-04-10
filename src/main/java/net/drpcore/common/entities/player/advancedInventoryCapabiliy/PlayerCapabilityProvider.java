package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>{

	IPlayerInventoryAdvanced inst = DarkRoleplayCore.DRPCORE_INV.getDefaultInstance();

	public PlayerCapabilityProvider(Entity entity){
		if(!entity.hasCapability(DarkRoleplayCore.DRPCORE_INV, null)){
			inst = DarkRoleplayCore.DRPCORE_INV.getDefaultInstance();
		}else{
			inst = entity.getCapability(DarkRoleplayCore.DRPCORE_INV, null);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return DarkRoleplayCore.DRPCORE_INV != null && capability == DarkRoleplayCore.DRPCORE_INV;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == DarkRoleplayCore.DRPCORE_INV ? DarkRoleplayCore.DRPCORE_INV.<T>cast(inst) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound)DarkRoleplayCore.DRPCORE_INV.getStorage().writeNBT(DarkRoleplayCore.DRPCORE_INV, inst, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		DarkRoleplayCore.DRPCORE_INV.getStorage().readNBT(DarkRoleplayCore.DRPCORE_INV, inst, null, nbt);
		
	}


}
