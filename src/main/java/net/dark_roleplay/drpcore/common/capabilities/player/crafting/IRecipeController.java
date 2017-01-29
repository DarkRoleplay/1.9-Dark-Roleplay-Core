package net.dark_roleplay.drpcore.common.capabilities.player.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRecipeController {

	List<String> lockedRecipes = new ArrayList<String>();
	List<String> unlockedRecipes = new ArrayList<String>();
	Map<String,Float> progressedRecipes = new HashMap<String,Float>();
	
	
	public boolean unlockRecipe(String recipeID);
	
	public boolean lockRecipe(String recipeID);
	
	public boolean isUnlocked(String recipeID);
	
	public boolean isLocked(String recipeID);
	
	public void progressRecipe(String recipeID, float percantage);
	
	public float getProgressRecipe(String recipeID);
	
	public List<String> getLockedRecipes();
	
	public List<String> getUnlockedRecipes();
	
	public Map<String,Float> getProgressedRecipes();
	
	public boolean isRecipeLocked(String recipe);
	
	public boolean isRecipeUnlocked(String recipe);
	
	public boolean isRecipeProgressed(String recipe);
}
