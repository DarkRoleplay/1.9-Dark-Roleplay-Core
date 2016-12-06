package net.drpcore.common.capabilitiesOld.entities.player.advancedInventory;

import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class DefaultAdvancedInventory implements IPlayerAdvancedInventory {

	private AdvancedPlayerInventory inventory = new AdvancedPlayerInventory();

	public DefaultAdvancedInventory() {
		inventory = new AdvancedPlayerInventory();
	}

	@Override
	public void setInventory(AdvancedPlayerInventory playerInv) {

		this.inventory = playerInv;
	}

	@Override
	public AdvancedPlayerInventory getInventory() {

		return this.inventory;
	}

	@Override
	public NBTTagCompound saveNBTData() {

		return (NBTTagCompound) AdvancedInventoryStorage.inventoryStorage.writeNBT(AdvancedInventoryProvider.ADVANCED_INVENTORY, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {

		AdvancedInventoryStorage.inventoryStorage.readNBT(AdvancedInventoryProvider.ADVANCED_INVENTORY, this, null, compound);
	}
}
