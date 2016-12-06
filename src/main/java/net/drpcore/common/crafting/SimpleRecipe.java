package net.drpcore.common.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class SimpleRecipe {

	private String registryName;
	
	private ItemStack[] mainIngredients;
	
	private ItemStack[] mainOutput;
	
	private Block station;

	private String category;
	
	private boolean requiresUnlock = false;
	
	public SimpleRecipe(String registryName, Block station, String category, ItemStack[] mainOutput, ItemStack[] mainIngredients){
		this(registryName, mainOutput, mainIngredients);
		this.station = station;
		this.category = category;
	}
	
	public SimpleRecipe(String registryName, ItemStack[] mainOutput, ItemStack[] mainIngredients){
		this.registryName = registryName;
		this.mainOutput = mainOutput;
		this.mainIngredients = mainIngredients;
	}
	
	public String getRegistryName(){
		return this.registryName;
	}

	public ItemStack[] getMainIngredients(){
		return mainIngredients;
	}

	public ItemStack[] getMainOutput(){
		return mainOutput;
	}

	public Block getStation(){
		return station;
	}

	public String getCategory() {
		return category;
	}
	
	public boolean requiresUnlock(){
		return requiresUnlock;
	}

	//------------------------------------------------------------ Setters ------------------------------------------------------------

	public SimpleRecipe setStation(Block station){
		this.station = station;
		return this;
	}
	
	public SimpleRecipe setRequiresUnlock(boolean requiresUnlock){
		this.requiresUnlock = requiresUnlock;
		return this;
	}
	
	public SimpleRecipe setCategory(String category){
		this.category = category;
		return this;
	}
	
	
}
