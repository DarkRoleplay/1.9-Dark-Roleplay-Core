package net.dark_roleplay.drpcore.api.crafting;

import com.google.gson.JsonObject;

public interface IRecipeLoader {
	
	public IRecipe loadRecipe(JsonObject obj);
	
}
