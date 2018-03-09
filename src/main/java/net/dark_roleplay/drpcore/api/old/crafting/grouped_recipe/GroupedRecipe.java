package net.dark_roleplay.drpcore.api.old.crafting.grouped_recipe;

import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.crafting.RecipeGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@Deprecated
public class GroupedRecipe extends SimpleRecipe {

	public GroupedRecipe(RecipeGroup group, ResourceLocation registryName, ItemStack[] mainOutput, ItemStack[] mainIngredients) {
		super(registryName, mainOutput, mainIngredients);
	}

	public GroupedRecipe(RecipeGroup group, ResourceLocation registryName, Block station, String category, ItemStack[] mainOutput,ItemStack[] mainIngredients) {
		super(registryName, mainOutput, mainIngredients);
	}

}
