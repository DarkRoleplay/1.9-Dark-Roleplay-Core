package net.drpcore.common.capabilities;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.Arrays;

public class RecipeControllerDefault implements IRecipeController{

	private List<String> lockedRecipes = new ArrayList<String>();
	private List<String> unlockedRecipes = new ArrayList<String>();
	
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

}
