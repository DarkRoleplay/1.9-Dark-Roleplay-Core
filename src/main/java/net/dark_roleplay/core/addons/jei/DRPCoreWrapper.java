package net.dark_roleplay.core.addons.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class DRPCoreWrapper implements IRecipeWrapper {

	@SuppressWarnings("unused")
	private SimpleRecipe recipe;
	
	protected List<List<ItemStack>> inputs = new ArrayList<List<ItemStack>>();
	protected List<List<ItemStack>> outputs = new ArrayList<List<ItemStack>>();
	
	public DRPCoreWrapper(SimpleRecipe recipe)
	{
		this.recipe = recipe;
		
		for(ItemStack stack : recipe.getMainIngredients())
		{
			inputs.add(Arrays.asList(stack));
		}
		
		for(ItemStack stack : recipe.getMainOutput())
		{
			outputs.add(Arrays.asList(stack));
		}
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
	
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputLists(ItemStack.class, outputs);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
		
	}

}
