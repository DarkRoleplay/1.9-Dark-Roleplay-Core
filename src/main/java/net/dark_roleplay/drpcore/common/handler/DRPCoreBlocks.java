package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.common.objects.blocks.blueprint_controller.BlueprintController;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder("drpcore")
@Mod.EventBusSubscriber
public class DRPCoreBlocks  {
	
	public static final BlueprintController BLUEPRINT_CONTROLLER = null;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Block> event) {
		registerBlocks(
				event.getRegistry(),
			new BlueprintController().setRegistryName("blueprint_controller").setUnlocalizedName("blueprint_controller")
		);
	}
	
	private static final void registerBlocks(IForgeRegistry<Block> registry, Block... blocks){
		for(Block block : blocks){
			registry.register(block);
			DRPCoreItems.addBlockItem((ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
}
