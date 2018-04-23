package net.dark_roleplay.drpcore.client.keybindings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.old.crafting.Crafting_Util;
import net.dark_roleplay.drpcore.api.old.modules.hud.HudLoader;
import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.common.config.Debug;
import net.dark_roleplay.drpcore.common.util.toasts.ToastController;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class DRPCoreKeybindings {
	
	public static KeyBinding GUI_CRAFTING = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C,"Dark Roleplay Core");		
	public static KeyBinding GUI_SKILLS = new KeyBinding("keyBinding.openSkills", Keyboard.KEY_K,"Dark Roleplay Core");		
	public static KeyBinding GUI_VARIATIONS = new KeyBinding("keyBinding.veriationSelection", Keyboard.KEY_V,"Dark Roleplay Core");	
	public static KeyBinding RELOAD_HUD = new KeyBinding("keyBinding.reloadHud", Keyboard.CHAR_NONE, "Dark Roleplay Core");
	public static KeyBinding debugging = new KeyBinding("keyBinding.debuging", Keyboard.KEY_B, "Dark Roleplay Core");

	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(GUI_CRAFTING);
		ClientRegistry.registerKeyBinding(GUI_VARIATIONS);
		ClientRegistry.registerKeyBinding(GUI_SKILLS);
		ClientRegistry.registerKeyBinding(RELOAD_HUD);
//		ClientRegistry.registerKeyBinding(openSkill);
		
		if(Debug.DEBUG_KEY){
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

		if(this.GUI_CRAFTING.isKeyDown()) {
			Crafting_Util.openRecipeSelection(Blocks.AIR);
			References.CRAFTING_TUT.hide();
		}else if(this.GUI_SKILLS.isKeyDown()){
			ToastController.displayInfoToast("dpcore.featureNotImplemented", null);
		}else if(this.GUI_VARIATIONS.isKeyDown()){
			ToastController.displayInfoToast("dpcore.featureNotImplemented", null);
		}else if(this.RELOAD_HUD.isKeyDown()) {
			HudLoader.initializeHuds();
		}
		else if(Debug.DEBUG_KEY && this.debugging.isKeyDown()) {
			Minecraft.getMinecraft().setRenderViewEntity(Minecraft.getMinecraft().player.getEntityWorld().getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().player, new AxisAlignedBB(0,0,0,10,10,10)).get(0));
			
//			Minecraft.getMinecraft().displayGuiScreen(new Gui_UpdateInformation());
//			System.out.println(	PermissionAPI.hasPermission(Minecraft.getMinecraft().player, "drpcore.test.number2"));
//			Minecraft.getMinecraft().displayGuiScreen(new Gui_Test());

//			Minecraft.getMinecraft().displayGuiScreen(new Crafting5());
			
//			createRecipe();
//			Minecraft.getMinecraft().displayGuiScreen(new GuiCrafting(null, 0, 0, 0, 0));
//			Minecraft.getMinecraft().displayGuiScreen(new GuiPremium());
		}
	}
	
	private void createRecipe(){
		Block station = Block.REGISTRY.getObject(new ResourceLocation("drpmedieval", "anvil"));
//		Block station = Blocks.AIR;
		String category = "decorations";
		
		ItemStack output = null;
		
		JsonArray outputArr = new JsonArray();
		JsonArray inputArr = new JsonArray();
		
		NonNullList<ItemStack> inv = Minecraft.getMinecraft().player.inventory.mainInventory;
		
		for(int i = 9; i < 18; i++){
			if(!inv.get(i).isEmpty()){
				if(output == null)
					output = inv.get(i);
				outputArr.add(serializeItem(inv.get(i)));;
			}
		}
		
		for(int i = 18; i < 27; i++){
			if(!inv.get(i).isEmpty()){
				inputArr.add(serializeItem(inv.get(i)));;
			}
		}
		
		JsonObject obj = new JsonObject();
		if(station != Blocks.AIR)
			obj.addProperty("station", station.getRegistryName().toString());
		
		obj.addProperty("category", category);
		obj.addProperty("name", output.getItem().getRegistryName().getResourcePath());
		obj.add("outputs", outputArr);
		obj.add("ingredients", inputArr);
		
		
		File file = new File(References.FOLDER_RECIPES.getPath() + "/" + output.getItem().getRegistryName().getResourcePath() + ".json");
		int i = 1;
		while(file.exists()){
			file = new File(References.FOLDER_RECIPES.getPath() + "/" + output.getItem().getRegistryName().getResourcePath() + i + ".json");
			i++;
		}
		
		try {
			file.createNewFile();
			


			System.out.println(obj.toString());
			
			FileWriter writer = new FileWriter(file);
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    gson.toJson(obj, writer);
		    writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static JsonObject serializeItem(ItemStack stack) {
		JsonObject obj = new JsonObject();
		obj.addProperty("item", stack.getItem().getRegistryName().toString());
		if(stack.getCount() != 1)
			obj.addProperty("count", stack.getCount());
		if(stack.getMetadata() != 0)
			obj.addProperty("data", stack.getMetadata());
		
		return obj;
    }
}
