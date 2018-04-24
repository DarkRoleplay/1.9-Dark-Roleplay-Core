package net.dark_roleplay.drpcore.api.crafting;

import java.util.List;

import net.dark_roleplay.drpcore.api.old.modules.gui.IIcon;
import net.minecraft.item.ItemStack;

public interface IRecipe {

	public IIcon getIcon();
	
	public List<IIngredient> getIngredients();
	
	public List<ItemStack> getOutputs();
	
	public boolean craft();
}
