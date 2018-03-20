package net.dark_roleplay.drpcore.common.network.packets.chunks;

import java.util.concurrent.Callable;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.old.modules.locks.ILockHandler;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SyncPacket_LockHandler extends PacketBase.Client<SyncPacket_LockHandler>{

	private ChunkPos pos;
	private NBTTagCompound tag;
	
	public SyncPacket_LockHandler(){
		
	}
	
	public SyncPacket_LockHandler(ChunkPos pos, ILockHandler instance){
		this.pos = pos;
		this.tag = instance.serializeNBT();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new ChunkPos(buf.readInt(), buf.readInt());
		this.tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.pos.x);
		buf.writeInt(this.pos.z);
		ByteBufUtils.writeTag(buf, this.tag);
	}

	@Override
	public void handleClientSide(SyncPacket_LockHandler message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			World world = Minecraft.getMinecraft().player.getEntityWorld();
			Chunk chunk = world.getChunkFromChunkCoords(message.pos.x, message.pos.z);
			ILockHandler locks = chunk.getCapability(DRPCoreCapabilities.LOCK_HANDLER, null);
			locks.deserializeNBT(message.tag);
		});
	}

}
