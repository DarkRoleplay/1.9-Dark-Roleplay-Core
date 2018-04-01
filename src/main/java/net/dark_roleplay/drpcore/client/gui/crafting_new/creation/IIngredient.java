package net.dark_roleplay.drpcore.client.gui.crafting_new.creation;

import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIngredient {
	
	public IIngredient readFromJson(JsonObject object);
	
	public boolean doesStationHave(World world, BlockPos pos, EntityPlayer player);
	
	public boolean doesPlayerHave(EntityPlayer player);
	
	public void useFromStation(World world, BlockPos pos, EntityPlayer player);
	
	public void useFromPlayer(EntityPlayer player);
	
	public IIcon getIcon();

}
