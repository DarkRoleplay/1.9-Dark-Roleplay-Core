package net.dark_roleplay.drpcore.addons.jei;

import java.util.List;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.minecraft.item.ItemStack;

public class DRPCoreHandler implements IRecipeHandler<DRPCoreJEIRecipe> {

	@Override
	public Class getRecipeClass() {
		return DRPCoreJEIRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(DRPCoreJEIRecipe recipe) {
		return DRPCoreJEIAddon.DRPCoreCategory;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(DRPCoreJEIRecipe recipe) {
		return new DRPCoreWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(DRPCoreJEIRecipe recipe) {
		return true;
	}

}
