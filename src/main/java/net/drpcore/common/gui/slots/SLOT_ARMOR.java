package net.drpcore.common.gui.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SLOT_ARMOR extends Slot {

	private EntityEquipmentSlot armorType = null;

	final EntityPlayer player;

	public SLOT_ARMOR(EntityPlayer player, IInventory inventory, int slotID, int posX, int posY, int armorType) {
		super(inventory, slotID, posX, posY);
		this.player = player;

		switch (armorType) {
			case 0:
				this.armorType = EntityEquipmentSlot.HEAD;
				break;
			case 1:
				this.armorType = EntityEquipmentSlot.CHEST;
				break;
			case 2:
				this.armorType = EntityEquipmentSlot.LEGS;
				break;
			case 3:
				this.armorType = EntityEquipmentSlot.FEET;
				break;
		}

	}

	public int getSlotStackLimit() {

		return 1;
	}

	public boolean isItemValid(ItemStack itemstack) {

		return true;

		// ItemArmor item = (itemstack == null ? null : itemstack.getItem()
		// instanceof ItemArmor ? (ItemArmor) itemstack.getItem() : null);

		// EntityEquipmentSlot type = item.getEquipmentSlot();

		// return item != null && item.isValidArmor(itemstack, type, player) &&
		// type == armorType;
	}

	@SideOnly(Side.CLIENT)
	public String getSlotTexture() {

		switch (armorType) {
			case HEAD:
				return "minecraft:items/empty_armor_slot_helmet";
			case CHEST:
				return "minecraft:items/empty_armor_slot_chestplate";
			case LEGS:
				return "minecraft:items/empty_armor_slot_leggings";
			case FEET:
				return "minecraft:items/empty_armor_slot_boots";
			case MAINHAND:
				break;
			case OFFHAND:
				break;
			default:
				break;
		}

		return "";

	}

}
