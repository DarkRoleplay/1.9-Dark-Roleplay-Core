package net.dark_roleplay.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;

public class CraftingRegistry {
	
	private static Map<String,SimpleRecipe> recipes = new HashMap<String,SimpleRecipe>();
	private static Map<String,SimpleRecipe> unlockRecipes = new HashMap<String,SimpleRecipe>();

	
	private static Map<Block,Map<String,List<String>>> stationKeys = new HashMap<Block,Map<String,List<String>>>();

	
	public static boolean registerRecipe(Block station, String category, SimpleRecipe recipe, boolean requiresUnlock){
		recipe.setStation(station);
		if(recipes.containsKey(recipe.getRegistryName()) || unlockRecipes.containsKey(recipe.getRegistryName())){
			return false;
		}
		
		if(requiresUnlock){
			unlockRecipes.put(recipe.getRegistryName(), recipe);
		}else{
			recipes.put(recipe.getRegistryName(), recipe);
		}
		
		Map<String,List<String>> tmpRecipeKeys;
		List<String> tmpCategoryKeys;
		
		if(!stationKeys.containsKey(station)){
			tmpRecipeKeys = new HashMap<String,List<String>>();
			tmpCategoryKeys = new ArrayList<String>();
			tmpCategoryKeys.add(recipe.getRegistryName());
			tmpRecipeKeys.put(category, tmpCategoryKeys);
			stationKeys.put(station, tmpRecipeKeys);
		}else{
			tmpRecipeKeys = stationKeys.get(station);
			if(!tmpRecipeKeys.containsKey(category)){
				tmpCategoryKeys = new ArrayList<String>();
			}else{
				tmpCategoryKeys = tmpRecipeKeys.get(category);
			}
			tmpCategoryKeys.add(recipe.getRegistryName());
			tmpRecipeKeys.put(category, tmpCategoryKeys);
			stationKeys.replace(station, tmpRecipeKeys);
		}
		return true;
	}
	public static Map<String,List<String>> getRecipesForStation(Block block){
		return stationKeys.containsKey(block) ? stationKeys.get(block) : null;
	}
	
	public static SimpleRecipe getRecipe(String recipeName){
		return recipes.containsKey(recipeName) ? recipes.get(recipeName) : unlockRecipes.containsKey(recipeName) ? unlockRecipes.get(recipeName) : null;
	}
	
	public static boolean requiresRecipeUnlock(String recipeName){
		return unlockRecipes.containsKey(recipeName);
	}
	
	public static List<Block> getRecipeStations(){
		return new ArrayList<Block>(stationKeys.keySet());
	}
	
}
