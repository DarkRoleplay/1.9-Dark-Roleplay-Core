package net.drpcore.common.network.packets;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.network.PacketBase;
import net.minecraft.entity.player.EntityPlayer;

public class PacketOpenCraftingGui extends PacketBase<PacketOpenCraftingGui> {

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleClientSide(PacketOpenCraftingGui message, EntityPlayer player) {

	}

	@Override
	public void handleServerSide(PacketOpenCraftingGui message, EntityPlayer player) {

		player.openGui(DarkRoleplayCore.instance, GuiHandler.GUI_CRAFTING, player.worldObj, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		// player.addStat(Achievements.DrpOpenCraft, 1);
	}

}
