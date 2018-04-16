package net.dark_roleplay.drpcore.api.crafting.item_ingredient;

import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.crafting.IIngredient;
import net.dark_roleplay.drpcore.client.gui.crafting_new.creation.IIcon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
