package net.dark_roleplay.drpcore.api.blocks;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.modules.crops.ICrop;
import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Crop extends Block implements ICrop{
	
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	
	public Crop() {
		super(Material.PLANTS);
	}

	@Override
	public GrowthResult growthUpdate(World world, BlockPos pos, IBlockState state, int age) {
		if(age <= 30){
			world.setBlockState(pos, state.withProperty(AGE, (int) (((age + 1)/30F) * 14F)));
			return GrowthResult.GROWING;
		}else{
			world.setBlockState(pos, state.withProperty(AGE, 15));
			Chunk chunk = world.getChunkFromBlockCoords(pos);
	    	if(chunk.hasCapability(DRPCoreCapabilities.CROP_HANDLER, null)){
				ICropHandler instance = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
				instance.removeCrop(pos);
	    	}
			return GrowthResult.MATURED;
		}
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {AGE});
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	Chunk chunk = world.getChunkFromBlockCoords(pos);
    	if(chunk.hasCapability(DRPCoreCapabilities.CROP_HANDLER, null)){
			ICropHandler instance = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
			instance.addCrop(pos);
    	}
    }
}
