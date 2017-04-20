package net.dark_roleplay.drpcore.jei;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DRPCoreCategory extends BlankRecipeCategory<DRPCoreWrapper> {

	@Nonnull
	private final IDrawable background;

	public DRPCoreCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/jei/jei_crafting.png");
		background = guiHelper.createDrawable(location, 0, 0, 143, 54, 0, 4, -16, 0);
	}

	@Override
	public String getUid() {
		return DRPCoreJEIAddon.DRPCoreCategory;
	}

	@Override
	public String getTitle() {
		return "Dark Roleplay Crafting";
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DRPCoreWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		guiItemStacks.init(0, true, 46, 18);

		for (int i = 0; i < 9; i++) {
			guiItemStacks.init(i + 1, true, ((i % 3) * 18) - 16, (i / 3) * 18);
		}

		for (int i = 0; i < 9; i++) {
			guiItemStacks.init(i + 10, false, 73 + ((i % 3) * 18), (i / 3) * 18);
		}

		for (int i = 0; i < inputs.size() && i < 10; i++) {
			List<ItemStack> input = inputs.get(i);
			if (input != null) {
				guiItemStacks.set(i, input);
			}
		}

		for (int i = 0; i < outputs.size() && i < 9; i++) {
			List<ItemStack> output = outputs.get(i);
			if (output != null) {
				guiItemStacks.set(i + 10, output);
			}
		}

	}

}
