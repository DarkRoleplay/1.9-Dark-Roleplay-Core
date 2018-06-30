package net.dark_roleplay.core.api.old.crafting2.item_ingredient;

import net.dark_roleplay.core.api.old.crafting2.IIngredient;
import net.dark_roleplay.core.testing.crafting.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemIngredient implements IIngredient{

	private ItemStack stack = null;
	
	protected ItemIngredient(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean doesStationHave(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesPlayerHave(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void takeFromStation(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeFromPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
