package net.dark_roleplay.drpcore.common.capabilities.player.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimi.dsi.fastutil.Arrays;

public class RecipeControllerDefault implements IRecipeController{

	private List<String> lockedRecipes = new ArrayList<String>();
	private List<String> unlockedRecipes = new ArrayList<String>();
	Map<String,Float> progressedRecipes = new HashMap<String,Float>();
	
	@Override
	public boolean unlockRecipe(String recipeID) {
		if(unlockedRecipes.contains(recipeID))
			return false;
		unlockedRecipes.add(recipeID);
		return true;
	}

	@Override
	public boolean lockRecipe(String recipeID) {
		if(lockedRecipes.contains(recipeID))
			return false;
		lockedRecipes.add(recipeID);
		return true;
	}

	@Override
	public boolean isUnlocked(String recipeID) {
		return unlockedRecipes.contains(recipeID);
	}

	@Override
	public boolean isLocked(String recipeID) {
		return lockedRecipes.contains(recipeID);
	}

	@Override
	public List<String> getLockedRecipes() {
		return lockedRecipes;
	}

	@Override
	public List<String> getUnlockedRecipes() {
		return unlockedRecipes;
	}

	@Override
	public boolean progressRecipe(String recipeID, float percantage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getProgressRecipe(String recipeID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Float> getProgressedRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

}

