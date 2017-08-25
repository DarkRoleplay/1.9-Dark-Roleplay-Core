package net.dark_roleplay.drpcore.client.keybindings;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.properties.Property;

import net.dark_roleplay.drpcore.api.crafting.Crafting_Util;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.api.schematic.Schematic;
import net.dark_roleplay.drpcore.api.schematic.SchematicUtil;
import net.dark_roleplay.drpcore.api.skills.Skill_Util;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.client.resources.creation.TextureCombiner;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipeLoader;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.debug.Packet_DebugKey;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.dark_roleplay.drpcore.common.util.jsons.Json_Premium;
import net.dark_roleplay.drpcore.common.util.web.WebRequest_PremiumPoints;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.gui.toasts.TutorialToast.Icons;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class DRPCoreKeybindings {
	
	public static KeyBinding openCrafting = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C,"Dark Roleplay Core");
//	public static KeyBinding openSkill = new KeyBinding("keyBinding.openSkill", Keyboard.KEY_K, "Dark Roleplay Core");
	public static KeyBinding debugging = new KeyBinding("keyBinding.debuging", Keyboard.KEY_B, "Dark Roleplay Core");

	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(openCrafting);
//		ClientRegistry.registerKeyBinding(openSkill);
		
		if(DRPCoreConfigs.DEBUG.DEBUG_KEY){
			ClientRegistry.registerKeyBinding(debugging);
		}
		
		MinecraftForge.EVENT_BUS.register(new DRPCoreKeybindings());
	}

	public static void postInit(FMLPostInitializationEvent event) {
	}

	IToast globalToast;

	
	@SubscribeEvent
	public void KeyInput(KeyInputEvent event) {

		if(this.openCrafting.isKeyDown()) {
			Crafting_Util.openRecipeSelection(Blocks.AIR);
		}
		
//		if(this.openSkill.isKeyDown()){
//			Skill_Util.openSkillOverview();
//		}
		
		if(DRPCoreConfigs.DEBUG.DEBUG_KEY && this.debugging.isKeyDown()) {

			String uuid = Minecraft.getMinecraft().player.getGameProfile().getId().toString();
			String answer = WebRequest_PremiumPoints.executePost("http://localhost:12540/request_points.php/?uuid=" + uuid, "").replaceAll("\r", "");
			System.out.println(answer);
			
			System.out.println(Arrays.toString(Json_Premium.readData(answer).getGadgets()));
			
			
//			System.out.println( );
//			Minecraft.getMinecraft().player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPECREATION, Minecraft.getMinecraft().world, 0, 0, 0);
//			DRPCorePackets.sendToServer(new Packet_DebugKey());
//			ScriptingController.executeScript();
//			globalToast = new TutorialToast(Icons.WOODEN_PLANKS, new TextComponentString("test"), new TextComponentString("test2"), true);
//			Minecraft.getMinecraft().getToastGui().add(globalToast);
			
//			TextureCombiner.overlayTextures(new ResourceLocation("drpcore:textures/guis/skill_tree.png"), new ResourceLocation("drpcore:textures/guis/recipe_selection.png"), new ResourceLocation("drpcore", "textures/blocks/test.png"));
		}
	}
	
}
