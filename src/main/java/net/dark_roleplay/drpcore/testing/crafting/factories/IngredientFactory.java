package net.dark_roleplay.drpcore.testing.crafting.factories;

import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.testing.crafting.IIngredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class IngredientFactory extends IForgeRegistryEntry.Impl<IngredientFactory>{
	
	public abstract IIngredient readIngredient(JsonObject obj);

}
