package net.dark_roleplay.drpcore.testing.crafting.factories;

import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.testing.crafting.Recipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class RecipeFactory extends IForgeRegistryEntry.Impl<RecipeFactory>{
	
	public abstract Recipe readRecipe(Boolean isModded, JsonObject obj);

}
