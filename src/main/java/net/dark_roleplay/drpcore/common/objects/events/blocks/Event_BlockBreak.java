package net.dark_roleplay.drpcore.common.objects.events.blocks;

import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.common.capabilities.CapabilityProvider;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_BlockBreak {

	@SubscribeEvent
	public void blockBreak(BlockEvent.BreakEvent event) {
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

	private void destroyTree(BlockPos pos, World world, Block type, boolean isInit) {
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
