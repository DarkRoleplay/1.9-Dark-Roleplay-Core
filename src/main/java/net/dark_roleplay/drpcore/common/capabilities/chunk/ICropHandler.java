package net.dark_roleplay.drpcore.common.capabilities.chunk;

import net.minecraft.util.math.BlockPos;

public interface ICropHandler {

	public void addCrop(BlockPos pos);
	
	public void removeCrop(BlockPos pos);
	
	public int getLastDay(BlockPos pos);
	
}
