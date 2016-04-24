package net.drpcore.common.gui.inventories;

import java.util.ArrayList;
import java.util.List;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.items.templates.AmmunitionBase;
import net.drpcore.common.items.templates.BackpackBase;
import net.drpcore.common.items.templates.BeltBase;
import net.drpcore.common.items.templates.NecklaceBase;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.items.templates.RingBase;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.packets.PacketSyncAdvancedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class AdvancedPlayerInventory implements IInventory {

	protected ItemStack[] inventory;

	private static final String SAVE_KEY = "DRPCoreCustomInv";

	public static final int INV_SIZE = 11;
	public static final int SLOT_NECKLACE = 0;
	public static final int SLOT_RING_LEFT = 1;
	public static final int SLOT_RING_RIGHT = 2;
	public static final int SLOT_BELT = 3;
	public static final int SLOT_PURSE = 4;
	public static final int SLOT_BACKPACK = 5;
	public static final int SLOT_AMMUNITIONCONTAINER = 6;
	public static final int SLOT_COS_HELMET = 7;
	public static final int SLOT_COS_CHESTPLATE = 8;
	public static final int SLOT_COS_LEGGINS = 9;
	public static final int SLOT_COS_BOOTS = 10;

	public EntityPlayer player;

	public AdvancedPlayerInventory() {
		this.inventory = new ItemStack[INV_SIZE];
	}

	public AdvancedPlayerInventory(AdvancedPlayerInventory inv, EntityPlayer player) {
		this.inventory = inv.inventory;
		this.player = player;
	}

	@Override
	public int getSizeInventory() {

		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return slot >= this.getSizeInventory() ? null : this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {

		ItemStack stack = this.inventory[slot];
		if(stack != null){
			if(stack.stackSize > amount){
				stack = stack.splitStack(amount);
				syncSlotToClients(slot);
				return stack;
			}
			else{
				setInventorySlotContents(slot, null);
				syncSlotToClients(slot);
				return stack;
			}
		}
		else{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {

		if(this.inventory[slot] != null){
			ItemStack itemstack = this.inventory[slot];
			this.inventory[slot] = null;
			return itemstack;
		}
		else{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		this.inventory[slot] = stack;
		syncSlotToClients(slot);
	}

	@Override
	public int getInventoryStackLimit() {

		return 1;
	}

	@Override
	public void markDirty() {

		try{
			player.inventory.markDirty();
		}
		catch(Exception e){}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {

		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		if(stack == null) return false;
		if(slot == SLOT_NECKLACE && stack.getItem() instanceof NecklaceBase)
			return true;
		else if(slot >= SLOT_RING_LEFT && slot <= SLOT_RING_RIGHT && stack.getItem() instanceof RingBase)
			return true;
		else if(slot == SLOT_BELT && stack.getItem() instanceof BeltBase)
			return true;
		else if(slot == SLOT_PURSE && stack.getItem() instanceof PurseBase)
			return true;
		else if(slot == SLOT_BACKPACK && stack.getItem() instanceof BackpackBase)
			return true;
		else if(slot == SLOT_AMMUNITIONCONTAINER && stack.getItem() instanceof AmmunitionBase)
			return true;
		else if(slot == SLOT_COS_HELMET && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.HEAD)
			return true;
		else if(slot == SLOT_COS_CHESTPLATE && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.CHEST)
			return true;
		else if(slot == SLOT_COS_LEGGINS && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.LEGS)
			return true;
		else if(slot == SLOT_COS_BOOTS && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.FEET) return true;
		return false;
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
	public void clear() {

		for(int i = 0; i < inventory.length; i++){
			inventory[i] = null;
		}
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public ITextComponent getDisplayName() {

		return null;
	}

	@Override
	public String getName() {

		return null;
	}

	// -----Custom Inventory Start------

	protected String getNbtKey() {

		return SAVE_KEY;
	}

	public void saveNBT(EntityPlayer player) {

		NBTTagCompound tags = player.getEntityData();
		saveNBT(tags);
	}

	public void saveNBT(NBTTagCompound tags) {

		NBTTagList tagList = new NBTTagList();
		NBTTagCompound invSlot;
		for(int i = 0; i < this.inventory.length; ++i){
			if(this.inventory[i] != null){
				invSlot = new NBTTagCompound();
				invSlot.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(invSlot);
				tagList.appendTag(invSlot);
			}
		}
		tags.setTag("Baubles.Inventory", tagList);
	}

	public void readNBT(EntityPlayer player) {

		NBTTagCompound tags = player.getEntityData();
		readNBT(tags);
	}

	public void readNBT(NBTTagCompound tags) {

		NBTTagList tagList = tags.getTagList("Baubles.Inventory", 10);
		for(int i = 0; i < tagList.tagCount(); ++i){
			NBTTagCompound nbttagcompound = (NBTTagCompound) tagList.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
			if(itemstack != null){
				this.inventory[j] = itemstack;
			}
		}
	}

	public void dropItems(ArrayList<EntityItem> drops) {

		for(int i = 0; i < 4; ++i){
			if(this.inventory[i] != null){
				EntityItem ei = new EntityItem(player.worldObj, player.posX, player.posY + player.getEyeHeight(), player.posZ, this.inventory[i].copy());
				ei.setPickupDelay(40);
				float f1 = player.worldObj.rand.nextFloat() * 0.5F;
				float f2 = player.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
				ei.motionX = (double) (-MathHelper.sin(f2) * f1);
				ei.motionZ = (double) (MathHelper.cos(f2) * f1);
				ei.motionY = 0.20000000298023224D;
				drops.add(ei);
				this.inventory[i] = null;
				syncSlotToClients(i);
			}
		}
	}

	public void dropItemsAt(List<EntityItem> drops, Entity e) {

		for(int i = 0; i < 4; ++i){
			if(this.inventory[i] != null){
				EntityItem ei = new EntityItem(e.worldObj, e.posX, e.posY + e.getEyeHeight(), e.posZ, this.inventory[i].copy());
				ei.setPickupDelay(40);
				float f1 = e.worldObj.rand.nextFloat() * 0.5F;
				float f2 = e.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
				ei.motionX = (double) (-MathHelper.sin(f2) * f1);
				ei.motionZ = (double) (MathHelper.cos(f2) * f1);
				ei.motionY = 0.20000000298023224D;
				drops.add(ei);
				this.inventory[i] = null;
				syncSlotToClients(i);
			}
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		String key = getNbtKey();
		if(key == null || key.equals("")){ return null; }
		NBTTagList items = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); ++i){
			if(getStackInSlot(i) != null){
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				getStackInSlot(i).writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag(key, items);
		return compound;
	}

	public void readFromNBT(NBTTagCompound compound) {

		String key = getNbtKey();
		if(key == null || key.equals("")){ return; }
		NBTTagList items = compound.getTagList(key, compound.getId());
		for(int i = 0; i < items.tagCount(); ++i){
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if(slot >= 0 && slot < getSizeInventory()){
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public void syncSlotToClients(int slot) {

		if(DarkRoleplayCore.isServer == Side.SERVER) for(EntityPlayerMP other : (List<EntityPlayerMP>) FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList()){
			for(int i = 0; i < this.getSizeInventory(); i++)
				PacketHandler.sendTo(new PacketSyncAdvancedInventory(i, player), other);
		}
		/*
		 * try { if(player != null) if (DarkRoleplayCore.isServer ==
		 * Side.SERVER) { PacketHandler.INSTANCE.sendToAll(new
		 * PacketSyncAdvancedInventory( slot, player)); } } catch (Exception e)
		 * { e.printStackTrace(); }
		 */
	}
}
