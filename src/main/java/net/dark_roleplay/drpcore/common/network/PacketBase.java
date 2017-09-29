package net.dark_roleplay.drpcore.common.network;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class PacketBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ> {

	@Override
	public REQ onMessage(REQ message, MessageContext ctx) {

		if(ctx.side == Side.SERVER) {
			handleServerSide(message, ctx.getServerHandler().player);
		} else {
			handleClientSide(message, null);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public abstract void handleClientSide(REQ message, EntityPlayer player);

	public abstract void handleServerSide(REQ message, EntityPlayer player);
	
	public static abstract class Server<REQ extends IMessage> extends PacketBase<REQ>{
		@Override
		@SideOnly(Side.CLIENT)
		public void handleClientSide(REQ message, EntityPlayer player){
			DRPCoreInfo.LOGGER.error("Received a packet that was ment to be only used to send Data form Client to Server!");
			DRPCoreInfo.LOGGER.error("False Packet: "  + message.getClass().getSimpleName());
		}
	}
	
	public static abstract class Client<REQ extends IMessage> extends PacketBase<REQ>{
		@Override
		public void handleServerSide(REQ message, EntityPlayer player){
			DRPCoreInfo.LOGGER.error("Received a packet that was ment to be only used to send Data form Server to Client!");
			DRPCoreInfo.LOGGER.error("False Packet: "  + message.getClass().getSimpleName());
		}
	}
}
