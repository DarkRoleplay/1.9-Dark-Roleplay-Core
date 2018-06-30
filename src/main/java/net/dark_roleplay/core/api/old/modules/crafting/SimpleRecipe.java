package net.dark_roleplay.core.api.old.modules.crafting;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class SimpleRecipe implements IRecipe{

	private ItemStack[] previewStacks = new ItemStack[0];
	private ItemStack[] outputs = new ItemStack[0];
	private ItemStack[] ingredients = new ItemStack[0];
	
	protected SimpleRecipe(ItemStack[] previewStacks, ItemStack[] outputs, ItemStack[] ingredients){
		this.previewStacks = previewStacks;
		this.outputs = outputs;
		this.ingredients = ingredients;
	}
	
	@Override
	public List<ItemStack> getPreviewItems() {
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
		return PlayerInventoryHelper.hasItems(player, ingredients.clone()) == null ? true : false;
	}

	
	@Override
	public void deserialize(OutputStream os) {
		
	}

	@Override
	public void serialize(InputStream is) {
		
	}
	
	public class Builder{
		
		private ItemStack[] previewStacks;
		private ItemStack[] outputs;
		private ItemStack[] ingredients;
		
		public SimpleRecipe build(){
			return new SimpleRecipe(previewStacks == null ? outputs.clone() : previewStacks, outputs, ingredients);
		}
		
		public void setPreviewStacks(ItemStack... itemStacks){
			previewStacks = itemStacks;
		}
		
		public void setOutputs(ItemStack... itemStacks){
			outputs = itemStacks;
		}
		
		public void setIngredients(ItemStack... itemStacks){
			ingredients = itemStacks;
		}
	}
}
