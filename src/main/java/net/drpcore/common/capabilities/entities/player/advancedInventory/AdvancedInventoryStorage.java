package net.drpcore.common.capabilities.entities.player.advancedInventory;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


public class AdvancedInventoryStorage implements IStorage<IPlayerAdvancedInventory> {

	public static final AdvancedInventoryStorage inventoryStorage = new AdvancedInventoryStorage();

	@Override
	public NBTBase writeNBT(Capability<IPlayerAdvancedInventory> capability, IPlayerAdvancedInventory instance, EnumFacing side) {

		NBTTagCompound nbtData = new NBTTagCompound();
		return instance.getInventory().writeToNBT(nbtData);
	}

	@Override
	public void readNBT(Capability<IPlayerAdvancedInventory> capability, IPlayerAdvancedInventory instance, EnumFacing side, NBTBase nbt) {

		NBTTagCompound properties = (NBTTagCompound) nbt;
		instance.getInventory().readFromNBT(properties);
	}
}
