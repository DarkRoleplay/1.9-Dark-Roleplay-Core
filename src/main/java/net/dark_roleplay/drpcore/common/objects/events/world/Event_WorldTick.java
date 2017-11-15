package net.dark_roleplay.drpcore.common.objects.events.world;

import java.util.Collections;
import java.util.Iterator;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldBridge;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class Event_WorldTick {

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.phase == Phase.END && event.side == Side.SERVER) {
			WorldServer world = (WorldServer) event.world;
			if(world.rand.nextInt(32) != 0 || world.getWorldTime() % 24000 > 12000)
				return;
			Iterator<Chunk> iterator = world.getPersistentChunkIterable(world.getPlayerChunkMap().getChunkIterator());
			while (iterator.hasNext()) {
				Chunk chunk = iterator.next();
				if(world.rand.nextInt(16) == 0){
					WorldBridge.setUpdateLCG(world, WorldBridge.getUpdateLCG(world) * 3 + 1013904223);
                    int randOffset = WorldBridge.getUpdateLCG(world) >> 2;
					
					int x = chunk.x << 4;
					int z = chunk.z << 4;

                    BlockPos topPos = chunk.getPrecipitationHeight(new BlockPos(x + (randOffset & 15), 0, z + (randOffset >> 8 & 15)));
					BlockPos groundPos = topPos.down();
	
					if (world.getBlockState(topPos).getBlock() == Blocks.SNOW_LAYER) {
						world.setBlockToAir(topPos);
					}
				}
			}
		}
	}

}
