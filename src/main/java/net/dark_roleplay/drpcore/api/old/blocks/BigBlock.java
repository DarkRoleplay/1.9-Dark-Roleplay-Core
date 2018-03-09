package net.dark_roleplay.drpcore.api.old.blocks;

import net.dark_roleplay.library.blueprints.Blueprint;
import net.dark_roleplay.library.blueprints.BlueprintUtil;
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
		this.blueprint = BlueprintUtil.readFromFile(this.getClass().getResourceAsStream("/assets/drpcore/big_blocks/big_block_test.blueprint"));
		System.out.println(blueprint.getName());
	}

	
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		switch(placer.getHorizontalFacing()){
		case EAST:
			blueprint.build(world, pos, Rotation.CLOCKWISE_90);
			break;
		case NORTH:
			blueprint.build(world, pos, Rotation.NONE);
			break;
		case SOUTH:
			blueprint.build(world, pos, Rotation.CLOCKWISE_180);
			break;
		case WEST:
			blueprint.build(world, pos, Rotation.COUNTERCLOCKWISE_90);
			break;
		default:
			break;
		}
    }
	
	public boolean canPlaceBlockAt(World world, BlockPos pos){
        if(world.getBlockState(pos).getBlock().isReplaceable(world, pos)){
        	return blueprint.canBuild(world, pos);
        }
        return false;
    }
	
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		blueprint.destroy(world, pos);
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
}
