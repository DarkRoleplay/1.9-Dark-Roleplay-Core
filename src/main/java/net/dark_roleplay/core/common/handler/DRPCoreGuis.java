package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.client.gui.crafting.recipe_creation.Container_RecipeCreation;
import net.dark_roleplay.core.client.gui.crafting.recipe_creation.Gui_RecipeCreation;
import net.dark_roleplay.core.common.DarkRoleplayCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DRPCoreGuis implements IGuiHandler {
	
	public static final int DRPCORE_GUI_SKILL_POINT_OVERVIEW = 3;
	public static final int DRPCORE_GUI_CRAFTING_RECIPECREATION = 4;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		switch(ID){
			case DRPCORE_GUI_CRAFTING_RECIPECREATION:
				return new Container_RecipeCreation(player.inventory);
			default:
				break;
				
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case DRPCORE_GUI_CRAFTING_RECIPECREATION:
			return new Gui_RecipeCreation(new Container_RecipeCreation(player.inventory));
	}
		return null;
	}

	public static void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(DarkRoleplayCore.instance, new DRPCoreGuis());
	}
}
