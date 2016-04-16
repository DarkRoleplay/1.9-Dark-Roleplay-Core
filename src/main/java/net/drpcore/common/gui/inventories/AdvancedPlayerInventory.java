package net.drpcore.common.gui.inventories;

import net.drpcore.common.items.templates.AmmunitionBase;
import net.drpcore.common.items.templates.BackpackBase;
import net.drpcore.common.items.templates.BeltBase;
import net.drpcore.common.items.templates.NecklaceBase;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.items.templates.RingBase;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.PacketSyncAdvancedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AdvancedPlayerInventory extends InventoryCustom{

	private final String name = "Inventory";
	
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

	public AdvancedPlayerInventory(){
		this.inventory = new ItemStack[INV_SIZE];
		//this.player = Minecraft.getMinecraft().thePlayer;
	}
	
	@Override
	protected String getNbtKey(){
		return SAVE_KEY;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public int getInventoryStackLimit(){
		return 1;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack){
		if(slot == SLOT_NECKLACE && stack.getItem() instanceof NecklaceBase)return true;
		else if(slot >= SLOT_RING_LEFT && slot <= SLOT_RING_RIGHT && stack.getItem() instanceof RingBase)return true;
		else if(slot == SLOT_BELT && stack.getItem() instanceof BeltBase)return true;
		else if(slot == SLOT_PURSE && stack.getItem() instanceof PurseBase)return true;
		else if(slot == SLOT_BACKPACK && stack.getItem() instanceof BackpackBase)return true;
		else if(slot == SLOT_AMMUNITIONCONTAINER && stack.getItem() instanceof AmmunitionBase)return true;
		else if(slot == SLOT_COS_HELMET && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.HEAD) return true;
		else if(slot == SLOT_COS_CHESTPLATE && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.CHEST) return true;
		else if(slot == SLOT_COS_LEGGINS && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.LEGS) return true;
		else if(slot == SLOT_COS_BOOTS && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getEquipmentSlot() == EntityEquipmentSlot.FEET) return true;
		return false;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null){
			if (stack.stackSize > amount){
				stack = stack.splitStack(amount);
			}else{
				setInventorySlotContents(slot, null);
			}
			syncSlotToClients(slot);;
		}
		return stack;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
		syncSlotToClients(slot);
	}
	
	public void syncSlotToClients(int slot) {
		try {
			//if (Baubles.proxy.getClientWorld() == null) {
			//	PacketHandler.INSTANCE.sendToAll(new PacketSyncAdvancedInventory(slot, player));
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
