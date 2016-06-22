package net.drpcore.common.util.schematic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Deprecated
public class Schematic {

	private short width;

	private short height;

	private short length;

	private int totalSize;

	private BlockPos[] positions;

	private IBlockState[] states;

	public BlockPos getPositionWithOffset(BlockPos posA, BlockPos posB) {

		posA = posA.add(posB.getX(), posB.getY(), posB.getZ());
		return posA;
	}

	public Schematic(BlockPos[] positions, IBlockState[] states, short width, short height, short length) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.totalSize = width * height * length;
		this.positions = positions;
		this.states = states;
	}

	public void generate(World world, BlockPos pos) {

		System.out.println("Debug2");
		int current = 0;
		for(BlockPos position : positions) {
			if(states[current].getBlock() != Blocks.air) {
				System.out.println(getPositionWithOffset(position, pos));
			}
			world.setBlockState(getPositionWithOffset(position, pos), states[current]);
			current++ ;
		}
	}
}
