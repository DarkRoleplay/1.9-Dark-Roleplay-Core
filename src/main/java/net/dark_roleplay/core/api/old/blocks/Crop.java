package net.dark_roleplay.core.api.old.blocks;

import java.util.Collections;
import java.util.Random;

import javax.annotation.Nullable;

import net.dark_roleplay.core.api.old.modules.crops.GrowthResult;
import net.dark_roleplay.core.api.old.modules.crops.ICrop;
import net.dark_roleplay.core.api.old.modules.crops.ICropHandler;
import net.dark_roleplay.core.api.old.modules.time.Season;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Crop extends Block implements ICrop, IPlantable{
	
    public static final PropertyInteger AGE_DEFAULT = PropertyInteger.create("age", 0, 15);
	
    protected int maxAge = 10;
    
    protected Season[] seasons;
    
    protected Item crop;
    protected Item seed;
    
    public Crop(int maxAge, Season... seasons) {
		super(Material.PLANTS);
		this.maxAge = maxAge;
		this.seasons = seasons;
		this.setSoundType(SoundType.PLANT);
	}

	protected PropertyInteger getAgeProperty(){
        return AGE_DEFAULT;
    }
	
	public Item getCrop() {
		return crop;
	}

	public Item getSeed() {
		return seed;
	}

	@Override
	public GrowthResult growthUpdate(World world, BlockPos pos, IBlockState state, int age) {
		int maxAge = this.getMaxAge(world, pos, state);
		if(age <= maxAge){
			world.setBlockState(pos, state.withProperty(getAgeProperty(), (int) (((age + 1F)/maxAge) * Collections.max(getAgeProperty().getAllowedValues()) - 1)));
			return GrowthResult.GROWING;
		}else{
			world.setBlockState(pos, state.withProperty(getAgeProperty(), Collections.max(getAgeProperty().getAllowedValues())));
			return GrowthResult.MATURED;
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		 return false;
	}

	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(getAgeProperty(), meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(getAgeProperty());
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {getAgeProperty()});
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	Chunk chunk = world.getChunkFromBlockCoords(pos);
    	if(chunk.hasCapability(DRPCoreCapabilities.CROP_HANDLER, null)){
			ICropHandler instance = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
			instance.addCrop(pos);
    	}
    }

	public int getMaxAge(IBlockAccess world, BlockPos pos, IBlockState state) {
		return this.maxAge;
	}

	@Override
	public Season[] getSeasons(){
		return this.seasons;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return this.getDefaultState();
	}
	
	@Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
        return NULL_AABB;
    } 
	
	@Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		System.out.println("call");
        super.getDrops(drops, world, pos, state, 0);
        int age = getAge(world, pos, state);
        Random rand = world instanceof World ? ((World)world).rand : new Random();

        System.out.println(isMature(state));
        if (isMature(state)){
            int k = 3 + fortune;

            for (int i = 0; i < 3 + fortune; ++i){
                if (rand.nextInt(2 * getMaxAge(world, pos, state)) <= age){
                    drops.add(new ItemStack(this.getSeed(), 1, 0));
                }
            }
        }
    }
	
	public boolean isMature(IBlockState state){
		System.out.println(state.getValue(this.getAgeProperty()) + " --> " + Collections.max(getAgeProperty().getAllowedValues()));
		return state.getValue(this.getAgeProperty()) >= Collections.max(getAgeProperty().getAllowedValues());
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return this.isMature(state) ? this.getCrop() : this.getSeed();
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(this.getSeed());
    }

	@Override
	public int getAge(IBlockAccess world, BlockPos pos, IBlockState state) {
		return state.getValue(this.getAgeProperty());
	}
}
