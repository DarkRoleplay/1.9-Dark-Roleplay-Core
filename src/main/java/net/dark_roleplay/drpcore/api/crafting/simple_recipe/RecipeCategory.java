package net.dark_roleplay.drpcore.api.crafting.simple_recipe;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class RecipeCategory {

	private String categoryName;
	private Block craftingStation;
	private ItemStack previewStack;
	
	private ArrayList<IRecipe> recipes = new ArrayList<IRecipe>();
	
	public RecipeCategory(String categoryName){
		this(Blocks.AIR, categoryName);
	}
	
	public RecipeCategory(Block craftingStation, String categoryName){
		this(Blocks.AIR, categoryName, ItemStack.EMPTY);
	}
	
	public RecipeCategory(Block craftingStation, String categoryName, ItemStack previewStack){
		this.craftingStation = craftingStation;
		this.categoryName = categoryName;
		this.previewStack = previewStack;
	}
	
	public RecipeCategory add(IRecipe... recipe){
		for(IRecipe rec : recipe){
			add(rec);
		}
		
		return this;
	}
	
	public RecipeCategory add(IRecipe recipe){
		this.recipes.add(recipe);
		if(this.previewStack.isEmpty()){
			this.previewStack = recipe.getDisplayItems()[0];
		}
		return this;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Block getCraftingStation() {
		return craftingStation;
	}

	public ArrayList<IRecipe> getRecipes() {
		return recipes;
	}

	public ItemStack getPreviewStack() {
		return previewStack;
	}
	
	
}
