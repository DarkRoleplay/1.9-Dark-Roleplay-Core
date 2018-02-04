package net.dark_roleplay.drpcore.modules.crops;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.modules.time.Date;
import net.dark_roleplay.drpcore.modules.time.Season;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface ICrop {
	
	public GrowthResult growthUpdate(World world, BlockPos pos, IBlockState state, int age);
	
	public int getMaxAge(World world, BlockPos pos, IBlockState state);
	
	public Season[] getSeasons();
	
	public default boolean canGrow(Season season){
		for(Season season2 : getSeasons()){
			if(season2 == season)
				return true;
		}
		return false;
	}
}
