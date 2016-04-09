package net.drpcore.common.entities.player.advancedInventoryCapabiliy;

import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AdvancedPlayerStorage implements Capability.IStorage<IPlayerInventoryAdvanced> {
	
	public final PlayerInventory inventory = new PlayerInventory();

	@Override
	public NBTBase writeNBT(Capability<IPlayerInventoryAdvanced> capability, IPlayerInventoryAdvanced instance, EnumFacing side) {

		NBTTagCompound nbtData = new NBTTagCompound();
		
		return inventory.writeToNBT(nbtData);
	}

	@Override
	public void readNBT(Capability<IPlayerInventoryAdvanced> capability, IPlayerInventoryAdvanced instance, EnumFacing side,NBTBase nbt) {
		
		NBTTagCompound properties = (NBTTagCompound) nbt;

		inventory.readFromNBT(properties);
	}

}
