package net.dark_roleplay.library.items;

import java.util.List;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVariantBlock extends DRPItem {

	protected final Block[] blocks;
	
	private WeakHashMap<EntityPlayer, Integer> selectedVariants;

	public ItemVariantBlock(Block[] blocks, String registryName, int stackSize) {
		super(registryName, stackSize);
		this.blocks = blocks;
		this.selectedVariants = new WeakHashMap<EntityPlayer, Integer>();
	}

	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(world, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack)
				&& world.mayPlace(this.getVariant(player), pos, false, facing, (Entity) null)) {
			int i = this.getMetadata(itemstack.getMetadata());
			IBlockState iblockstate1 = this.getVariant(player).getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, i, player, hand);

			if (placeBlockAt(itemstack, player, world, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
				iblockstate1 = world.getBlockState(pos);
				SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
						(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	public static boolean setTileEntityNBT(World world, @Nullable EntityPlayer player, BlockPos pos, ItemStack stackIn) {
		MinecraftServer minecraftserver = world.getMinecraftServer();

		if (minecraftserver == null) {
			return false;
		} else {
			NBTTagCompound nbttagcompound = stackIn.getSubCompound("BlockEntityTag");

			if (nbttagcompound != null) {
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity != null) {
					if (!world.isRemote && tileentity.onlyOpsCanSetNbt()
							&& (player == null || !player.canUseCommandBlock())) {
						return false;
					}

					NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
					NBTTagCompound nbttagcompound2 = nbttagcompound1.copy();
					nbttagcompound1.merge(nbttagcompound);
					nbttagcompound1.setInteger("x", pos.getX());
					nbttagcompound1.setInteger("y", pos.getY());
					nbttagcompound1.setInteger("z", pos.getZ());

					if (!nbttagcompound1.equals(nbttagcompound2)) {
						tileentity.readFromNBT(nbttagcompound1);
						tileentity.markDirty();
						return true;
					}
				}
			}

			return false;
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		Block block = world.getBlockState(pos).getBlock();

		if (block == Blocks.SNOW_LAYER && block.isReplaceable(world, pos)) {
			side = EnumFacing.UP;
		} else if (!block.isReplaceable(world, pos)) {
			pos = pos.offset(side);
		}

		return world.mayPlace(this.getVariant(player), pos, false, side, (Entity) null);
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
		if (!world.setBlockState(pos, newState, 11))
			return false;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == this.getVariant(player)) {
			setTileEntityNBT(world, player, pos, stack);
			this.getVariant(player).onBlockPlacedBy(world, pos, state, player, stack);

			if (player instanceof EntityPlayerMP)
				CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
		}

		return true;
	}
	
	protected void setVariant(EntityPlayer player, int id) {
		if(this.selectedVariants.containsKey(player)) {
			this.selectedVariants.replace(player, id);
		}else {
			this.selectedVariants.put(player,  id);
		}
	}
	
	protected Block getVariant(EntityPlayer player) {
		if(this.selectedVariants.containsKey(player)) {
			return getVariant(this.selectedVariants.get(player));
		}else {
			this.selectedVariants.put(player,  0);
			return getVariant(0);
		}
	}
	
	protected Block getVariant(int id) {
		if(id >= 0 && id < this.blocks.length) {
			return this.blocks[id];
		}else {
			return this.blocks[0];
		}
	}
}
