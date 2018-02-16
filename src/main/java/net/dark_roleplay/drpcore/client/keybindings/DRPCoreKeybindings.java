package net.dark_roleplay.drpcore.client.keybindings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.dark_roleplay.drpcore.api.crafting.Crafting_Util;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.modules.crafting.GuiCrafting;
import net.dark_roleplay.drpcore.modules.crops.ICropHandler;
import net.dark_roleplay.drpcore.modules.time.Date;
import net.dark_roleplay.drpcore.modules.work_in_progress.model_editor.Gui_EntityEdit;
import net.dark_roleplay.drpcore.modules.work_in_progress.music.Song;
import net.dark_roleplay.drpcore.modules.work_in_progress.premium.GuiPremium;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
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
			
//			createRecipe();
//			Minecraft.getMinecraft().displayGuiScreen(new GuiCrafting(null, 0, 0, 0, 0));
//			Minecraft.getMinecraft().displayGuiScreen(new GuiPremium());

//			World world = Minecraft.getMinecraft().world;
//			ChunkPos pos = new ChunkPos(Minecraft.getMinecraft().player.getPosition());
//			Chunk chunk = world.getChunkFromChunkCoords(pos.x, pos.z);
//			if(chunk.hasCapability(DRPCoreCapabilities.CROP_HANDLER, null)){
//				ICropHandler instance = chunk.getCapability(DRPCoreCapabilities.CROP_HANDLER, null);
////				instance.growCrops(world, new Date(0, SEASONS.EARLY_SUMMER, 0));
//			}
		}
	}
	
	private void createRecipe(){
		Block station = Block.REGISTRY.getObject(new ResourceLocation("drpmedieval", "cauldron"));
//		Block station = Blocks.AIR;
		String category = "stews";
		
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
		
		
		File file = new File(DRPCoreReferences.DARK_ROLEPLAY_RECIPES_FOLDER.getPath() + "/" + output.getItem().getRegistryName().getResourcePath() + ".json");
		int i = 1;
		while(file.exists()){
			file = new File(DRPCoreReferences.DARK_ROLEPLAY_RECIPES_FOLDER.getPath() + "/" + output.getItem().getRegistryName().getResourcePath() + i + ".json");
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
