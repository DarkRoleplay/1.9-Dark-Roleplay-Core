package net.dark_roleplay.core.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dark_roleplay.core.common.crafting.CraftingRegistry;
import net.dark_roleplay.core.compat.jei.simple_recipe.Category_SimpleRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class DRPCoreJEIAddon implements IModPlugin{
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		for(Block b : CraftingRegistry.getStations()){
			registry.addRecipeCategories(new Category_SimpleRecipe(registry.getJeiHelpers().getGuiHelper(), "drpcore_jet_" + b.getRegistryName().getResourcePath(), b.getRegistryName().getResourcePath()));
		}
	}
	
	@Override
	public void register(IModRegistry registry) {
		for(Block b : CraftingRegistry.getStations()){
			registry.addRecipes(CraftingRegistry.getRecipesForStation(b), "drpcore_jet_" + b.getRegistryName().getResourcePath());

			if(b != Blocks.AIR)
				registry.addRecipeCatalyst(new ItemStack(b), "drpcore_jet_" + b.getRegistryName().getResourcePath());
			else
				registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), "drpcore_jet_" + b.getRegistryName().getResourcePath());
		}
	}
}
