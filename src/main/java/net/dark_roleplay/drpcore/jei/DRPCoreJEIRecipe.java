package net.dark_roleplay.drpcore.jei;

import java.util.List;

import net.minecraft.item.ItemStack;

public class DRPCoreJEIRecipe {

	private List<ItemStack> outputs;
	private List<ItemStack> inputs;
	
	private ItemStack station;
	
	public DRPCoreJEIRecipe(ItemStack station, List<ItemStack> outputs, List<ItemStack> inputs){
		this.outputs = outputs;
		this.inputs = inputs;
		this.station = station;
	}

	public List<ItemStack> getOutputs() {
		return outputs;
	}

	public ItemStack getStation() {
		return station;
	}

	public List<ItemStack> getInputs() {
		return inputs;
	}
	
	
	
}
