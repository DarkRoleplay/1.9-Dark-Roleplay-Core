package net.dark_roleplay.core.addons.jei.simple_recipe;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.dark_roleplay.core.addons.jei.DRPCoreJEIAddon;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.core.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class Category_SimpleRecipe implements IRecipeCategory<SimpleRecipe>{

	public static final int width = 162;
	public static final int height = 54;

	private final IDrawable background;
	private final IGuiHelper guiHelper;

	private String uid;
	private String title;
	
	public Category_SimpleRecipe(IGuiHelper guiHelper, String uid, String title) {
		this.uid = uid;
		this.title = I18n.format("drp.category." + title + ".name");
		this.guiHelper = guiHelper;
		background = guiHelper.createDrawable(new ResourceLocation(References.MODID, "textures/guis/jei_background_sr.png"), 0, 0, width, height);
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public String getUid() {
		return this.uid;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getModName() {
		return References.NAME;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SimpleRecipe recipeWrapper, IIngredients ingredients) {
		int outputStart = 0;
		int outputSize = ingredients.getOutputs(ItemStack.class).size();
		int inputStart = outputSize;
		int inputSize = ingredients.getInputs(ItemStack.class).size();
//		final ICraftingGridHelper craftingGridHelper = guiHelper.createCraftingGridHelper(inputStart, outputStart);;

		
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
		
		for (int i = 0; i < outputSize; ++i) {
			guiItemStacks.init(i, false, (i % 9) * 18, Math.floorDiv(i, 9) * 18  + (int) (Math.ceil(inputSize/9D) * 18) + 18);
			guiItemStacks.set(i + outputStart, outputs.get(i));
		}

		for (int i = 0; i < inputSize; ++i) {
			guiItemStacks.init(i + inputStart, false, (i % 9) * 18, Math.floorDiv(i, 9) * 18);
			guiItemStacks.set(i + inputStart, inputs.get(i));
		}
	}
}
