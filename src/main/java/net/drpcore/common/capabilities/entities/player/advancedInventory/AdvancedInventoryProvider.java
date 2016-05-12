package net.drpcore.common.capabilities.entities.player.advancedInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class AdvancedInventoryProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>{

	@CapabilityInject(IPlayerAdvancedInventory.class)
	public static Capability<IPlayerAdvancedInventory> ADVANCED_INVENTORY = null;
	
	private IPlayerAdvancedInventory inventory = null;
	
	public AdvancedInventoryProvider(){
		inventory = new DefaultAdvancedInventory();
	}
	
	public AdvancedInventoryProvider(IPlayerAdvancedInventory inventory){
		this.inventory = inventory;
	}
	
	public static IPlayerAdvancedInventory get(EntityPlayer player) {
		return player.hasCapability(ADVANCED_INVENTORY, null)? player.getCapability(ADVANCED_INVENTORY, null): null;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return inventory.saveNBTData();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		inventory.loadNBTData(nbt);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return ADVANCED_INVENTORY != null && capability == ADVANCED_INVENTORY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (ADVANCED_INVENTORY != null && capability == ADVANCED_INVENTORY) return (T)inventory;
		return null;
	}
	
}
