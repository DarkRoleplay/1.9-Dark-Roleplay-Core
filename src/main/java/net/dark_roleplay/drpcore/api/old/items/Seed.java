package net.dark_roleplay.drpcore.api.old.items;

import net.dark_roleplay.drpcore.modules.crops.ICrop;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class Seed extends DRPItem implements IPlantable{

	protected Block crop;
	
	public <T extends Block & ICrop> Seed(String name, int stackSize, T block, String... subNames){
		super(name, null, stackSize, subNames);
		this.crop = block;
	}
	
	public <T extends Block & ICrop> Seed(String name, String itemFolder, int stackSize, T block, String... subNames){
		super(name, itemFolder, stackSize, subNames);
		this.crop = block;
	}
	
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())){
            worldIn.setBlockState(pos.up(), this.crop.getDefaultState());
            if(!worldIn.isRemote)
            	this.crop.onBlockPlacedBy(worldIn, pos.up(), state, player, itemstack);
            
            if (player instanceof EntityPlayerMP){
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }else {
            return EnumActionResult.FAIL;
        }
    }

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos){
		return this.crop.getDefaultState();
	}
	
}
