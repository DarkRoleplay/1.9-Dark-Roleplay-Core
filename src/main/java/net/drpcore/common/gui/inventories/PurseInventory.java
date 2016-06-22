package net.drpcore.common.gui.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;


public class PurseInventory implements IInventory {

	private String name = "Leather Purse";

	/** Provides NBT Tag Compound to reference */
	private final ItemStack invItem;

	public int MAX_INV_SIZE = 9;

	public int INV_SIZE = 3;

	private ItemStack[] inventory = null;

	/**
	 * @param itemstack
	 *            - the ItemStack to which this inventory belongs
	 */
	public PurseInventory(ItemStack stack, int INV_SIZE) {
		invItem = stack;
		if(INV_SIZE <= this.MAX_INV_SIZE)
			this.INV_SIZE = INV_SIZE;
		else
			this.INV_SIZE = this.MAX_INV_SIZE;
		this.inventory = new ItemStack[this.INV_SIZE];
		if( ! stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		readFromNBT(stack.getTagCompound());
	}

	@Override
	public int getSizeInventory() {

		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		ItemStack stack = getStackInSlot(slot);
		if(stack != null) {
			if(stack.stackSize > amount) {
				stack = stack.splitStack(amount);
				markDirty();
			} else {
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	/*
	 * @Override public ItemStack getStackInSlotOnClosing(int slot) { ItemStack
	 * stack = getStackInSlot(slot); setInventorySlotContents(slot, null);
	 * return stack; }
	 */
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		inventory[slot] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public boolean hasCustomName() {

		return name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {

		return 100;
	}

	@Override
	public void markDirty() {

		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory[i] = null;
			}
		}
		writeToNBT(invItem.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {

		return true;
	}

	/**
	 * A custom method to read our inventory from an ItemStack's NBT compound
	 */
	public void readFromNBT(NBTTagCompound compound) {

		NBTTagList items = compound.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < items.tagCount(); ++i) {
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");
			if(slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public void writeToNBT(NBTTagCompound tagcompound) {

		NBTTagList items = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); ++i) {
			if(getStackInSlot(i) != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		tagcompound.setTag("ItemInventory", items);
	}

	@Override
	public ITextComponent getDisplayName() {

		return new TextComponentString(invItem.getDisplayName());
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return null;
	}
}
