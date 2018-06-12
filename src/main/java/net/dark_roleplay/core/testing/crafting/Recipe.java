package net.dark_roleplay.core.testing.crafting;

import net.minecraft.block.Block;

public abstract class Recipe {

	private final boolean isModded;
	
	private int id = -1;
	private boolean hasID = false;
	private String registryName;
	
	public Recipe(String registryName, boolean isModded) {
		this.isModded = isModded;
		this.registryName = registryName;
	}
	
	public final boolean isModded() {
		return this.isModded;
	}
	
	public final String getRegistryName() {
		return this.registryName;
	}
	
	public final void setID(int id) {
		if(!hasID) {
			this.id = id;
			this.hasID = true;
		}
	}
	
	public final void removeID() {
		if(hasID) {
			this.id = -1;
			this.hasID = false;
		}
	}
	
	public abstract IIcon getIcon();
	
	public abstract boolean isAllowedCraftingStation(Block station);
	
	public abstract void setCraftingStations(Block[] stations);
}
