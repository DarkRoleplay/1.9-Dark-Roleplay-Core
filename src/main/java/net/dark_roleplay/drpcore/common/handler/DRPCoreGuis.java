package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_creation.Container_RecipeCreation;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_creation.Gui_RecipeCreation;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.client.gui.inventory.Gui_ExtendedInventory;
import net.dark_roleplay.drpcore.client.gui.structure.Gui_StructureControll;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.containers.Container_ExtendedInventory;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.items.CapabilityItemHandler;

public class DRPCoreGuis implements IGuiHandler {
	
	public static final int DRPCORE_GUI_SKILL_POINT_OVERVIEW = 3;
	public static final int DRPCORE_GUI_CRAFTING_RECIPECREATION = 4;
	
	public static final int DRPCORE_GUI_STRUCTURE_CONTROLLER = 5;
	
	public static final int DRPCORE_GUI_EXTENDED_INVENTORY = 6;
	
	//TODO GUI HANDLER
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		switch(ID){
			case DRPCORE_GUI_CRAFTING_RECIPECREATION:
				return new Container_RecipeCreation(player.inventory);
			case DRPCORE_GUI_EXTENDED_INVENTORY:
				return new Container_ExtendedInventory(player.inventory, player, player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
			default:
				break;
				
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
//		case DRPCORE_GUI_SKILL_POINT_OVERVIEW:
//			return new SkillOverview();
		case DRPCORE_GUI_CRAFTING_RECIPECREATION:
			return new Gui_RecipeCreation(new Container_RecipeCreation(player.inventory));
		case DRPCORE_GUI_STRUCTURE_CONTROLLER:
			if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TE_BlueprintController){
				return new Gui_StructureControll((TE_BlueprintController) world.getTileEntity(new BlockPos(x, y, z)));
			}
			return null;
		case DRPCORE_GUI_EXTENDED_INVENTORY:
			return new Gui_ExtendedInventory(new Container_ExtendedInventory(player.inventory, player, player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)));
	}
		return null;
	}
	
	
	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(DarkRoleplayCore.instance, new DRPCoreGuis());
	}

	public static void postInit(FMLPostInitializationEvent event) {}

}
