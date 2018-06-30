package net.dark_roleplay.core.common.capabilities.player.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeControllerDefault implements IRecipeController{
	
	private List<String> lockedRecipes = new ArrayList<String>();
	private List<String> unlockedRecipes = new ArrayList<String>();
	private Map<String,Float> progressedRecipes = new HashMap<String,Float>();
	
	@Override
	public boolean unlockRecipe(String recipeID) {
		if(unlockedRecipes.contains(recipeID)){
			unlockedRecipes.remove(recipeID);
			return false;
		}
		unlockedRecipes.add(recipeID);
		return true;
	}

	@Override
	public boolean lockRecipe(String recipeID) {
		if(lockedRecipes.contains(recipeID)){
			lockedRecipes.remove(recipeID);
//			ToastController.displayInfoToast("Unlocked recipe", recipeID);
			return false;
		}
		lockedRecipes.add(recipeID);
//		ToastController.displayInfoToast("Locked recipe", recipeID);
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
	public void progressRecipe(String recipeID, float percantage) {
		if(!unlockedRecipes.contains(recipeID)){
			if(progressedRecipes.containsKey(recipeID)){
				progressedRecipes.put(recipeID, percantage + progressedRecipes.get(recipeID));
			}else{
				progressedRecipes.put(recipeID, percantage);
			}
			if(progressedRecipes.get(recipeID) >= 1F){
				unlockRecipe(recipeID);
				progressedRecipes.remove(recipeID);
			}
		}
	}

	@Override
	public float getProgressRecipe(String recipeID) {
		if(progressedRecipes.containsKey(recipeID)){
			return progressedRecipes.get(recipeID);
		}
		return 0F;
	}

	@Override
	public Map<String, Float> getProgressedRecipes() {
		return progressedRecipes;
	}

	@Override
	public boolean isRecipeLocked(String recipe) {
		return this.lockedRecipes.contains(recipe);
	}

	@Override
	public boolean isRecipeUnlocked(String recipe) {
		return this.unlockedRecipes.contains(recipe);
	}

	@Override
	public boolean isRecipeProgressed(String recipe) {
		return this.progressedRecipes.containsKey(recipe);
	}

}

