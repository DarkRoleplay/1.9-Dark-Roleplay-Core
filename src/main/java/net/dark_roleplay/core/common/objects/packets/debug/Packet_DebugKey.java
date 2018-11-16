package net.dark_roleplay.core.common.objects.packets.debug;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.core.DarkRoleplayCore;
import net.dark_roleplay.core.common.handler.DRPCoreGuis;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.entity.player.EntityPlayer;

public class Packet_DebugKey extends PacketBase.Server<Packet_DebugKey>{

	public Packet_DebugKey(){}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleServerSide(Packet_DebugKey message, EntityPlayer player) {

		player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.EXTENDED_INVENTORY, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

//		player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_EXTENDED_INVENTORY, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
	}
}