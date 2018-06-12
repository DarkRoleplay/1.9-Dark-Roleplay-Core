package net.dark_roleplay.core.api.old.modules.crafting;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class PlayerInventoryHelper {

	public static void consumeItems(EntityPlayer player, ItemStack[] stacks){
		InventoryPlayer inv = player.inventory;
		stacks = stacks.clone();
		for(ItemStack stack : stacks){
			int remaining = stack.getCount();
			for(int i = 0; i < inv.mainInventory.size(); i++){
				ItemStack stackB;
				if((stackB = inv.getStackInSlot(i)).isItemEqual(stack)){
					if(stackB.getCount() < remaining){
						remaining -= stackB.getCount();
						inv.decrStackSize(i, stackB.getCount());
					}else if(stack.getCount() >= remaining){
						inv.decrStackSize(i, remaining);
						continue;
					}
				}
			}
		}
	}
	
	public static ItemStack[] hasItems(EntityPlayer player, ItemStack[] stacks){
		InventoryPlayer inv = new InventoryPlayer(player);
		inv.copyInventory(player.inventory);
		stacks = stacks.clone();
		for(ItemStack stack : stacks){
			for(int i = 0; i < inv.mainInventory.size(); i++){
				ItemStack stackB;
				if((stackB = inv.getStackInSlot(i)).isItemEqual(stack)){
					if(stackB.getCount() < stack.getCount()){
						stack.shrink(stackB.getCount());
						inv.decrStackSize(i, stackB.getCount());
					}else if(stack.getCount() >= stack.getCount()){
						inv.decrStackSize(i, stack.getCount());
						stack.shrink(stack.getCount());
						continue;
					}
				}
			}
		}
		boolean hasItems = true;
		for(ItemStack stack : stacks){
			if(!stack.isEmpty())
				hasItems = false;
		}
		if(hasItems)
			return null;
		return stacks;
	}
	
}
