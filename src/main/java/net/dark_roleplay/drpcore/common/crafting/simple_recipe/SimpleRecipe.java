package net.dark_roleplay.drpcore.common.crafting.simple_recipe;

import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SimpleRecipe {

	private String registryName;
	
	private ItemStack[] mainIngredients;
	
	private ItemStack[] mainOutput;
	
	private boolean[] ignoreMeta;
	
	private Block station;

	private String category;
	
	private boolean requiresUnlock = false;
	
	private SimpleCrafter crafter;

	public SimpleRecipe(ResourceLocation registryName, Block station, String category, ItemStack[] mainOutput, ItemStack[] mainIngredients){
		this(registryName, mainOutput, mainIngredients);
		this.station = station;
		this.category = category;
		this.crafter = CraftingRegistry.SIMPLE_CRAFTER_INSTANCE;
	}
	
	public SimpleRecipe(ResourceLocation registryName, ItemStack[] mainOutput, ItemStack[] mainIngredients){
		this.registryName = registryName.getResourceDomain() + ":" + registryName.getResourcePath();
		this.mainOutput = mainOutput;
		this.mainIngredients = mainIngredients;
		this.ignoreMeta = new boolean[mainIngredients.length];
		this.crafter = new SimpleCrafter();
		
		for(int i = 0; i < this.ignoreMeta.length; i++){
			this.ignoreMeta[i] = false; 
		}
	}
	
	//------------------------------------------------------------ Getter ------------------------------------------------------------

	public boolean[] getIgnoreMeta() {
		return ignoreMeta;
	}

	public String getRegistryName(){
		return this.registryName;
	}

	public ItemStack[] getMainIngredients(){
		return mainIngredients.clone();
	}

	public ItemStack[] getMainOutput(){
		return mainOutput.clone();
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
	
	
	public SimpleCrafter getCrafter() {
		return crafter;
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
	
	public void setCrafter(SimpleCrafter crafter) {
		this.crafter = crafter;
	}
	
}
