package net.dark_roleplay.drpcore.client.keybindings;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import net.dark_roleplay.drpcore.api.crafting.Crafting_Util;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.modules.model_editor.Gui_EntityEdit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
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
			enableDebugKeys();
		}
		
		MinecraftForge.EVENT_BUS.register(new DRPCoreKeybindings());
	}
	
	private static boolean debugEnabled = false;
	
	public static void enableDebugKeys(){
		if(!debugEnabled){
			ClientRegistry.registerKeyBinding(debugging);
		}
		debugEnabled = !debugEnabled;
	}
	
	public static void disableDebugKeys(){
		if(debugEnabled){
			Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.removeElement(Minecraft.getMinecraft().gameSettings.keyBindings, debugging);
		}
		debugEnabled = !debugEnabled;
	}

	public static void postInit(FMLPostInitializationEvent event) {
	}

	@SubscribeEvent
	public void KeyInput(KeyInputEvent event) {

		if(this.openCrafting.isKeyDown()) {
			Crafting_Util.openRecipeSelection(Blocks.AIR);
		}
		
//		if(this.openSkill.isKeyDown()){
//			Skill_Util.openSkillOverview();
//		}
		
		if(DRPCoreConfigs.DEBUG.DEBUG_KEY && this.debugging.isKeyDown()) {
			
			Minecraft.getMinecraft().displayGuiScreen(new Gui_EntityEdit());

//			Minecraft.getMinecraft().displayGuiScreen(new Gui_Skills());
//			EntityPlayer player = Minecraft.getMinecraft().player;
//			System.out.println(
//					player.getEntityWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 10, 5, 10)));
		}
	}
}
