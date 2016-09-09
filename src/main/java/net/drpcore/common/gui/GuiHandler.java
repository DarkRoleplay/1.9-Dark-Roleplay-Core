package net.drpcore.common.gui;

import net.drpcore.api.items.equip.PurseBase;
import net.drpcore.client.gui.crafting.guis.RecipeCrafting;
import net.drpcore.client.gui.crafting.guis.RecipeSelection;
import net.drpcore.client.gui.guis.AdvancedPlayerInventoryGui;
import net.drpcore.client.gui.guis.CraftingGui;
import net.drpcore.client.gui.guis.PurseGui;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.entities.player.advancedInventory.AdvancedInventoryProvider;
import net.drpcore.common.crafting.AdvancedRecipe;
import net.drpcore.common.crafting.CraftingController;
import net.drpcore.common.crafting.DRPCoreCrafting;
import net.drpcore.common.gui.container.Container_Crafting;
import net.drpcore.common.gui.container.Container_Purse;
import net.drpcore.common.gui.container.AdvancedPlayerInventoryContainer;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.gui.inventories.PurseInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler {
	
	public static final int GUI_INVENTORY = 0;

	public static final int GUI_CRAFTING = 1;

	public static final int GUI_PURSE = 2;

	public static final int GUI_CRAFTING_RECIPESELECTION = 3;
	
	public static final int GUI_CRAFTING_RECIPECRAFTING = 4;

	private static String craftingCategory;
	private static AdvancedRecipe craftingRecipe;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
			case GUI_INVENTORY:
				return new AdvancedPlayerInventoryContainer(player, player.inventory, new AdvancedPlayerInventory((AdvancedPlayerInventory) player.getCapability(AdvancedInventoryProvider.ADVANCED_INVENTORY, null).getInventory(), player));
			case GUI_CRAFTING:
				return new Container_Crafting(player.inventory);
			case GUI_PURSE:
				AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
				if(inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE) != null && inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE).getItem() instanceof PurseBase) {
					ItemStack purse = inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE);
					return new Container_Purse(player, player.inventory, new PurseInventory(purse, ((PurseBase) purse.getItem()).SlotAmount));
				}
				break;
			case GUI_CRAFTING_RECIPESELECTION:
				return null;
			case GUI_CRAFTING_RECIPECRAFTING:
				break;
			default:
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		switch (ID) {
			case GUI_INVENTORY:
				return new AdvancedPlayerInventoryGui(player, player.inventory, new AdvancedPlayerInventory((AdvancedPlayerInventory) player.getCapability(AdvancedInventoryProvider.ADVANCED_INVENTORY, null).getInventory(), player));
			case GUI_CRAFTING:
				return new CraftingGui(new Container_Crafting(player.inventory), player, world.getBlockState(new BlockPos(x, y, z)).getBlock());
			case GUI_PURSE:
				AdvancedPlayerInventory inventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
				if(inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE) != null && inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE).getItem() instanceof PurseBase) {
					ItemStack purse = inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE);
					return new PurseGui((Container_Purse) new Container_Purse(player, player.inventory, new PurseInventory(purse, ((PurseBase) purse.getItem()).SlotAmount)));
				}
				break;
			case GUI_CRAFTING_RECIPESELECTION:
				if(CraftingController.isStation(player.worldObj.getBlockState(new BlockPos(x,y,z)).getBlock()))
					return new RecipeSelection(player.worldObj.getBlockState(new BlockPos(x,y,z)).getBlock(), player);
				else return new RecipeSelection(Blocks.AIR,player);
			case GUI_CRAFTING_RECIPECRAFTING:
				return new RecipeCrafting(new Container_Crafting(player.inventory), world.getBlockState(new BlockPos(x,y,z)).getBlock(),this.craftingCategory,this.craftingRecipe ,player);
			default:
				break;
		}
		return null;
	}
	
	public static void setRecipe(String category, AdvancedRecipe recipe){
		craftingCategory = category;
		craftingRecipe = recipe;
	}
}
