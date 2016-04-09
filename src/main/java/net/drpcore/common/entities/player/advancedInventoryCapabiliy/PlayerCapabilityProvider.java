package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>{

	IPlayerInventoryAdvanced inst;

	public PlayerCapabilityProvider(Entity entity){
		if(!entity.hasCapability(DarkRoleplayCore.getDrpcoreInv(), null)){
			inst = DarkRoleplayCore.getDrpcoreInv().getDefaultInstance();
		}else{
			inst = entity.getCapability(DarkRoleplayCore.getDrpcoreInv(), null);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return DarkRoleplayCore.getDrpcoreInv() != null && capability == DarkRoleplayCore.getDrpcoreInv();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == DarkRoleplayCore.getDrpcoreInv() ? DarkRoleplayCore.getDrpcoreInv().<T>cast(inst) : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return (NBTTagCompound)DarkRoleplayCore.getDrpcoreInv().getStorage().writeNBT(DarkRoleplayCore.getDrpcoreInv(), inst, null);
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {

		DarkRoleplayCore.getDrpcoreInv().getStorage().readNBT(DarkRoleplayCore.getDrpcoreInv(), inst, null, nbt);
		
	}


}
