package net.dark_roleplay.drpcore.modules.crops;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICrop {

	public GrowthResult growthUpdate(World world, BlockPos pos, IBlockState state, int age);
	
	public static enum GrowthResult{
		GROWING,
		MATURED,
		SPOILED
	}
}
