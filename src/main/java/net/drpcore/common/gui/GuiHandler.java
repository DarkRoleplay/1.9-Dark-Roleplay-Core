package net.drpcore.common.gui;

import net.drpcore.client.gui.guis.AdvancedPlayerInventoryGui;
import net.drpcore.client.gui.guis.CraftingGui;
import net.drpcore.client.gui.guis.PurseGui;
import net.drpcore.client.gui.guis.crafting.RecipeSelection;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.entities.player.advancedInventory.AdvancedInventoryProvider;
import net.drpcore.common.gui.container.Container_Crafting;
import net.drpcore.common.gui.container.Container_Purse;
import net.drpcore.common.gui.container.AdvancedPlayerInventoryContainer;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.gui.inventories.PurseInventory;
import net.drpcore.common.items.templates.PurseBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler {

	public static final int GUI_INVENTORY = 0;

	public static final int GUI_CRAFTING = 1;

	public static final int GUI_PURSE = 2;

	public static final int GUI_CRAFTING_RECIPESELECTION = 3;

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
				return null;
			case GUI_CRAFTING_RECIPESELECTION:
				return null;
			default:
				return null;
		}
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
				return null;
			case GUI_CRAFTING_RECIPESELECTION:
				return new RecipeSelection(Blocks.stone);
			default:
				return null;
		}
	}
}
