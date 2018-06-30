package net.dark_roleplay.core.api.old.crafting.simple_recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;

public class SimpleCrafter {

	public void initializeCrafting(EntityPlayer player, SimpleRecipe recipe, int multiplier) {

		List<ItemStack> inv = player.inventory.mainInventory;

		ItemStack[] ingredients = recipe.getMainIngredients();
		NonNullList<ItemStack> invCopy = NonNullList.<ItemStack>withSize(36, ItemStack.EMPTY);
		for (int i = 0; i < inv.size(); i++) {
			invCopy.set(i, inv.get(i).copy());
		}
		boolean[] missingStacks = hasMaterials(invCopy, ingredients.clone(), recipe.getIgnoreMeta(), multiplier);

		for (int i = 0; i < missingStacks.length; i++) {
			if (!missingStacks[i]) {
				player.sendStatusMessage(new TextComponentString("You are missing the following Item (or more): " + ingredients[i].getDisplayName()), true);
				return;
			}
		}

		consumeMaterials(player, ingredients.clone(), recipe.getIgnoreMeta(), multiplier);
		ItemStack[] outs = recipe.getMainOutput();
		for(int i = 0; i < multiplier; i++){
			for (ItemStack s : outs) {
				if (!player.inventory.addItemStackToInventory(s.copy())) {
					player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, s.copy()));
				}
			}
		}
		player.openContainer.detectAndSendChanges();
	}

	public static void consumeMaterials(EntityPlayer player, ItemStack[] ingredientsMain, boolean[] respectMetaMain, int multiplier) {

		List<ItemStack> containerItems = new ArrayList<ItemStack>();
		
		ItemStack[] ingredients = new ItemStack[ingredientsMain.length * multiplier];
		
		for(int j = 0; j < multiplier; j++){
			for(int i = 0; i < ingredientsMain.length; i++){
				ingredients[i + (j * ingredientsMain.length)] = ingredientsMain[i].copy();
			}
		}
		
		boolean[] respectMeta = new boolean[respectMetaMain.length * multiplier];
		
		for(int j = 0; j < multiplier; j++){
			for(int i = 0; i < respectMetaMain.length; i++){
				respectMeta[i + (j * respectMetaMain.length)] = respectMetaMain[i];
			}
		}

		InventoryPlayer inv = player.inventory;

		outerLoop:
		for (int i = 0; i < ingredients.length; i++) {
			ItemStack stack = ingredients[i].copy();
			if (stack == null)
				continue;

			for (int j = 0; j < inv.mainInventory.size(); j++) {
				ItemStack invStack = inv.mainInventory.get(j);
				if (invStack != null && ((!respectMeta[i] && ItemStack.areItemsEqualIgnoreDurability(stack, invStack)) || (respectMeta[i] && ItemStack.areItemsEqual(stack, invStack)))) {
					if (invStack.getCount() >= stack.getCount()) {
						if (invStack.getItem().hasContainerItem(invStack)) {
							ItemStack correctStack = invStack.copy();
							correctStack.setCount(stack.getCount());
							containerItems.add(correctStack.getItem().getContainerItem(correctStack));
						}
						inv.decrStackSize(j, stack.getCount());
						stack = null;
						continue outerLoop;
					} else {
						stack.shrink(invStack.getCount());
						if (invStack.getItem().hasContainerItem(invStack)) {
							ItemStack correctStack = invStack.copy();
							containerItems.add(correctStack.getItem().getContainerItem(correctStack));
						}
						inv.removeStackFromSlot(j);
					}
				}
			}
		}
		
		for(ItemStack stack : containerItems){
			if(!player.inventory.addItemStackToInventory(stack.copy())){
				player.world.spawnEntity(new EntityItem(player.world, player.posX,player.posY, player.posZ, stack));
			}
		}
	}

	public static boolean[] hasMaterials(List<ItemStack> inventory, ItemStack[] comparedMain, boolean[] respectMetaMain, int multiplier) {
		ItemStack[] remainingInv = inventory.toArray(new ItemStack[inventory.size()]);
		boolean[] hasStack = new boolean[comparedMain.length];

		ItemStack[] compared = new ItemStack[comparedMain.length * multiplier];
		
		for(int j = 0; j < multiplier; j++){
			for(int i = 0; i < comparedMain.length; i++){
				compared[i + (j * comparedMain.length)] = comparedMain[i].copy();
			}
		}
		
		boolean[] respectMeta = new boolean[respectMetaMain.length * multiplier];
		
		for(int j = 0; j < multiplier; j++){
			for(int i = 0; i < respectMetaMain.length; i++){
				respectMeta[i + (j * respectMetaMain.length)] = respectMetaMain[i];
			}
		}
		
		outerLoop: for (int i = 0; i < compared.length; i++) {
			ItemStack stack = compared[i].copy();
			if (stack == null)
				continue;
			for (ItemStack invStack : remainingInv) {

				if (invStack != null && ((!respectMeta[i] && ItemStack.areItemsEqualIgnoreDurability(stack, invStack))
						|| (respectMeta[i] && ItemStack.areItemsEqual(stack, invStack)))) {
					if (invStack.getCount() >= stack.getCount()) {
						invStack.shrink(stack.getCount());
						stack = null;
						hasStack[(i % comparedMain.length)] = true;
						continue outerLoop;
					} else {
						stack.shrink(invStack.getCount());
						invStack = null;
					}
				}
			}
			hasStack[(i % comparedMain.length)] = false;
		}
		return hasStack;
	}

}
