package net.drpcore.api.blocks;

import java.util.HashMap;
import java.util.Random;

import net.drpcore.common.blocks.tileentities.AdvancedPlantTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AdvancedPlant extends Block implements ITileEntityProvider {

    public static final PropertyBool IS_CROP_CONTROLLER = PropertyBool.create("is_crop_controller");
	
	public AdvancedPlant(String registryName, int daysTillGrown){
		this(registryName, registryName, daysTillGrown);
        this.setDefaultState(this.blockState.getBaseState().withProperty(IS_CROP_CONTROLLER, false));
	}
	
	protected int daysTillGrown;
	
	public AdvancedPlant(String registryName, String ulocalizedName, int daysTillGrown){
		super(Material.PLANTS);
		this.setUnlocalizedName(ulocalizedName);
        this.setRegistryName(registryName);
        this.daysTillGrown = daysTillGrown;
	}

    private AdvancedPlantTileEntity getTE(World world, BlockPos pos) {
        return (AdvancedPlantTileEntity) world.getTileEntity(pos);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta > 7){
			return new AdvancedPlantTileEntity();
		}else{
			return null;
		}
	}
	
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer (this, IS_CROP_CONTROLLER);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
    	if(state.getValue(IS_CROP_CONTROLLER)){
    		return 8;
    	}
        return 0;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(IS_CROP_CONTROLLER, meta > 7 ? true:false);
    }

	public void grow(World worldIn, int day, BlockPos pos, IBlockState state) {}

	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
                int counter;
                if(getTE(world, pos) != null){
                	HashMap<BlockPos, Integer> crops = getTE(world,pos).getCrops();
                	for(BlockPos tmpPos : crops.keySet()){
                		player.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + tmpPos.toString() + " : " + crops.get(tmpPos)));
                	}
                }
                	//player.addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "DEBUG"));
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	for(int i = -5; i <= 5; i++){
    		for(int j = -5; j <= 5; j++){
    			BlockPos tmpPos = pos.add(i,-1,j);
    			for(int k = 0; k < 3; k++){
    				if(world.getBlockState(tmpPos).getBlock() instanceof AdvancedPlant){
    					if(world.getBlockState(tmpPos).getValue(IS_CROP_CONTROLLER)){
    						addBlock(world, state, pos, tmpPos);
    						return;
    					}
    				}
    				tmpPos = tmpPos.add(0,1,0);
    			}
        	}
    	}
    	world.setBlockState(pos, state.withProperty(IS_CROP_CONTROLLER, true), 2);
		((AdvancedPlantTileEntity) world.getTileEntity(pos)).addCrop(pos);
		((AdvancedPlantTileEntity) world.getTileEntity(pos)).setDay((int)(world.getWorldTime() / 24000L % 2147483647L));;
    }	
    
    private void addBlock(World world, IBlockState state, BlockPos pos, BlockPos tePos){
		((AdvancedPlantTileEntity) world.getTileEntity(tePos)).addCrop(pos);
		world.setBlockState(pos, state.withProperty(IS_CROP_CONTROLLER, false), 2);
    }
}
