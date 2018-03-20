package net.dark_roleplay.drpcore.common.objects.events.world;

import net.dark_roleplay.drpcore.api.old.modules.crops.CropLoadingTracker;
import net.dark_roleplay.drpcore.api.old.modules.locks.ILockHandler;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.chunks.SyncPacket_LockHandler;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ChunkLoad {

	@SubscribeEvent
	public static void loadChunk(ChunkDataEvent.Load e){
		Chunk chunk = e.getChunk();
		CropLoadingTracker.addChunk(chunk);
	}
	
	@SubscribeEvent
	public static void watchChunk(ChunkWatchEvent.Watch e){
		Chunk chunk = e.getPlayer().getEntityWorld().getChunkFromChunkCoords(e.getChunk().x, e.getChunk().z);
		ILockHandler handler = chunk.getCapability(DRPCoreCapabilities.LOCK_HANDLER, null);
		if(handler.hasLocks())
			DRPCorePackets.sendTo(new SyncPacket_LockHandler(e.getChunk(), handler), e.getPlayer());
	}
}
