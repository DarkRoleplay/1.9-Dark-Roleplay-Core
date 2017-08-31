package net.dark_roleplay.drpcore.addons.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DRPCoreWrapper extends BlankRecipeWrapper {

	private final DRPCoreJEIRecipe recipe;

	public DRPCoreWrapper(DRPCoreJEIRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> out = recipe.getOutputs();
		List<ItemStack> in = new ArrayList<ItemStack>();
		in.add(recipe.getStation());
		for(ItemStack stack : recipe.getInputs()){
			in.add(stack);
		}
		ingredients.setOutputs(ItemStack.class, out);
		ingredients.setInputs(ItemStack.class, in);
	}
}
