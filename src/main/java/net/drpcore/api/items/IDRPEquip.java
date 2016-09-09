package net.drpcore.api.items;

import net.minecraft.entity.player.EntityPlayer;


public interface IDRPEquip {

	/**
	 * @return The Equipment Type (Necklace or Ring and that stuff)
	 */
	public EnumDRPEquipType getEquipType();
}
