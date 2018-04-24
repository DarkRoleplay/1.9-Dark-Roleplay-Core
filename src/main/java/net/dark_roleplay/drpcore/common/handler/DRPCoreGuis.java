package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.client.gui.crafting.recipe_creation.Container_RecipeCreation;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_creation.Gui_RecipeCreation;
import net.dark_roleplay.drpcore.client.gui.structure.Gui_StructureControll;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DRPCoreGuis implements IGuiHandler {
	
	public static final int DRPCORE_GUI_SKILL_POINT_OVERVIEW = 3;
	public static final int DRPCORE_GUI_CRAFTING_RECIPECREATION = 4;
	
	public static final int DRPCORE_GUI_STRUCTURE_CONTROLLER = 5;
	
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
		case DRPCORE_GUI_STRUCTURE_CONTROLLER:
			if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TE_BlueprintController){
				return new Gui_StructureControll((TE_BlueprintController) world.getTileEntity(new BlockPos(x, y, z)));
			}
			return null;
	}
		return null;
	}

	public static void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(DarkRoleplayCore.instance, new DRPCoreGuis());
	}
}
