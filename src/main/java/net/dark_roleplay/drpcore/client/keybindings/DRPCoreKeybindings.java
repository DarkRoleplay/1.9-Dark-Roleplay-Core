package net.dark_roleplay.drpcore.client.keybindings;

import java.io.File;

import org.lwjgl.input.Keyboard;

import net.dark_roleplay.drpcore.api.schematic.Schematic;
import net.dark_roleplay.drpcore.api.schematic.SchematicUtil;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipeLoader;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.debug.Packet_DebugKey;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class DRPCoreKeybindings {
	
	public static KeyBinding openCrafting = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C,"Dark Roleplay Core");
	public static KeyBinding openSkill = new KeyBinding("keyBinding.openSkill", Keyboard.KEY_K, "Dark Roleplay Core");
	public static KeyBinding debugging = new KeyBinding("keyBinding.debuging", Keyboard.KEY_B, "Dark Roleplay Core");

	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(openCrafting);
		ClientRegistry.registerKeyBinding(openSkill);
		ClientRegistry.registerKeyBinding(debugging);
		MinecraftForge.EVENT_BUS.register(new DRPCoreKeybindings());
	}

	public static void postInit(FMLPostInitializationEvent event) {
	}

	@SubscribeEvent
	public void KeyInput(KeyInputEvent event) {

		if(this.openCrafting.isKeyDown()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPESELECTION, player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		}
		
		if(this.openSkill.isKeyDown()){
			EntityPlayer player = Minecraft.getMinecraft().player;
			player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_SKILL_POINT_OVERVIEW, player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
		}
		
		if(this.debugging.isKeyDown()) {
			Minecraft.getMinecraft().player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPECREATION, Minecraft.getMinecraft().world, 0, 0, 0);
//			DRPCorePackets.sendToServer(new Packet_DebugKey());
		}
	}
	
}
