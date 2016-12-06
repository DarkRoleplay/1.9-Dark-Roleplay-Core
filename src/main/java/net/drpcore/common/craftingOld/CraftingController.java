package net.drpcore.common.craftingOld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.drpcore.common.util.crafting.CraftingRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;


public class CraftingController {

	// -------------------------------------------------- Variables and Objects --------------------------------------------------
	public static final CraftingController INSTANCE = new CraftingController();

	// Categorys for Station
	private static HashMap<String, String[]> stations = new HashMap<String, String[]>();

	// Recipes Safed for Category
	private static HashMap<String, HashMap<String, AdvancedRecipe[]>> loadedRecipes = new HashMap<String, HashMap<String, AdvancedRecipe[]>>();

	// TODO fix recipe registration
	private static AdvancedRecipe[] registeredRecipes = new AdvancedRecipe[0];

	// -------------------------------------------------- Registration --------------------------------------------------
	public static void registerRecipe(AdvancedRecipe recipe) {
		
		registerRecipe("defaultCategory", recipe);
	}

	public static void registerRecipe(String category, AdvancedRecipe recipe) {

		registerRecipe(Blocks.AIR, category, recipe);
	}

	public static void registerRecipe(Block station, String category, AdvancedRecipe recipe) {

		// Recipe Additions
		recipe.setStation(station);
		recipe.setCategory(category);
		AdvancedRecipe[] newRecipes = new AdvancedRecipe[registeredRecipes.length + 1];
		
		for(int i = 0; i < registeredRecipes.length; i++ ) {
			newRecipes[i] = registeredRecipes[i];
		}
		
		newRecipes[registeredRecipes.length] = recipe;
		registeredRecipes = newRecipes;
	}

	public static void loadRecipes() {

		for(AdvancedRecipe recipe : registeredRecipes) {
		
			registerCategory(recipe.getStation(), recipe.getCategory());
			// Recipe Registration
			String stationName = getStationRegistryName(recipe.getStation());
			AdvancedRecipe[] oldRecipes = loadedRecipes.containsKey(stationName) ? loadedRecipes.get(stationName).containsKey(recipe.getCategory()) ? loadedRecipes.get(stationName).get(recipe.getCategory()) : null : null;
			int recipesLength = oldRecipes == null ? 0 : oldRecipes.length;
			AdvancedRecipe[] newRecipes = new AdvancedRecipe[recipesLength + 1];
			for(int i = 0; i < recipesLength; i++ ) {
				newRecipes[i] = oldRecipes[i];
			}
			recipe.setRegisteredID(recipesLength);
			newRecipes[recipesLength] = recipe;
			HashMap<String, AdvancedRecipe[]> tempRecipes = loadedRecipes.containsKey(stationName) ? loadedRecipes.get(stationName) : new HashMap<String,AdvancedRecipe[]>();
			tempRecipes.put(recipe.getCategory(), newRecipes);
			loadedRecipes.put(stationName, tempRecipes);
		}
	}

	public static void unloadRecipes() {
		
		loadedRecipes = new HashMap<String, HashMap<String, AdvancedRecipe[]>>();
		
	}
	
	private static void registerCategory(Block station, String category) {

		String stationName = getStationRegistryName(station);
		String[] oldCategorys = stations.get(stationName);
		int categoryLength = oldCategorys == null ? 0 : oldCategorys.length;
		
		ArrayList<String> categoryTest = oldCategorys == null ? null : new ArrayList<String>(Arrays.asList(oldCategorys));

		if(categoryTest != null)
			if(categoryTest.contains(category))
				return;
		
			String[] newCategorys;
			newCategorys = new String[categoryLength + 1];
			for(int i = 0; i < categoryLength; i++ ) {
				newCategorys[i] = oldCategorys[i];
			}
			newCategorys[categoryLength] = category;
			stations.put(stationName, newCategorys);
	}

	// -------------------------------------------------- Getter --------------------------------------------------
	public static String[] getCategorysForStation(Block block) {

		if(isStation(block)) {
			return stations.get(getStationRegistryName(block));
		} else {
			return null;
		}
	}

	public static AdvancedRecipe[] getRecipesForCategory(Block station, String category) {

		return loadedRecipes.get(getStationRegistryName(station)).get(category);
	}
	// -------------------------------------------------- Setter --------------------------------------------------

	// -------------------------------------------------- Utility --------------------------------------------------
	public static boolean isStation(Block block) {

		if(stations.containsKey(getStationRegistryName(block)))
			return true;
		return false;
	}

	private static String getStationRegistryName(Block block) {

		return block.getRegistryName().getResourceDomain() + "_" + block.getRegistryName().getResourcePath();
	}
	
	/** TODO Move to Recipe Manager */
	public String createRegistryName(AdvancedRecipe recipe) {

		String name = recipe.getDefaultOutput().getItem().getRegistryName().getResourcePath();
		String domain = recipe.getDefaultOutput().getItem().getRegistryName().getResourceDomain();
		String registryName = domain + "_" + name;
		for(ItemStack stack : recipe.getPrimaryIngredients()) {
			char[] Chars = stack.getItem().getRegistryName().getResourcePath().toCharArray();
			String ingredientName = "";
			for(int i = 0; i < Chars.length; i += 2) {
				ingredientName = ingredientName + Chars[i];
			}
			registryName = registryName + "_" + ingredientName;
			ingredientName = "";
		}
		return registryName;
	}
	
	public static boolean craftRecipe(EntityPlayer player,AdvancedRecipe recipe, ItemStack[] consumedPrimary, ItemStack[] consumedSecondary,int craftAmount){
		
		InventoryPlayer inventory = player.inventory;
		ItemStack[] testInv = inventory.mainInventory.clone();
		List<ItemStack[]> tempInv = new ArrayList<ItemStack[]>();
		ArrayList<ItemStack> pRemovedItems = new ArrayList<ItemStack>();
		ArrayList<ItemStack> sRemovedItems = new ArrayList<ItemStack>();
		
		HashMap<ArrayList<Integer>,ArrayList<Integer>> slotsToDecrease = new HashMap<ArrayList<Integer>, ArrayList<Integer>>();
 		
		if(consumedPrimary != null)
			for(int i = 0; i < consumedPrimary.length; i++){
				ItemStack stack = consumedPrimary[i].copy();
				stack.stackSize *= craftAmount;
				//System.out.println(stack.stackSize);
				if(consumeItemStack(stack,testInv.clone(),recipe.respectPrimaryMeta(i),true) != null){
					tempInv = consumeItemStack(stack,testInv.clone(),recipe.respectPrimaryMeta(i),true) != null ? consumeItemStack(stack,testInv.clone(),recipe.respectPrimaryMeta(i),false) : null;
					if(tempInv != null){
						for(ItemStack stack2 : tempInv.get(1)){
							pRemovedItems.add(stack2);
						}
						testInv = tempInv.get(0);
					}
				}else{
					return false;
				}
			}
		
		if(consumedSecondary != null)
			for(int i = 0; i < consumedSecondary.length; i++){
				ItemStack stack = consumedSecondary[i].copy(); 
				stack.stackSize *= craftAmount;
				System.out.println(stack.stackSize);
				if(consumeItemStack(stack,testInv,recipe.respectSecondaryMeta(i),true) != null){
					tempInv = consumeItemStack(stack,testInv,recipe.respectSecondaryMeta(i),true) != null ? consumeItemStack(stack,testInv,recipe.respectSecondaryMeta(i),false) : null;
					if(tempInv != null){
						for(ItemStack stack2 : tempInv.get(1)){
							sRemovedItems.add(stack2);
						}
						testInv = tempInv.get(0);
				
				}else{
					return false;
				}
			}
		}
		
		for(int i = 0; i < player.inventory.mainInventory.length ; i++ ){
			player.inventory.mainInventory[i] = testInv[i];
		}
		
		//Output
		//Container Items
		for(ItemStack stack : pRemovedItems){
			if(stack != null)
			if(stack.getItem().getContainerItem(stack) != null){
				stack = stack.getItem().getContainerItem(stack);
				if(!inventory.addItemStackToInventory(stack.copy())){
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack.copy()));
				}
			}
		}
		
		for(ItemStack stack : sRemovedItems){
			if(stack.getItem().getContainerItem(stack) != null){
				stack = stack.getItem().getContainerItem(stack);
				if(stack != null)
				if(!inventory.addItemStackToInventory(stack.copy())){
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack.copy()));
				}
			}
		}

		ItemStack[] output = recipe.getOutput(pRemovedItems.toArray(new ItemStack[recipe.getPrimaryIngredients().length]), recipe.getSecondaryIngredients() != null ?  sRemovedItems.toArray(new ItemStack[recipe.getSecondaryIngredients().length]) : null);
		for(ItemStack stack : output){
			for(int i = 0; i < craftAmount; i++){
				if(!inventory.addItemStackToInventory(stack.copy())){
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack.copy()));
				}
			}
		}
		((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
		return true;
	}
	
	private static List<ItemStack[]> consumeItemStack(ItemStack stack, ItemStack[] inventory, boolean ignoreMeta,boolean test) {
//.getContainerItem(removedItem)
		ArrayList<ItemStack[]> toReturn = new ArrayList<ItemStack[]>();
		
		ArrayList<ItemStack> removedItems = new ArrayList<ItemStack>();
		ItemStack[] removedItemsArray = new ItemStack[36];
		
		
		if(test){
			ItemStack[] tempInv = new ItemStack[inventory.length];
			for(int i = 0; i < tempInv.length; i ++){
				if(inventory[i] != null)
				tempInv[i] = inventory[i].copy();
				else
				tempInv[i] = null;
			}
			inventory = tempInv;
		}
		int missingAmount = stack.stackSize;
		for(int i = 0; i < inventory.length; ++i) {
			if(inventory[i] != null && stack != null && inventory[i].getItem() == stack.getItem()) {
				if(ignoreMeta) {
					if(inventory[i].stackSize > missingAmount) {
						removedItems.add(inventory[i].splitStack(stack.stackSize));
						inventory[i].stackSize = inventory[i].stackSize - stack.stackSize;
						toReturn.add(inventory);
						toReturn.add(removedItems.toArray(removedItemsArray));
						return toReturn;
					} else if(inventory[i].stackSize == missingAmount) {
						inventory[i] = null;
						removedItems.add(inventory[i]);
						toReturn.add(inventory);
						toReturn.add(removedItems.toArray(removedItemsArray));
						return toReturn;
					} else if(inventory[i].stackSize < missingAmount) {
						missingAmount -= inventory[i].stackSize;
						removedItems.add(inventory[i]);
						inventory[i] = null;
					}
				} else if( ! ignoreMeta && inventory[i].getMetadata() == stack.getMetadata()) {
					if(inventory[i].stackSize >= stack.stackSize) {
						removedItems.add(inventory[i].splitStack(stack.stackSize));
						inventory[i].stackSize = inventory[i].stackSize - stack.stackSize;
						toReturn.add(inventory);
						toReturn.add(removedItems.toArray(removedItemsArray));
						return toReturn;
					} else if(inventory[i].stackSize <= missingAmount) {
						missingAmount -= inventory[i].stackSize;
						removedItems.add(inventory[i]);
						inventory[i] = null;
					}
				}
				if(missingAmount == 0)
					
					toReturn.add(inventory);
					toReturn.add(removedItems.toArray(removedItemsArray));
					return toReturn;
			}
		}
		return null;
	}

	private static int getInventorySlotContainStack(ItemStack stack, ItemStack[] inventory, boolean ignoreMeta) {

		for(int i = 0; i < inventory.length; ++i) {
			if(ignoreMeta){
				if(inventory[i] != null && inventory[i].getItem() == stack.getItem()) {
					return i;
				}
			}else{
				if(inventory[i] != null && inventory[i].getItem() == stack.getItem() && inventory[i].getMetadata() == stack.getMetadata()) {
					return i;
				}
			}
		}
		return - 1;
	}

	public static AdvancedRecipe getRecipe(String stationName, String categoryName, int recipeID) {
		HashMap<String, AdvancedRecipe[]> categorys = loadedRecipes.get(stationName);
		AdvancedRecipe[] recipes = categorys.get(categoryName);
		return recipes[recipeID];
	}
}
