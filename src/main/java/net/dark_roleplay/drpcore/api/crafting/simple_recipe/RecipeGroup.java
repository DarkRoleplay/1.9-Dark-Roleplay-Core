package net.dark_roleplay.drpcore.api.crafting.simple_recipe;

import net.minecraft.util.ResourceLocation;

public class RecipeGroup extends IRecipe.Impl{

	public RecipeGroup(ResourceLocation registryName, boolean requiresUnlock){
	
		this.registryName = registryName;
		this.requiresUnlock = requiresUnlock;
	}
	
}
