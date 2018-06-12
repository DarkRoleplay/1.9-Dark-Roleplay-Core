package net.dark_roleplay.core.api.crafting;

import com.google.gson.JsonObject;

public interface IIngredientFactory {

	public IIngredient loadFromJson(JsonObject object);
	
}
