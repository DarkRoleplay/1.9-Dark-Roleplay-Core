package net.dark_roleplay.core.api.old.crafting2;

import net.dark_roleplay.core.testing.crafting.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IIngredient {
	
	public boolean doesStationHave(World world, BlockPos pos, EntityPlayer player);
	
	public boolean doesPlayerHave(EntityPlayer player);
	
	public void takeFromStation(World world, BlockPos pos, EntityPlayer player);
	
	public void takeFromPlayer(EntityPlayer player);
	
	public IIcon getIcon();

}
