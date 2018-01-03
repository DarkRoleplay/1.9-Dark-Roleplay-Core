package net.dark_roleplay.drpcore.api.blocks;

import net.dark_roleplay.drpcore.api.blueprints.Blueprint;
import net.dark_roleplay.drpcore.api.blueprints.BlueprintUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BigBlock extends Block{

	private Blueprint blueprint;
	
	public BigBlock() {
		super(Material.CAKE);
		this.blueprint = BlueprintUtil.readFromFile(BigBlock.class.getResourceAsStream("big_block_test.blueprint"));
		System.out.println(blueprint.getName());
	}

	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){

		blueprint.build(world, pos);
//		blueprint.build(world, pos.add(0, 4, 0), Rotation.CLOCKWISE_90);
//		blueprint.build(world, pos.add(0, 8, 0), Rotation.CLOCKWISE_180);
//		blueprint.build(world, pos.add(0, 12, 0), Rotation.COUNTERCLOCKWISE_90);
    }
	
	public boolean canPlaceBlockAt(World world, BlockPos pos){
        if(world.getBlockState(pos).getBlock().isReplaceable(world, pos)){
        	return blueprint.canBuild(world, pos);
        }
        return false;
    }
	
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		blueprint.remove(world, pos);
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
}
