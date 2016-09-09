package net.drpcore.api.items.equip;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;


public class PurseBase extends Item {

	public int SlotAmount = 3;

	public PurseBase(int slotAmount) {
		this.SlotAmount = slotAmount;
	}

	public void openPurse(World world, EntityPlayer player) {

		player.openGui(DarkRoleplayCore.instance, GuiHandler.GUI_PURSE, world, 0, 0, 0);
	}
}
