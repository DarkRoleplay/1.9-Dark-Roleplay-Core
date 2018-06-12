package net.dark_roleplay.core.api.crafting;

import com.google.gson.JsonObject;

public interface IRecipeFactory {
	
	public IRecipe loadRecipe(JsonObject obj);
	
}
