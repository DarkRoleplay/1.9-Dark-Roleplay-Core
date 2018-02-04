package net.dark_roleplay.drpcore.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Test_Block extends Block{

	public Test_Block() {
		super(Material.CIRCUITS);
	}

	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote)
			return true;
		
		StreetNode node = new StreetNode(pos.add(0, 1, 0), null, 0);
		node.generateAndConnectLinks(new Random(world.getSeed()), world, 5, 15, 20);
		
		return true;
	}
}
