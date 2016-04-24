package net.drpcore.common.items.templates;

import net.minecraft.entity.player.EntityPlayer;

public interface IDRPEquip {

	/**
	 * Called Every Tick when the Item is equiped
	 */
	public void onTick(EntityPlayer player);

	/**
	 * @return The Equipment Type (Necklace or Ring and that stuff)
	 */
	public EnumDRPEquipType getEquipType();

	/**
	 * @return Does this Item have a Storage?
	 * This has a higher Priority than the Equip Type Enum
	 */
	public boolean hasStorage();
	
}
