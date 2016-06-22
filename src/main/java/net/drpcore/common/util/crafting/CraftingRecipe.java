package net.drpcore.common.util.crafting;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Deprecated
public class CraftingRecipe {

	private ItemStack DefaultOutput;

	private ItemStack[] MainIngredients;

	private ItemStack[] AdditionalIngredients = null;

	private Block CraftingStation;

	private String Category = null;

	private int CraftingTime = 0;

	private boolean ignoreMeta = false;

	// private Skill needetSkill;
	/**
	 * @param craftingStation
	 *            The block needed null for Handcrafted
	 * @param Category
	 *            The Category you will find the Recipe choose what you want. Not
	 *            existing Categorys will be added
	 * @param output
	 *            The Output ItemStack
	 * @param mainIngredients
	 *            The List of Items that is necesary for the Recipe example: {new
	 *            ItemStack(Items.apple,1), new ItemStack(Items.sugar,1);}
	 */
	public CraftingRecipe(Block craftingStation, String Category, ItemStack output, ItemStack[] mainIngredients, ItemStack[] additionalIngredients) {
		if(craftingStation == null)
			craftingStation = Blocks.air;
		this.CraftingStation = craftingStation;
		if(Category == null)
			Category = "Default Category";
		this.Category = Category;
		if(output == null)
			output = new ItemStack(Blocks.bedrock);
		this.setDefaultOutput(output);
		if(mainIngredients == null) {
			ItemStack[] DefaultIngredients = {new ItemStack(Blocks.bedrock)};
			mainIngredients = DefaultIngredients;
		}
		if(additionalIngredients != null) {
			this.AdditionalIngredients = additionalIngredients;
		}
		ItemStack[] Test = {new ItemStack(Items.apple)};
		this.MainIngredients = mainIngredients;
	}

	public void setStation(Block station) {

		this.CraftingStation = station;
	}

	public void setCategory(String category) {

		this.Category = category;
	}

	/**
	 * Override to change Output depending on Additional Ingredients
	 * 
	 * @param addIngredients
	 * @return ItemStack depending on
	 */
	public ItemStack getOutput(ItemStack[] addIngredients) {

		return this.getDefaultOutput();
	}

	public ItemStack getDefaultOutput() {

		return DefaultOutput;
	}

	public void setDefaultOutput(ItemStack output) {

		DefaultOutput = output;
	}

	public void setAdditionalIngredients(ItemStack[] Ingredients) {

		AdditionalIngredients = Ingredients;
	}

	public ItemStack[] getMainIngredients() {

		return this.MainIngredients;
	}

	public ItemStack[] getAdditionalIngredients() {

		return this.AdditionalIngredients;
	}

	public Block getStation() {

		return this.CraftingStation;
	}

	public String getCategory() {

		return this.Category;
	}

	public int getCraftingTime() {

		return this.CraftingTime;
	}

	public void ignoreMeta(boolean ignore) {

		this.ignoreMeta = ignore;
	}

	public boolean shouldIgnoreMeta() {

		return this.ignoreMeta;
	}
}
