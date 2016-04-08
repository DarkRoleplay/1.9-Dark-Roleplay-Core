package net.drpcore.common.gui.inventories;

import net.drpcore.common.items.templates.AmmunitionBase;
import net.drpcore.common.items.templates.BackpackBase;
import net.drpcore.common.items.templates.BeltBase;
import net.drpcore.common.items.templates.NecklaceBase;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.items.templates.RingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PlayerInventory extends InventoryCustom{

	private final String name = "Inventory";
	
	private static final String SAVE_KEY = "DRPCoreCustomInv";
	
	public static final int INV_SIZE = 7;
	public static final int SLOT_NECKLACE = 0;
	public static final int SLOT_RING_LEFT = 1;
	public static final int SLOT_RING_RIGHT = 2;
	public static final int SLOT_BELT = 3;
	public static final int SLOT_PURSE = 4;
	public static final int SLOT_BACKPACK = 5;
	public static final int SLOT_AMMUNITIONCONTAINER = 6;
	
	@Override
	protected String getNbtKey(){
		return SAVE_KEY;
	}

	public PlayerInventory(){
		this.inventory = new ItemStack[INV_SIZE];
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
		return false;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}
}
