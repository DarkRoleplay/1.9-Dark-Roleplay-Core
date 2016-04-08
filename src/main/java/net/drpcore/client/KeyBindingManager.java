package net.drpcore.client;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.ExtendedPlayer;
import net.drpcore.common.gui.inventories.PlayerInventory;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.PacketOpenCraftingGui;
import net.drpcore.common.network.PacketOpenInventory;
import net.drpcore.common.network.PacketOpenPurse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;



public class KeyBindingManager {

	public static KeyBinding openCrafting = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C, "Dark Roleplay Core");
	public static KeyBinding openInventory = new KeyBinding("keyBinding.openInventory", Keyboard.KEY_X, "Dark Roleplay Core");
	
	public static KeyBinding openPurse = new KeyBinding("keyBinding.openPurse",Keyboard.KEY_B,"Dark Roleplay Core");
	
	@SubscribeEvent
	public void KeyInput(KeyInputEvent event){
		if(this.openCrafting.isKeyDown()){
			PacketHandler.sendToServer(new PacketOpenCraftingGui());
		}
		else if(this.openInventory.isKeyDown()){
			PacketHandler.sendToServer(new PacketOpenInventory());
		}else if(this.openPurse.isKeyDown()){
			PlayerInventory inventory = Minecraft.getMinecraft().thePlayer.getCapability(DarkRoleplayCore.getDrpcoreInv(), null).getInventory();
			if(inventory.getStackInSlot(PlayerInventory.SLOT_PURSE) != null && inventory.getStackInSlot(PlayerInventory.SLOT_PURSE).getItem() instanceof PurseBase){
				PacketHandler.sendToServer(new PacketOpenPurse());
			}
		}
	}
}
