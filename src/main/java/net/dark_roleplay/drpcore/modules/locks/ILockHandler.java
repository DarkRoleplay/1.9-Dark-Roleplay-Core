package net.dark_roleplay.drpcore.modules.locks;

import java.util.HashMap;
import java.util.Map.Entry;

import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemHandlerHelper;

public interface ILockHandler extends INBTSerializable<NBTTagCompound>{

	public ItemStack insertLock(BlockPos pos, ItemStack stack);
	
	public boolean isLocked(BlockPos pos);
	
	public ItemStack extractLock(BlockPos pos);
	
	public boolean canOpen(BlockPos pos, ItemStack stack);
	
	public boolean hasLocks();
	
	public static class Impl implements ILockHandler{

		protected HashMap<BlockPos, ItemStack> locks = new HashMap<BlockPos, ItemStack>();
		
		@Override
		public ItemStack insertLock(BlockPos pos, ItemStack stack) {
			if(locks.containsKey(pos))
				return stack;
			else{
				if(!(stack.getItem() instanceof ILock))
					return stack;
				locks.put(pos, ItemHandlerHelper.copyStackWithSize(stack, 1));
				return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - 1);
			}
		}

		@Override
		public boolean isLocked(BlockPos pos) {
			return locks.containsKey(pos);
		}

		@Override
		public ItemStack extractLock(BlockPos pos) {
			if(locks.containsKey(pos)){
				ItemStack lock = locks.get(pos);
				locks.remove(pos);
				return lock;
			}else{
				return ItemStack.EMPTY;
			}
		}

		@Override
		public boolean canOpen(BlockPos pos, ItemStack stack) {
			if(locks.containsKey(pos) && (stack.getItem() instanceof ILock) && ((ILock)stack.getItem()).getType(stack) == ILock.TYPE.KEY){
				ItemStack lockStack = locks.get(pos);
				return true;
			}
			return false;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			if(this.locks.isEmpty()) return new NBTTagCompound();
			
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagList locks = new NBTTagList();
			
			for(Entry<BlockPos, ItemStack> entry: this.locks.entrySet()){
				locks.appendTag(NBTUtil.createPosTag(entry.getKey()));
				locks.appendTag(entry.getValue().serializeNBT());
			}
			
			tag.setTag("locks", locks);
			return tag;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			this.locks = new HashMap<BlockPos, ItemStack>();
			if(nbt == null || !nbt.hasKey("locks")) return;
			
			
			NBTTagList locks = nbt.getTagList("locks", NBT.TAG_COMPOUND);
			for(int i = 0; i < locks.tagCount(); i += 2){
				this.locks.put(NBTUtil.getPosFromTag(locks.getCompoundTagAt(i)), new ItemStack(locks.getCompoundTagAt(i+1)));
			}			
		}

		@Override
		public boolean hasLocks() {
			return !locks.isEmpty();
		}
		
	}
}
