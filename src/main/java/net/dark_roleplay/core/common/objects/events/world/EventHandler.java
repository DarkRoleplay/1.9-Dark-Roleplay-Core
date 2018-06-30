package net.dark_roleplay.core.common.objects.events.world;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.modules.crops.CropLoadingTracker;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = References.MODID)
public class EventHandler {

	@SubscribeEvent
	public static void loadChunk(ChunkDataEvent.Load e){
		Chunk chunk = e.getChunk();
		CropLoadingTracker.addChunk(chunk);
	}
}
