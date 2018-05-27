package net.dark_roleplay.drpcore.testing.crafting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public interface IIngredient {
	
	public IIcon getIcon();
	
	public Block getCraftingStation();

	public boolean canCraft(EntityPlayer player, BlockPos pos);
	
}