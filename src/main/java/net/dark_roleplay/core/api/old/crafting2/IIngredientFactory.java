package net.dark_roleplay.core.api.old.crafting2;

import com.google.gson.JsonObject;

public interface IIngredientFactory {

	public IIngredient loadFromJson(JsonObject object);
	
}
