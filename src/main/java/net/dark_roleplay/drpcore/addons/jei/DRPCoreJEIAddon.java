package net.dark_roleplay.drpcore.addons.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dark_roleplay.drpcore.addons.jei.simple_recipe.Category_SimpleRecipe;
import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.IRecipe;
import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JEIPlugin
public class DRPCoreJEIAddon implements IModPlugin{
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		for(Block b : CraftingRegistry.getStations()){
			System.out.println(b);
			registry.addRecipeCategories(new Category_SimpleRecipe(registry.getJeiHelpers().getGuiHelper(), "drpcore_jet_" + b.getRegistryName().getResourcePath(), b.getRegistryName().getResourcePath()));
		}
	}
	
	@Override
	public void register(IModRegistry registry) {
		for(Block b : CraftingRegistry.getStations()){
			System.out.println(b);
			registry.addRecipes(CraftingRegistry.getRecipesForStation(b), "drpcore_jet_" + b.getRegistryName().getResourcePath());

			if(b != Blocks.AIR)
				registry.addRecipeCatalyst(new ItemStack(b), "drpcore_jet_" + b.getRegistryName().getResourcePath());
			else
				registry.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), "drpcore_jet_" + b.getRegistryName().getResourcePath());
		}
	}
}
