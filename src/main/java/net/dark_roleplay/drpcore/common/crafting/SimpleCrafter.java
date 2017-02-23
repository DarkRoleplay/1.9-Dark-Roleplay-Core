package net.dark_roleplay.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class SimpleCrafter {

	public void initializeCrafting(EntityPlayer player, SimpleRecipe recipe){
	
		List<ItemStack> inv = player.inventory.mainInventory;
		
		ItemStack[] ingredients = recipe.getMainIngredients();
		NonNullList<ItemStack> invCopy = NonNullList.<ItemStack>withSize(36, ItemStack.EMPTY);
		for(int i = 0; i < inv.size(); i++){
			invCopy.set(i, inv.get(i).copy());
		}
		boolean[] missingStacks = hasMaterials(invCopy, ingredients.clone(), recipe.getIgnoreMeta());
		
		for(int i = 0; i < missingStacks.length; i++){
			if(!missingStacks[i]){
				player.sendMessage(new TextComponentString("Sorry but you are missing at least the following Item: " + ingredients[i].getDisplayName()));
				return;
			}
		}
		
		consumeMaterials(player, ingredients.clone());
		ItemStack[] outs = recipe.getMainOutput();
		for(ItemStack s : outs){
			if(!player.inventory.addItemStackToInventory(s.copy())){
				player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, s.copy()));
			}
		}
        player.openContainer.detectAndSendChanges();
	}
	
	public static void consumeMaterials(EntityPlayer player, ItemStack[] ingredients){
		List<ItemStack> containerItems = new ArrayList<ItemStack>();
		for(ItemStack ingredient : ingredients){
			while(ingredient != null){
				int slot = getSlotFor(player.inventory, ingredient);
				int removalSize = player.inventory.getStackInSlot(slot).getCount();
				ItemStack stack = player.inventory.getStackInSlot(slot);
				if(stack.getItem().hasContainerItem(stack)){
					containerItems.add(stack.getItem().getContainerItem(stack));
				}
				player.inventory.getStackInSlot(slot).shrink(ingredient.getCount());
				if(removalSize >= ingredient.getCount()){
					ingredient = null;
				}
			}
			//player.inventory.removeStackFromSlot(player.inventory.getSlotFor(ingredient));
		}
		for(ItemStack stack : containerItems){
			if(player.inventory.addItemStackToInventory(stack)){
				player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, stack));
			}
		}
	}
	
	private static int getSlotFor(InventoryPlayer inv, ItemStack stack){
        for (int i = 0; i < inv.mainInventory.size(); ++i){
            if (!((ItemStack)inv.mainInventory.get(i)).isEmpty() && stackEqualExact(stack, (ItemStack)inv.mainInventory.get(i))){
                return i;
            }
        }
        return -1;
    }
	
	private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2){
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
	
	public static boolean[] hasMaterials(List<ItemStack> inventory, ItemStack[] compared, boolean[] respectMeta){
		ItemStack[] remainingInv = inventory.toArray(new ItemStack[inventory.size()]);
		boolean[] hasStack = new boolean[compared.length]; 
		
		outerLoop:
		for(int i = 0; i < compared.length; i++){
			ItemStack stack = compared[i].copy();
			if(stack == null) continue;
			if(!respectMeta[i]){
				for(ItemStack invStack : remainingInv){
				
					if(invStack != null && ItemStack.areItemsEqual(stack,invStack)){
						if(invStack.getCount() >= stack.getCount()){
							invStack.shrink(stack.getCount());
							stack = null;
							hasStack[i] = true;
							continue outerLoop;
						}else{
							stack.shrink(invStack.getCount());
							invStack = null;
							continue outerLoop; //TODO REMOVE AND IMPROVE STACK REMOVAL
						}
					}
				}
			}else{
				for(ItemStack invStack : remainingInv){
					if(invStack != null && ItemStack.areItemsEqualIgnoreDurability(stack, invStack)){
						if(invStack.getCount() >= stack.getCount()){
							invStack.shrink(stack.getCount());
							stack = null;
							hasStack[i] = true;
							continue outerLoop;
						}else if (invStack.getCount() < stack.getCount()){
							stack.shrink(invStack.getCount());
							invStack = null;
							continue outerLoop; //TODO REMOVE AND IMPROVE STACK REMOVAL
						}
					}
				}
			}
			hasStack[i] = false;
		}
		return hasStack;
	}
	
}
