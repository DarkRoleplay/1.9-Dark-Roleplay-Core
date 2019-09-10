package net.dark_roleplay.core.addons.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.IRecipe;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.core.common.crafting.CraftingRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DRPCoreJEICategory implements IRecipeCategory<DRPCoreWrapper> {

	private static ResourceLocation loc = new ResourceLocation(References.MODID, "textures/guis/jei_background_sr.png");
	private static ResourceLocation inventoryIcon = new ResourceLocation(References.MODID, "textures/guis/jei_inventory_crafting.png");

	private Block station;
	private IGuiHelper helper;

	public DRPCoreJEICategory(Block craftingStation, IGuiHelper h) {
		station = craftingStation;
		helper = h;
	}

	@Override
	public String getUid() {
		return station != Blocks.AIR ? station.getRegistryName().toString() : References.MODID + ":drp_inventory" ;
	}

	@Override
	public String getTitle() {
		return station != Blocks.AIR ? station.getLocalizedName() : "Inventory Crafting";
	}

	@Override
	public String getModName() {
		return References.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(loc, 0, 0, 162, 54);
	}
	
	@Override
	public IDrawable getIcon() {
		if(station == Blocks.AIR)
		{
			return helper.createDrawable(inventoryIcon, 0, 0, 16, 16, 16, 16);
		}
		
		return null;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DRPCoreWrapper recipeWrapper, IIngredients ingredients) {

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();

		for (int i = 0; i < 9; i++) {
			guiStacks.init(i, true, (i * 18), 0);
			guiStacks.init(i + 9, false, (i * 18), 36);
		}

		for (int i = 0; i < 9; i++) {
			if (i < inputs.size())
				guiStacks.set(i, inputs.get(i));
			if (i < outputs.size())
				guiStacks.set(i + 9, outputs.get(i));
		}

	}

	public List<DRPCoreWrapper> getRecipes() {
		List<DRPCoreWrapper> wrappedRecipes = new ArrayList<DRPCoreWrapper>();

		List<RecipeCategory> categories = CraftingRegistry.getCategorysForBlocks(station);
		for (RecipeCategory cat : categories) {
			List<IRecipe> recipes = cat.getRecipes();
			for (IRecipe recipe : recipes) {
				if (recipe instanceof SimpleRecipe)
					wrappedRecipes.add(new DRPCoreWrapper((SimpleRecipe) recipe));
			}
		}

		return wrappedRecipes;
	}

	public Block getBlockIcon() {
		return station;
	}

}
