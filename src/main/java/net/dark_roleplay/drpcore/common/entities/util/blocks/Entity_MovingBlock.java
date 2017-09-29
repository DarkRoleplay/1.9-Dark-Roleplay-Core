package net.dark_roleplay.drpcore.common.entities.util.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Entity_MovingBlock{// extends Entity{
//
//	private IBlockState movingBlock;
//	private NBTTagCompound tileEntityData;
//	
//	private BlockPos targetPos;
//	
//	private int moveTime = 0;
//	private boolean shouldDropItem = true;
//    private boolean setBlock = true;
//    private double speed = 4.0D;
//	
//	public Entity_MovingBlock(World worldIn) {
//		super(worldIn);
//	}
//
//	@Override
//	protected void entityInit() {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	public void onUpdate(){
//
//        if (this.movingBlock.getMaterial() == Material.AIR){
//            this.setDead();
//        }else{
//            this.prevPosX = this.posX;
//            this.prevPosY = this.posY;
//            this.prevPosZ = this.posZ;
//
//            if (this.moveTime++ == 0){
//                BlockPos blockpos = new BlockPos(this);
//
//                if (this.world.getBlockState(blockpos).getBlock() == this.movingBlock.getBlock()){
//                    this.world.setBlockToAir(blockpos);
//                }
//            }
//            
//            double x1 = this.posX, y1 = this.posY, z1 = this.posZ;
//            int x2 = this.targetPos.getX(), y2 = this.targetPos.getY(), z2 = this.targetPos.getZ();
//            
//            double totalDistance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2));
//            
//            if(this.speed < totalDistance){
//                double step = this.speed / totalDistance;
//                this.motionX = x1 + ((x2 - x1) * step);
//                this.motionX = y1 + ((y2 - y1) * step);
//                this.motionX = z1 + ((z2 - z1) * step);
//            }else{
//            	this.motionX = x1 + (x2 - x1);
//                this.motionX = y1 + (y2 - y1);
//                this.motionX = z1 + (z2 - z1);
//            }
//            
//            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//
//            if (!this.world.isRemote){
//                BlockPos blockpos1 = new BlockPos(this);
//                boolean flag1 = this.world.getBlockState(blockpos1).getMaterial() == Material.WATER;
//                double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
//
//                
//
//                if (!this.onGround && !flag1)
//                {
//                    if (this.moveTime > 100 && !this.world.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.moveTime > 600)
//                    {
//                        if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
//                        {
//                            this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
//                        }
//
//                        this.setDead();
//                    }
//                }
//                else
//                {
//                    IBlockState iblockstate = this.world.getBlockState(blockpos1);
//
//                    if (this.world.isAirBlock(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))) //Forge: Don't indent below.
//                    if (!flag1 && BlockFalling.canFallThrough(this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ))))
//                    {
//                        this.onGround = false;
//                        return;
//                    }
//
//                    this.motionX *= 0.699999988079071D;
//                    this.motionZ *= 0.699999988079071D;
//                    this.motionY *= -0.5D;
//
//                    if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION)
//                    {
//                        this.setDead();
//
//                        if (!this.dontSetBlock)
//                        {
//                            if (this.world.mayPlace(block, blockpos1, true, EnumFacing.UP, (Entity)null) && (flag1 || !BlockFalling.canFallThrough(this.world.getBlockState(blockpos1.down()))) && this.world.setBlockState(blockpos1, this.fallTile, 3))
//                            {
//                                if (block instanceof BlockFalling)
//                                {
//                                    ((BlockFalling)block).onEndFalling(this.world, blockpos1, this.fallTile, iblockstate);
//                                }
//
//                                if (this.tileEntityData != null && block.hasTileEntity(this.fallTile))
//                                {
//                                    TileEntity tileentity = this.world.getTileEntity(blockpos1);
//
//                                    if (tileentity != null)
//                                    {
//                                        NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
//
//                                        for (String s : this.tileEntityData.getKeySet())
//                                        {
//                                            NBTBase nbtbase = this.tileEntityData.getTag(s);
//
//                                            if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s))
//                                            {
//                                                nbttagcompound.setTag(s, nbtbase.copy());
//                                            }
//                                        }
//
//                                        tileentity.readFromNBT(nbttagcompound);
//                                        tileentity.markDirty();
//                                    }
//                                }
//                            }
//                            else if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
//                            {
//                                this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
//                            }
//                        }
//                        else if (block instanceof BlockFalling)
//                        {
//                            ((BlockFalling)block).onBroken(this.world, blockpos1);
//                        }
//                    }
//                }
//            }
//
//            this.motionX *= 0.9800000190734863D;
//            this.motionY *= 0.9800000190734863D;
//            this.motionZ *= 0.9800000190734863D;
//        }
//    }
//
//	@Override
//	protected void readEntityFromNBT(NBTTagCompound compound) {
//	    NBTTagCompound blockTag = (NBTTagCompound) compound.getTag("block");
//	    this.movingBlock = NBTUtil.readBlockState(blockTag);
//	
//	    if(compound.hasKey("tile_entity_data")){
//	    	this.tileEntityData = (NBTTagCompound) compound.getTag("tile_entity_data");
//	    }
//	    
//	    this.moveTime = compound.getInteger("time");
//	    this.shouldDropItem = compound.getBoolean("drop_item");
//	    this.setBlock = compound.getBoolean("set_block");
//	}
//
//	@Override
//	protected void writeEntityToNBT(NBTTagCompound compound) {
//		if(this.movingBlock == null){
//			this.movingBlock = Blocks.AIR.getDefaultState();
//		}
//        NBTTagCompound blockTag = NBTUtil.writeBlockState(new NBTTagCompound(), this.movingBlock);
//        compound.setTag("block", blockTag);
//
//        if (this.tileEntityData != null){
//            compound.setTag("tile_entity_data", this.tileEntityData);
//        }
//        
//        compound.setInteger("time", this.moveTime);
//        compound.setBoolean("drop_item", this.shouldDropItem);
//        compound.setBoolean("set_block", this.setBlock);
//    }		
//	
//	@Override
//	public boolean canBeAttackedWithItem(){
//        return false;
//    }
//	
//	@Override
//	public boolean canBeCollidedWith(){
//        return !this.isDead;
//    }
//	
//	@Override
//	protected boolean canTriggerWalking(){
//        return false;
//    }
}