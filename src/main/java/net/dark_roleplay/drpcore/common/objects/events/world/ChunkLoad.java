package net.dark_roleplay.drpcore.common.objects.events.world;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.dark_roleplay.drpcore.modules.time.Date;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class ChunkLoad {

	@SubscribeEvent
	public static void loadChunk(ChunkDataEvent.Load e){
//		Chunk chunk = e.getChunk();
//		ICropHandler crops = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
//		crops.growCrops(e.getWorld());
	}
	
}
