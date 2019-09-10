package net.dark_roleplay.core.addons.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dark_roleplay.core.common.crafting.CraftingRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class DRPCoreJEIAddon implements IModPlugin {

	public static IRecipeRegistry reg;

	public static List<DRPCoreWrapper> allRecipes = new ArrayList<DRPCoreWrapper>();

	private static List<DRPCoreJEICategory> drpCategories = new ArrayList<DRPCoreJEICategory>();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		for (Block station : CraftingRegistry.getStations()) {
			if (station != null) {
				DRPCoreJEICategory cat = new DRPCoreJEICategory(station, registry.getJeiHelpers().getGuiHelper());
				drpCategories.add(cat);
			}
		}

		registry.addRecipeCategories(drpCategories.toArray(new DRPCoreJEICategory[drpCategories.size()]));
	}

	@Override
	public void register(IModRegistry registry) {

		for (DRPCoreJEICategory cat : drpCategories) {
			registry.addRecipes(cat.getRecipes(), cat.getUid());
			if (cat.getBlockIcon() != null && cat.getBlockIcon() != Blocks.AIR)
				registry.addRecipeCatalyst(new ItemStack(cat.getBlockIcon()), cat.getUid());
		}
	}
}
