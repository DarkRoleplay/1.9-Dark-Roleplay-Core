package net.dark_roleplay.drpcore.common.events;

import com.google.common.collect.ImmutableList;

import net.dark_roleplay.drpcore.common.handler.DRPCoreBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MissingMappings {

	@SubscribeEvent
	public static void MissingMappingsBlock(RegistryEvent.MissingMappings<Block> event){
		ImmutableList<Mapping<Block>> mappings = event.getAllMappings();
		for(RegistryEvent.MissingMappings.Mapping mapping : mappings){
			String name = mapping.key.toString();
			if(name.equals("drpcore:schematic_controller")){
				mapping.remap(DRPCoreBlocks.schem);
			}
		}
	}
	
}
