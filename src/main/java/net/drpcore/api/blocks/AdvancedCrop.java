package net.drpcore.api.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdvancedCrop extends AdvancedPlant {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);;
	
    private Block soil;
    
	public AdvancedCrop(String registryName, int daysTillGrown) {
		super(registryName, daysTillGrown);
	}
	
	public AdvancedCrop(String registryName, String unlocalizedName, int daysTillGrown) {
		super(registryName, unlocalizedName, daysTillGrown);
	}
	
	public AdvancedCrop(String registryName, String unlocalizedName, int daysTillGrown, Item seed, Item crop) {
		super(registryName, unlocalizedName, daysTillGrown);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(IS_CROP_CONTROLLER, false));
        this.setCreativeTab((CreativeTabs)null);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
	}
	
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state){
        IBlockState soil = worldIn.getBlockState(pos.down());
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && (soil.getBlock() == this.getSoil());
    }

	protected Block getSoil(){
		return Blocks.FARMLAND;
	}
	
    protected Item getSeed(){
        return null;
    }

    protected Item getCrop(){
        return null;
    }
	
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {AGE,IS_CROP_CONTROLLER});
    }
    
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    public int getMaxAge(){
        return 7;
    }

    protected int getAge(IBlockState state){
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }

    public IBlockState withAge(int age){
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }

    public boolean isMaxAge(IBlockState state){
        return ((Integer)state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
    }
    
    public IBlockState getStateFromMeta(int meta){
    	if(meta < 8)
    		return this.getDefaultState().withProperty(AGE,meta);
    	else
    		return super.getStateFromMeta(meta).withProperty(AGE, meta - 8);
    }

    public int getMetaFromState(IBlockState state){
        return super.getMetaFromState(state) + state.getValue(AGE);
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(this.getSeed());
    }
    
    @Override
    public java.util.List<ItemStack> getDrops(net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        java.util.List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
        int age = getAge(state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        if (age >= getMaxAge()){
            int k = 3 + fortune;
            for (int i = 0; i < 3 + fortune; ++i){
                if (rand.nextInt(2 * getMaxAge()) <= age){
                    ret.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
        return ret;
    }
    
    @Override
	public void grow(World world, int day, BlockPos pos, IBlockState state) {
    	if(day / (daysTillGrown / getMaxAge()) < this.getMaxAge())
    	world.setBlockState(pos, state.withProperty(AGE, day / (daysTillGrown / getMaxAge())));
    }

}
