package net.drpcore.common.craftingOld;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;


public class RecipeCategory {

	//-------------------------------------------------- Variables and Objects --------------------------------------------------
	private Item categoryIcon;

	private String unlocalizedName;

	//-------------------------------------------------- Constructors --------------------------------------------------
	public RecipeCategory(String unlocalizedName) {
		this(Item.getItemFromBlock(Blocks.BARRIER), unlocalizedName);
	}

	public RecipeCategory(Item icon, String unlocalizedName) {
		this.categoryIcon = icon;
		this.unlocalizedName = unlocalizedName;
	}

	//-------------------------------------------------- Getter --------------------------------------------------
	public Item getIcon() {

		return this.categoryIcon;
	}

	public String getUnlocalizedName() {

		return this.unlocalizedName;
	}

	//-------------------------------------------------- Setter --------------------------------------------------
	public void setIcon(Item icon) {

		this.categoryIcon = icon;
	}

	public void setUnlocalizedName(String name) {

		this.unlocalizedName = name;
	}
}
