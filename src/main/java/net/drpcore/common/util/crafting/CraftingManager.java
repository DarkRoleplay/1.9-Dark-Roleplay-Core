package net.drpcore.common.util.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.config.DRPCraftingRecipeConfiguration;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Deprecated
public class CraftingManager {

	public static HashMap<Block, ArrayList<String>> CategoryList = new HashMap<Block, ArrayList<String>>();

	public static HashMap<Block, HashMap<String, ArrayList<CraftingRecipe>>> RecipeList = new HashMap<Block, HashMap<String, ArrayList<CraftingRecipe>>>();

	public static void RegisterRecipe(CraftingRecipe Recipe) {

		DRPCraftingRecipeConfiguration.writeMainRecipe(Recipe);
		Block Station = Recipe.getStation();
		if(Station == null) {
			Recipe.setStation(Blocks.AIR);
			Station = Blocks.AIR;
		}
		String Category = Recipe.getCategory();
		if(Category == null) {
			Recipe.setCategory("Default");
			Category = "Default";
		}
		HashMap<String, ArrayList<CraftingRecipe>> CategoryRecipes = RecipeList.get(Station);
		if(CategoryRecipes == null) {
			CategoryRecipes = new HashMap<String, ArrayList<CraftingRecipe>>();
		}
		ArrayList<CraftingRecipe> Recipes = CategoryRecipes.get(Category);
		if(Recipes == null) {
			Recipes = new ArrayList<CraftingRecipe>();
		}
		Recipes.add(Recipe);
		CategoryRecipes.put(Category, Recipes);
		RecipeList.put(Station, CategoryRecipes);
		createCategory(Station, Category);
	}

	public static void unloadAllRecipes() {

		CategoryList = new HashMap<Block, ArrayList<String>>();
		RecipeList = new HashMap<Block, HashMap<String, ArrayList<CraftingRecipe>>>();
	}

	private static void debugRecipes() {

		for(Block a : RecipeList.keySet()) {
			HashMap<String, ArrayList<CraftingRecipe>> b = RecipeList.get(a);
			for(String c : b.keySet()) {
				ArrayList<CraftingRecipe> r = b.get(c);
				for(CraftingRecipe d : r) {
					System.out.println(d.getOutput(null));
				}
			}
		}
	}

	private static void createCategory(Block Station, String Category) {

		ArrayList<String> Categorys = CategoryList.get(Station);
		if(Categorys == null) {
			Categorys = new ArrayList<String>();
			Categorys.add(Category);
		} else {
			if( ! Categorys.contains(Category)) {
				Categorys.add(Category);
			}
		}
		CategoryList.put(Station, Categorys);
	}

	public static ArrayList<String> getCategoryList(Block craftingStation) {

		if(CategoryList.containsKey(craftingStation)) {
			return CategoryList.get(craftingStation);
		}
		return null;
	}

	public static CraftingRecipe getRecipe(Block Station, String Category, int RecipeID) {

		HashMap<String, ArrayList<CraftingRecipe>> CategoryRecipeList = RecipeList.get(Station);
		if(CategoryRecipeList == null)
			return null;
		ArrayList<CraftingRecipe> Recipes = CategoryRecipeList.get(Category);
		if(Recipes == null)
			return null;
		if(Recipes.size() <= RecipeID)
			return null;
		CraftingRecipe Recipe = Recipes.get(RecipeID);
		return Recipe;
	}

	public static CraftingRecipe getRecipe(Block Station, String Category, int RecipeID, boolean isSpecial) {

		HashMap<String, ArrayList<CraftingRecipe>> CategoryRecipeList = RecipeList.get(Station);
		if(CategoryRecipeList == null)
			return null;
		ArrayList<CraftingRecipe> Recipes = CategoryRecipeList.get(Category);
		if(Recipes == null)
			return null;
		if(Recipes.size() <= RecipeID)
			return null;
		CraftingRecipe Recipe = Recipes.get(RecipeID);
		return Recipe;
	}

	public static int getRecipeAmount(Block Station, String category) {

		HashMap<String, ArrayList<CraftingRecipe>> Categorys = RecipeList.get(Station);
		if(Categorys != null) {
			ArrayList<CraftingRecipe> Recipes = Categorys.get(category);
			if(Recipes != null)
				return Recipes.size();
		}
		return 0;
	}

	// public static boolean CraftItem(CraftingRecipe Recipe){
	// return false;
	// }
	public static boolean craftItem(int stationID, String craftCategory, int recipeID, EntityPlayer player) {

		Block craftingStation = Block.getBlockById(stationID);
		CraftingRecipe Recipe = getRecipe(craftingStation, craftCategory, recipeID, true);
		if(Recipe != null) {
			ItemStack[] MainIngredients = Recipe.getMainIngredients();
			ItemStack Output = Recipe.getOutput(null);
			for(ItemStack Input : MainIngredients) {
				if( ! hasItemStack(Input, player, Recipe.shouldIgnoreMeta())) {
					return false;
				}
			}
			InventoryPlayer playerInv = player.inventory;
			for(ItemStack Input : MainIngredients) {
				for(int i = 0; i < Input.stackSize; ++i) {
					ItemStack removedItem = Input;
					int containingSlot = getInventorySlotContainItem(Input.getItem(), player);
					if(getInventorySlotContainItem(Input.getItem(), player) >= 0)
						removedItem = playerInv.getStackInSlot(getInventorySlotContainItem(Input.getItem(), player));
					playerInv.decrStackSize(getInventorySlotContainItem(Input.getItem(), player), 1);
					if(Input.getItem().hasContainerItem(Input)) {
						player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, Input.getItem().getContainerItem(removedItem)));
					}
				}
			}
			player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, Output.copy()));
			return true;
		} else {
			return false;
		}
	}

	private static boolean hasItemStack(ItemStack stack, EntityPlayer player, boolean ignoreMeta) {

		int i;
		InventoryPlayer mainInventory = player.inventory;
		int missingAmount = stack.stackSize;
		for(i = 0; i < mainInventory.getSizeInventory(); ++i) {
			if(mainInventory.getStackInSlot(i) != null && stack != null && mainInventory.getStackInSlot(i).getItem() == stack.getItem()) {
				if(ignoreMeta) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return true;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				} else if( ! ignoreMeta && mainInventory.getStackInSlot(i).getMetadata() == stack.getMetadata()) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return true;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				}
				if(missingAmount == 0)
					return true;
			}
		}
		return false;
	}

	private static int getInventorySlotContainItem(Item itemIn, EntityPlayer player) {

		for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
			if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() == itemIn) {
				return i;
			}
		}
		return - 1;
	}
}
