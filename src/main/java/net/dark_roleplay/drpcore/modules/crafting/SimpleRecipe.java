package net.dark_roleplay.drpcore.modules.crafting;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class SimpleRecipe implements IRecipe{

	private ItemStack[] previewStacks = new ItemStack[0];
	private ItemStack[] outputs = new ItemStack[0];
	private ItemStack[] ingredients = new ItemStack[0];
	
	protected void initalize(ItemStack[] previewStacks, ItemStack[] outputs, ItemStack[] ingredients){
		this.previewStacks = previewStacks;
		this.outputs = outputs;
		this.ingredients = ingredients;
	}
	
	@Override
	public List<Item> getPreviewItems() {
		return Arrays.asList(previewStacks);
	}

	@Override
	public List<ItemStack> getOutputs(List<ItemStack> consumedInputs) {
		return Arrays.asList(outputs);
	}

	@Override
	public List<ItemStack> getIngredients() {
		return Arrays.asList(ingredients);
	}

	@Override
	public boolean canCraft(EntityPlayer player) {
		return true;
	}

	
	@Override
	public void deserialize(OutputStream os) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serialize(InputStream is) {
		
		
	}
	
	public class Builder{
		
	}
}
