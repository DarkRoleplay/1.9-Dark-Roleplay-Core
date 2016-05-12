package net.drpcore.common.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class AdvancedRecipe {

	/**
	 * Item Stack displayed as a preview in recipe selection screen
	 */
	private static ItemStack previewStack;
	
	private static ItemStack defaultOutput;
	
	private static ItemStack[] primaryIngredients;
	
	private static ItemStack[] secondaryIngredients;
	
	public AdvancedRecipe(ItemStack defaultOutput, ItemStack[] primaryIngredients){
		this(defaultOutput, defaultOutput,primaryIngredients, null);
	}
	
	public AdvancedRecipe(ItemStack previewStack, ItemStack defaultOutput, ItemStack[] primaryIngredients){
		this(previewStack, defaultOutput,primaryIngredients, null);
	}
	
	public AdvancedRecipe(ItemStack previewStack, ItemStack defaultOutput, ItemStack[] primaryIngredients, ItemStack[] secondaryIngredients){
		this.previewStack = previewStack;
		this.defaultOutput = defaultOutput;
		this.primaryIngredients = primaryIngredients;
		this.secondaryIngredients = secondaryIngredients;
	}
	
	public ItemStack[] getOutput(ItemStack[] primaryIngr, ItemStack[] secondaryIngr){
		return new ItemStack[] {defaultOutput};
	}
	
	public String createRegistryName(){
		
		String name = defaultOutput.getItem().getRegistryName().getResourcePath();
		String domain = defaultOutput.getItem().getRegistryName().getResourceDomain();
		
		String registryName = domain + "_" + name;
		
		for(ItemStack stack : primaryIngredients){
			char[] Chars = stack.getItem().getRegistryName().getResourcePath().toCharArray();
			String ingredientName = "";
			for(int i = 0; i < Chars.length; i += 2){
				ingredientName = ingredientName + Chars[i];
			}
			registryName = registryName + "_" + ingredientName;
			ingredientName = "";
		}
		return registryName;
	}
}
