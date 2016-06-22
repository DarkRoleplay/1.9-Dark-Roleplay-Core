package net.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;


public class CraftingController {

	//-------------------------------------------------- Variables and Objects --------------------------------------------------
	public CraftingController INSTANCE = this;

	//Categorys for Station
	private static HashMap<String, String[]> stations = new HashMap<String, String[]>();

	//Recipes Safed for Category
	private static HashMap<String,HashMap<String, AdvancedRecipe[]>> loadedRecipes = new HashMap<String,HashMap<String, AdvancedRecipe[]>>();
	
	//TODO fix recipe registration
	private static AdvancedRecipe[] registeredRecipes;
	
	//-------------------------------------------------- Registration --------------------------------------------------

	public static void registerRecipe(AdvancedRecipe recipe) {

		registerRecipe("Default Category", recipe);
	}

	public static void registerRecipe(String category, AdvancedRecipe recipe) {

		registerRecipe(Blocks.air, category, recipe);
	}

	public static void registerRecipe(Block station, String category, AdvancedRecipe recipe) {
		
		//Recipe Additions
		recipe.setStation(station);
		recipe.setCategory(category);
		
		//Category Registration
		registerCategory(station, category);
		
		//Recipe Registration
		String stationName = getStationRegistryName(station);
		
		AdvancedRecipe[] oldRecipes = loadedRecipes.get(station).get(category);
		
		AdvancedRecipe[] newRecipes = new AdvancedRecipe[oldRecipes.length];
		
		for(int i = 0; i < oldRecipes.length; i++){
			newRecipes[i] = oldRecipes[i];
		}
		
		newRecipes[oldRecipes.length] = recipe;
		
		HashMap<String,AdvancedRecipe[]> tempRecipes = loadedRecipes.get(station);
		
		tempRecipes.put(category, newRecipes);
		
		loadedRecipes.put(stationName, tempRecipes);
		
	}

	private static void registerCategory(Block station, String category){
		String stationName = getStationRegistryName(station);
		
		String[] oldCategorys = stations.get(stationName);
		
		String[] newCategorys = new String[oldCategorys.length];
		
		for(int i = 0; i < oldCategorys.length; i++){
			newCategorys[i] = oldCategorys[i];
		}
		
		newCategorys[oldCategorys.length] = category;
		
		stations.put(stationName, newCategorys);
	}
	
	//-------------------------------------------------- Getter --------------------------------------------------
	public static String[] getCategorysForStation(Block block) {

		if(isStation(block)) {
			return stations.get(getStationRegistryName(block));
		} else {
			return null;
		}
	}

	public static AdvancedRecipe[] getRecipesForCategory(Block station ,String category) {

		return loadedRecipes.get(getStationRegistryName(station)).get(category);
	}
	//-------------------------------------------------- Setter --------------------------------------------------

	//-------------------------------------------------- Utility --------------------------------------------------
	public static boolean isStation(Block block) {

		if(stations.containsKey(getStationRegistryName(block)))
			return true;
		return false;
	}

	private static String getStationRegistryName(Block block) {

		return block.getRegistryName().getResourceDomain() + "_" + block.getRegistryName().getResourcePath();
	}
}
