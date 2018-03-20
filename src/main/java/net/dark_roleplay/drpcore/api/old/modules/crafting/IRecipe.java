package net.dark_roleplay.drpcore.api.old.modules.crafting;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IRecipe {
	
	public List<ItemStack> getPreviewItems();
	
	public List<ItemStack> getOutputs(List<ItemStack> consumedInputs);
	
	public List<ItemStack> getIngredients();
	
	public boolean canCraft(EntityPlayer player);
	
	public void deserialize(OutputStream os);
	
	public void serialize(InputStream is);
}
