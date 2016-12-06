package net.drpcore.common.capabilities;

import java.util.ArrayList;
import java.util.List;

public interface IRecipeController {

	List<String> lockedRecipes = new ArrayList<String>();
	List<String> unlockedRecipes = new ArrayList<String>();
	
	public boolean unlockRecipe(String recipeID);
	
	public boolean lockRecipe(String recipeID);
	
	public boolean isUnlocked(String recipeID);
	
	public boolean isLocked(String recipeID);
	
	public List<String> getLockedRecipes();
	
	public List<String> getUnlockedRecipes();
}
