package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AdvancedPlayerStorage implements Capability.IStorage<IPlayerInventoryAdvanced> {
	
	public final AdvancedPlayerInventory inventory = new AdvancedPlayerInventory();

	@Override
	public NBTBase writeNBT(Capability<IPlayerInventoryAdvanced> capability, IPlayerInventoryAdvanced instance, EnumFacing side) {

		NBTTagCompound nbtData = new NBTTagCompound();
		
		return instance.getInventory().writeToNBT(nbtData);
	}

	@Override
	public void readNBT(Capability<IPlayerInventoryAdvanced> capability, IPlayerInventoryAdvanced instance, EnumFacing side,NBTBase nbt) {
		
		NBTTagCompound properties = (NBTTagCompound) nbt;

		instance.getInventory().readFromNBT(properties);
	}

}
