package net.dark_roleplay.drpcore.common.network.packets.config;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.dark_roleplay.drpcore.common.network.packets.crafting.Packet_InitSimpleRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SyncPacket_Boolean extends PacketBase<SyncPacket_Boolean>{

	private String key;
	
	private boolean value;

	public SyncPacket_Boolean(){
		this.key = null;
		this.value = false;
	}
	
	public SyncPacket_Boolean(String key, boolean value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.value = buf.readBoolean();
		this.key = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.value);
		ByteBufUtils.writeUTF8String(buf, this.key);
	}

	@Override
	public void handleClientSide(SyncPacket_Boolean message, EntityPlayer player) {
		SyncedConfigRegistry.addBooleanValue(message.key, message.value, true);
	}

	@Override
	public void handleServerSide(SyncPacket_Boolean message, EntityPlayer player) {}
}
