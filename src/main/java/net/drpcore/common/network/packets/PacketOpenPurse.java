package net.drpcore.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.items.templates.BackpackBase;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.network.PacketBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;


public class PacketOpenPurse extends PacketBase<PacketOpenPurse> {

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleClientSide(PacketOpenPurse message, EntityPlayer player) {}

	@Override
	public void handleServerSide(PacketOpenPurse message, EntityPlayer player) {

		AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
		if(inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE) != null && inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE).getItem() instanceof PurseBase) {
			ItemStack purse = inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE);
			((PurseBase) purse.getItem()).openPurse(player.worldObj, player);
		}
	}
}
