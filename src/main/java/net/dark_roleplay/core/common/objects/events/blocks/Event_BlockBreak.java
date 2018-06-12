package net.dark_roleplay.core.common.objects.events.blocks;

import net.dark_roleplay.core.common.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_BlockBreak {
	
	@SubscribeEvent
	public static void blockBreak(BlockEvent.BreakEvent event) {
//		SkillHandler handler = event.getPlayer().getCapability(DRPCoreCapabilities.SKILL_HANDLER, null);
//		if(!handler.hasSkill(TEST_1)) {
//			event.setCanceled(true);
//		}
//		IBlockState breaked = event.getState();
//		if (breaked.getBlock() instanceof BlockLog && event.getWorld().getBlockState(event.getPos().down()).getBlock() == Blocks.DIRT) {
//			System.out.println("TREE IS BEEING CUT!");
//			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
//				public void run() {
//					destroyTree(event.getPos(), event.getWorld(), breaked.getBlock(), true);
//				}
//			});
//		}
	}

	private static void destroyTree(BlockPos pos, World world, Block type, boolean isInit) {
		IBlockState state;

		if(!isInit)
			world.destroyBlock(pos, true);
		
		state = world.getBlockState(pos.north());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north(), world, type, false);
		}
		
		state = world.getBlockState(pos.north().east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north().east(), world, type, false);
		}
		
		state = world.getBlockState(pos.east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.east(), world, type, false);
		}
		
		state = world.getBlockState(pos.south().east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south().east(), world, type, false);
		}
		
		state = world.getBlockState(pos.south());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south(), world, type, false);
		}
		
		state = world.getBlockState(pos.south().west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south().west(), world, type, false);
		}
		
		state = world.getBlockState(pos.west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.west(), world, type, false);
		}
		
		state = world.getBlockState(pos.north().west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north().west(), world, type, false);
		}
		
		//1UP
		pos = pos.up();
		
		state = world.getBlockState(pos.north());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north(), world, type, false);
		}
		
		state = world.getBlockState(pos.north().east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north().east(), world, type, false);
		}
		
		state = world.getBlockState(pos.east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.east(), world, type, false);
		}
		
		state = world.getBlockState(pos.south().east());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south().east(), world, type, false);
		}
		
		state = world.getBlockState(pos.south());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south(), world, type, false);
		}
		
		state = world.getBlockState(pos.south().west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.south().west(), world, type, false);
		}
		
		state = world.getBlockState(pos.west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.west(), world, type, false);
		}
		
		state = world.getBlockState(pos.north().west());
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos.north().west(), world, type, false);
		}
		
		state = world.getBlockState(pos);
		if (state.getBlock() instanceof BlockLog) {
			destroyTree(pos, world, type, false);
		}

	}

}