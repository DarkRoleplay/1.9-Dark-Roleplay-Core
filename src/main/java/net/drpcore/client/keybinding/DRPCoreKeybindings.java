package net.drpcore.client.keybinding;

import org.lwjgl.input.Keyboard;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.packets.PacketOpenCraftingGui;
import net.drpcore.common.network.packets.PacketOpenInventory;
import net.drpcore.common.network.packets.PacketOpenPurse;
import net.drpcore.common.network.packets.PacketSwitchArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;


public class DRPCoreKeybindings {

	public static KeyBinding openCrafting = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C, "Dark Roleplay Core");

	public static KeyBinding openInventory = new KeyBinding("keyBinding.openInventory", Keyboard.KEY_X, "Dark Roleplay Core");

	public static KeyBinding openPurse = new KeyBinding("keyBinding.openPurse", Keyboard.KEY_B, "Dark Roleplay Core");

	public static KeyBinding switchArmor = new KeyBinding("keyBinding.switchArmor", Keyboard.KEY_G, "Dark Roleplay Core");

	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {

		ClientRegistry.registerKeyBinding(openInventory);
		ClientRegistry.registerKeyBinding(openCrafting);
		ClientRegistry.registerKeyBinding(openPurse);
		//ClientRegistry.registerKeyBinding(switchArmor);
		MinecraftForge.EVENT_BUS.register(new DRPCoreKeybindings());
	}

	public static void postInit(FMLPostInitializationEvent event) {}

	@SubscribeEvent
	public void KeyInput(KeyInputEvent event) {

		if(this.openCrafting.isKeyDown()) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.openGui(DarkRoleplayCore.instance, GuiHandler.GUI_CRAFTING_RECIPESELECTION, player.worldObj, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
			//PacketHandler.sendToServer(new PacketOpenCraftingGui());
		} else if(this.openInventory.isKeyDown()) {
			PacketHandler.sendToServer(new PacketOpenInventory());
		} else if(this.openPurse.isKeyDown()) {
			AdvancedPlayerInventory inventory = Minecraft.getMinecraft().thePlayer.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
			if(inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE) != null && inventory.getStackInSlot(AdvancedPlayerInventory.SLOT_PURSE).getItem() instanceof PurseBase) {
				PacketHandler.sendToServer(new PacketOpenPurse());
			}
		} else if(this.switchArmor.isKeyDown()) {
			PacketHandler.sendToServer(new PacketSwitchArmor());
		}
	}
}
