package net.dark_roleplay.drpcore.client.gui.crafting_new.creation;

import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemIngredient implements IIngredient{

	@Override
	public IIngredient readFromJson(JsonObject object) {
		// TODO Auto-generated method stub
		return null;
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
	public void useFromStation(World world, BlockPos pos, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useFromPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
