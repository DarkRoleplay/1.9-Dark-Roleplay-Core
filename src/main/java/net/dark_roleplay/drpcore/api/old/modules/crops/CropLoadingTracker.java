package net.dark_roleplay.drpcore.api.old.modules.crops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.modules.time.IDateHandler;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
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
			if(chunksTimer.get(i) == 0){
//				System.out.println("Working trough loaded Chunk");
				Chunk chunk = chunks.get(i);
				ICropHandler crops = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
//				if(crops.crop)
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
