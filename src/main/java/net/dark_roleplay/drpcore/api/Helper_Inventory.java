package net.dark_roleplay.drpcore.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class Helper_Inventory {

	public static ItemStack[] cloneInventory(EntityPlayer player){
		ItemStack[] inventoryCopy = new ItemStack[36];
		NonNullList<ItemStack> inventory = player.inventory.mainInventory;
		for(int i = 0; i < 36; i++){
			inventoryCopy[i] = inventory.get(i).copy();
		}
		return inventoryCopy;
	}
	
	public static boolean hasItems(EntityPlayer player, ItemStack[] items){
		ItemStack[] inventoryCopy = cloneInventory(player);
		
		boolean hasItems = true;
		for(int i = 0; i < items.length; i++){
			ItemStack stack = items[i].copy();
			for(int j = 0; j < 36; j++){
				if(inventoryCopy[j].isEmpty())
					continue;
				
				if(inventoryCopy[j].isItemEqual(stack)){
					
				}
			}
		}
		
		return hasItems;
	}
}
