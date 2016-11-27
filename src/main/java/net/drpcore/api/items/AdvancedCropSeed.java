package net.drpcore.api.items;

import java.util.List;

import javax.annotation.Nullable;

import net.drpcore.api.blocks.AdvancedCrop;
import net.drpcore.api.blocks.AdvancedPlant;
import net.drpcore.common.blocks.tileentities.AdvancedPlantTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdvancedCropSeed extends Item implements net.minecraftforge.common.IPlantable{
    private final Block crops;
    /** BlockID of the block the seeds can be planted on. */
    private final Block soilBlockID;

    public AdvancedCropSeed(Block crops, Block soil)
    {
        this.crops = crops;
        this.soilBlockID = soil;
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
    	net.minecraft.block.state.IBlockState stateTMP = world.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && stateTMP.getBlock().canSustainPlant(stateTMP, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())){      
    	
	    	pos = pos.add(0,1,0);
	        IBlockState state = crops.getDefaultState();
	    	
	        for(int i = -5; i <= 5; i++){
	    		for(int j = -5; j <= 5; j++){
	    			BlockPos tmpPos = pos.add(i,-1,j);
	    			for(int k = 0; k < 3; k++){
	    				if(world.getBlockState(tmpPos).getBlock() instanceof AdvancedPlant){
	    					if(world.getBlockState(tmpPos).getValue(AdvancedPlant.IS_CROP_CONTROLLER)){
	    						addBlock(world, state, pos, tmpPos);
	    						--stack.stackSize;
	    						return EnumActionResult.SUCCESS;
	    					}
	    				}
	    				tmpPos = tmpPos.add(0,1,0);
	    			}
	        	}
	    	}
	    	world.setBlockState(pos, state.withProperty(AdvancedPlant.IS_CROP_CONTROLLER, true), 2);
			((AdvancedPlantTileEntity) world.getTileEntity(pos)).addCrop(pos);
			((AdvancedPlantTileEntity) world.getTileEntity(pos)).setDay((int)(world.getWorldTime() / 24000L % 2147483647L));
			--stack.stackSize;
			return EnumActionResult.SUCCESS;
		
        }else{
            return EnumActionResult.FAIL;
        }       
    }

    private void addBlock(World world, IBlockState state, BlockPos pos, BlockPos tePos){
		((AdvancedPlantTileEntity) world.getTileEntity(tePos)).addCrop(pos);
		world.setBlockState(pos, state.withProperty(AdvancedPlant.IS_CROP_CONTROLLER, false), 2);
    }
    
    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos){
        return this.crops == net.minecraft.init.Blocks.NETHER_WART ? net.minecraftforge.common.EnumPlantType.Nether : net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public net.minecraft.block.state.IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos){
        return this.crops.getDefaultState();
    }
}