package net.dark_roleplay.core.modules.crops;

import net.dark_roleplay.core.modules.date.Season;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface ICrop {
	
	public GrowthResult growthUpdate(World world, BlockPos pos, IBlockState state, int age);
	
	public int getMaxAge(IBlockAccess world, BlockPos pos, IBlockState state);
	
	public int getAge(IBlockAccess world, BlockPos pos, IBlockState state);
	
	public Season[] getSeasons();
	
	public default boolean canGrow(Season season){
		for(Season season2 : getSeasons()){
			if(season2 == season)
				return true;
		}
		return false;
	}
}
