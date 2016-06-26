package net.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;


public class CraftingController {

	// -------------------------------------------------- Variables and Objects --------------------------------------------------
	public CraftingController INSTANCE = this;

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
			// Category Registration
			System.out.println(recipe.getStation() + "    " + recipe.getCategory());
			registerCategory(recipe.getStation(), recipe.getCategory());
			// Recipe Registration
			String stationName = getStationRegistryName(recipe.getStation());
			AdvancedRecipe[] oldRecipes = loadedRecipes.containsKey(stationName) ? loadedRecipes.get(stationName).containsKey(recipe.getCategory()) ? loadedRecipes.get(stationName).get(recipe.getCategory()) : null : null;
			int recipesLength = oldRecipes == null ? 0 : oldRecipes.length;
			AdvancedRecipe[] newRecipes = new AdvancedRecipe[recipesLength + 1];
			for(int i = 0; i < recipesLength; i++ ) {
				newRecipes[i] = oldRecipes[i];
			}
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
}
