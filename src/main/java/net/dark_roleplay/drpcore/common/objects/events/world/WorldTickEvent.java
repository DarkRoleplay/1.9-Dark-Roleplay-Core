package net.dark_roleplay.drpcore.common.objects.events.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMultiset;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.dark_roleplay.drpcore.modules.time.IDateHandler;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

@EventBusSubscriber
public class WorldTickEvent {

	private static Map<DimensionType, Integer> lastTick = new HashMap<DimensionType, Integer>();
	
	@SubscribeEvent
	public static void serverTick(TickEvent.WorldTickEvent e){
		if(e.phase == Phase.END && e.side.isServer()){
			World world = e.world;
			if(world.provider.getDimensionType() == DimensionType.OVERWORLD){
				int lastTick;
				if(!WorldTickEvent.lastTick.containsKey(world.provider.getDimensionType())){
					WorldTickEvent.lastTick.put(world.provider.getDimensionType(), 0);
					lastTick = 0;
				}else{
					lastTick = WorldTickEvent.lastTick.get(world.provider.getDimensionType()) + 1;
				}
				if(lastTick >= 100){
					lastTick = 0;
					
					IDateHandler date = world.getCapability(DRPCoreCapabilities.DATE_HANDLER, null);

					if(date.attemptIncrease((world.getWorldTime() + 6000L) % 24000L)){
						System.out.println("Date Increased!");
						Collection<Chunk> chunks = ((ChunkProviderServer) world.getChunkProvider()).getLoadedChunks();
						for(Chunk chunk : chunks){
							ICropHandler crops = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
							crops.growCrops(world);
						}
					}
				}
				WorldTickEvent.lastTick.replace(world.provider.getDimensionType(), lastTick);
			}
		}
	}
}
