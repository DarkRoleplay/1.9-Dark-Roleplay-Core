package net.dark_roleplay.library.blocks;

import java.util.Random;

import net.dark_roleplay.library.blocks.behaviours.IBlockActivated;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author JTK222
 */
public class DRPBlock extends Block{
	
	/**Behaviours**/
	private IBlockActivated onActivatedBehaviour = null;

	public DRPBlock(String name, BlockSettings settings) {
		super(settings.getMaterial(), settings.getMapColor());
		this.setSoundType(settings.getSoundType());
		this.setHardness(settings.getHardness());
		this.setResistance(settings.getBlastResistance());
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(onActivatedBehaviour != null)
			return this.onActivatedBehaviour.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
		return false;
	}
	
	 public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	    {
	        this.updateTick(worldIn, pos, state, random);
	    }

	    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	    {
	    }

	    @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
	    }

	    /**
	     * Called after a player destroys this Block - the posiiton pos may no longer hold the state indicated.
	     */
	    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	    {
	    }
	    
	    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	    {
	    }

	    /**
	     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
	     */
	    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
	        if (hasTileEntity(state))
	        {
	            worldIn.removeTileEntity(pos);
	        }
	    }
	    
	    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	    {
	    }
	    
	    /**
	     * Check whether this Block can be placed at pos, while aiming at the specified side of an adjacent block
	     */
	    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	    {
	        return this.canPlaceBlockAt(worldIn, pos);
	    }

	    /**
	     * Checks if this block can be placed exactly at the given position.
	     */
	    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	    {
	        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
	    }
	    
	    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	    {
	    }
	    
	    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	    {
	    }
	    
	    /**
	     * Called When an Entity Collided with the Block
	     */
	    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	    {
	    }
	    
	    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	    {
	    }
	    
	    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	    {
	        entityIn.fall(fallDistance, 1.0F);
	    }
	    
	    public void onLanded(World worldIn, Entity entityIn)
	    {
	        entityIn.motionY = 0.0D;
	    }
	    
	    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	    {
	    }

	    /**
	     * Called similar to random ticks, but only when it is raining.
	     */
	    public void fillWithRain(World worldIn, BlockPos pos)
	    {
	    }
	

	/**
	 * Sets the Behaviour that's being called when the Block is Activated
	 * @param behaviour
	 * @return itself
	 */
	public DRPBlock setACtivationBehaviour(IBlockActivated behaviour) {
		this.onActivatedBehaviour = behaviour;
		return this;
	}
}
