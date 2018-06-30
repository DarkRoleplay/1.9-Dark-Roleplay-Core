package net.dark_roleplay.core.modules.crops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.core.modules.date.capabilities.IDateHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class CropLoadingTracker {

	private static List<Chunk> chunks = new ArrayList<Chunk>();
	private static List<Integer> chunksTimer = new ArrayList<Integer>();
	
	
	public static void addChunk(Chunk chunk){
		chunks.add(chunk);
		chunksTimer.add(10);
	}
	
	public static void workTroughChunks(World world){
		for(int i = 0; i < chunks.size(); i++){
			Chunk chunk = chunks.get(i);
			if(!chunk.isLoaded()) {
				chunks.remove(i);
				chunksTimer.remove(i);
				i--;
				continue;
			}

			if(chunksTimer.get(i) == 0){
//				System.out.println("Working trough loaded Chunk");
				
				ICropHandler crops = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
				crops.growCrops(world);
				chunks.remove(i);
				chunksTimer.remove(i);
				i--;
			}else{
				chunksTimer.set(i, chunksTimer.get(i) - 1);
			}
		}
	}
}
