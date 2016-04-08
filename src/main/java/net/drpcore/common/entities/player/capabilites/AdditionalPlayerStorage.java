package net.drpcore.common.entities.player.capabilites;

import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AdditionalPlayerStorage implements IStorage<IPlayerCapability> {

	public final PlayerInventory inventory = new PlayerInventory();

	@Override
	public NBTBase writeNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, EnumFacing side) {

		NBTTagCompound nbtData = new NBTTagCompound();
		
		inventory.writeToNBT(nbtData);

		return nbtData;
	}

	@Override
	public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, EnumFacing side,NBTBase nbt) {
		NBTTagCompound properties = (NBTTagCompound) nbt;

		inventory.readFromNBT(properties);
	}

}
