package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.old.blocks.BigBlock;
import net.dark_roleplay.drpcore.api.old.blocks.Crop;
import net.dark_roleplay.drpcore.common.objects.blocks.blueprint_controller.BlueprintController;
import net.dark_roleplay.drpcore.modules.time.Season;
import net.dark_roleplay.drpcore.testing.Test_Block;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
//			new Test_Block().setRegistryName("test_block")
//			new BigBlock().setRegistryName("big_block")
		);
	}
	
	private static final void registerBlocks(IForgeRegistry<Block> registry, Block... blocks){
		for(Block block : blocks){
			registry.register(block);
			DRPCoreItems.addBlockItem((ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}
}
