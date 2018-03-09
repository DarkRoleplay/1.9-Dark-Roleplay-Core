package net.dark_roleplay.drpcore.common.crafting;

import java.util.List;

import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.SimpleRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class RecipeGroup {
	
	private String registryName;

	private Block station;

	private String category;
	
	private boolean requiresUnlock;
	
	private List<ItemStack> previewStacks;
	
	private List<SimpleRecipe> recipes;

	
	public void addRecipe(SimpleRecipe recipe){
		this.recipes.add(recipe);
		this.previewStacks.add(recipe.getMainOutput()[0]);
	}
	
	public RecipeGroup setStation(Block station){
		this.station = station;
		return this;
	}
	
	public RecipeGroup setRequiresUnlock(boolean requiresUnlock){
		this.requiresUnlock = requiresUnlock;
		return this;
	}
	
	public RecipeGroup setCategory(String category){
		this.category = category;
		return this;
	}

	public String getRegistryName() {
		return registryName;
	}

	public Block getStation() {
		return station;
	}

	public String getCategory() {
		return category;
	}

	public boolean isRequiresUnlock() {
		return requiresUnlock;
	}

	public List<ItemStack> getPreviewStacks() {
		return previewStacks;
	}

	public List<SimpleRecipe> getRecipes() {
		return recipes;
	}
	
	
}
