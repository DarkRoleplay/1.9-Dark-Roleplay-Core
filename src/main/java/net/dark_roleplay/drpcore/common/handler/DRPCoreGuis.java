package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DRPCoreGuis implements IGuiHandler {
	
	public static final int DRPCORE_GUI_CRAFTING_RECIPESELECTION = 1;
	public static final int DRPCORE_GUI_CRAFTING_RECIPECRAFTING_SIMPLE = 2;
	
	//TODO GUI HANDLER
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		switch(ID){
			case 1:
				break;
			case 2:
				break;
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case 1:
			return new RecipeSelection(new BlockPos(x,y,z));
		case 2:
			return new RecipeCrafting_SimpleRecipe(new BlockPos(x,y,z));
	}
		return null;
	}
	
	
	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(DarkRoleplayCore.instance, new DRPCoreGuis());
	}

	public static void postInit(FMLPostInitializationEvent event) {}

}
