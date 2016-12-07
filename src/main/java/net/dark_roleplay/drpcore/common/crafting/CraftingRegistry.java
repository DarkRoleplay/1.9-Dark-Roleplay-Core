package net.dark_roleplay.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;

public class CraftingRegistry {

	public static CraftingRegistry INSTANCE;
	
	private Map<String,SimpleRecipe> recipes = new HashMap<String,SimpleRecipe>();
	
	private Map<Block,Map<String,List<String>>> stationKeys = new HashMap<Block,Map<String,List<String>>>();

	
	public boolean registerRecipe(Block station, String category,String recipeRegistryName, SimpleRecipe recipe){
		if(this.recipes.containsKey(recipeRegistryName))
			return false;
		
		this.recipes.put(recipeRegistryName, recipe);
		
		Map<String,List<String>> tmpRecipeKeys;
		List<String> tmpCategoryKeys;
		
		if(!this.stationKeys.containsKey(station)){
			tmpRecipeKeys = new HashMap<String,List<String>>();
			tmpCategoryKeys = new ArrayList<String>();
			tmpCategoryKeys.add(recipeRegistryName);
			tmpRecipeKeys.replace(category, tmpCategoryKeys);
			this.stationKeys.replace(station, tmpRecipeKeys);
			
		}else{
			tmpRecipeKeys = this.stationKeys.get(station);
			if(!tmpRecipeKeys.containsKey(category)){
				tmpCategoryKeys = new ArrayList<String>();
			}else{
				tmpCategoryKeys = tmpRecipeKeys.get(category);
			}
			tmpCategoryKeys.add(recipeRegistryName);
			tmpRecipeKeys.replace(category, tmpCategoryKeys);
			this.stationKeys.replace(station, tmpRecipeKeys);
		}
		return true;
	}
	
	public void setInstance(CraftingRegistry rg){
		if(this.INSTANCE == null)
			this.INSTANCE = rg;
	}
	
	public Map<String,List<String>> getRecipesForStation(Block block){
		return stationKeys.containsKey(block) ? stationKeys.get(block) : null;
	}
	
	public SimpleRecipe getRecipe(String recipeName){
		return recipes.containsKey(recipeName) ? recipes.get(recipeName) : null;
	}
}
