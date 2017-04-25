package net.dark_roleplay.drpcore.common.inventory;

import net.minecraft.item.ItemStack;

public class InventoryHelper {

	public static boolean[] hasAllItems(ItemStack[] inventory, ItemStack[] compared, boolean[] respectMeta){
		ItemStack[] remainingInv = inventory.clone();
		boolean[] hasStack = new boolean[compared.length]; 
		
		outerLoop:
		for(int i = 0; i < compared.length; i++){
			ItemStack stack = compared[i];
			for(ItemStack invStack : remainingInv){
				if(respectMeta[i]){
					if(ItemStack.areItemsEqual(stack,invStack)){
						if(invStack.stackSize > stack.stackSize){
							invStack.stackSize -= stack.stackSize;
							stack = null;
						}else{
							invStack = null;
							stack = null;
							hasStack[i] = true;
							continue outerLoop;
						}
					}
				}else{
					if(ItemStack.areItemsEqualIgnoreDurability(stack, invStack)){
						if(invStack.stackSize > stack.stackSize){
							invStack.stackSize -= stack.stackSize;
							stack = null;
						}else{
							invStack = null;
							stack = null;
							hasStack[i] = true;
							continue outerLoop;
						}
					}
				}
			}
			hasStack[i] = false;
		}
		return hasStack;
	}
	
}
