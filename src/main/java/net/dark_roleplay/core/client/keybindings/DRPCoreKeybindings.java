package net.dark_roleplay.core.client.keybindings;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.crafting.Crafting_Util;
import net.dark_roleplay.core.common.config.Client;
import net.dark_roleplay.core.common.config.Debug;
import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core.common.objects.packets.debug.Packet_DebugKey;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class DRPCoreKeybindings {

	public static KeyBinding GUI_CRAFTING = new KeyBinding("keyBinding.openCrafting", Keyboard.KEY_C,"Dark Roleplay Core");
//	public static KeyBinding GUI_SKILLS = new KeyBinding("keyBinding.openSkills", Keyboard.KEY_K,"Dark Roleplay Core");
//	public static KeyBinding GUI_VARIATIONS = new KeyBinding("keyBinding.veriationSelection", Keyboard.KEY_V,"Dark Roleplay Core");
	public static KeyBinding TOGGLE_PLACEMENT_PREVIEW = new KeyBinding("keyBinding.placement_preview", Keyboard.KEY_NONE,"Dark Roleplay Core");

	public static KeyBinding debugging = new KeyBinding("keyBinding.debuging", Keyboard.KEY_NONE, "Dark Roleplay Core");

	public static void preInit(FMLPreInitializationEvent event) {
	}

	public static void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(GUI_CRAFTING);
		ClientRegistry.registerKeyBinding(TOGGLE_PLACEMENT_PREVIEW);
//		ClientRegistry.registerKeyBinding(GUI_VARIATIONS);
//		ClientRegistry.registerKeyBinding(GUI_SKILLS);

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
		if(GUI_CRAFTING.isKeyDown()) {
			Crafting_Util.openRecipeSelection(Blocks.AIR, Minecraft.getMinecraft().player.getPosition().add(0, -1, 0), 0f, -90f);
//		}else if(this.GUI_SKILLS.isKeyDown()){
//			ToastController.displayInfoToast("dpcore.featureNotImplemented", null);
//		}else if(this.GUI_VARIATIONS.isKeyDown()){
//			ToastController.displayInfoToast("dpcore.featureNotImplemented", null);
		}else if(TOGGLE_PLACEMENT_PREVIEW.isKeyDown()) {
			Client.BUILDING.PLACEMENT_PREVIEW = !Client.BUILDING.PLACEMENT_PREVIEW;
			ConfigManager.sync(References.MODID, Config.Type.INSTANCE);
		}
		else if(Debug.DEBUG_KEY && debugging.isKeyDown()) {

//			Minecraft.getMinecraft().displayGuiScreen(new DrawingGui(new int[] {
//					0x00000000,
//					0xFF136207, 0xFF004A7F, 0xFFFFC400, 0xFF7F0000,
//					0xFFAA7A46, 0xFF57007F, 0xFFD70270, 0xFF734F96
//					}, 24, 16, 16));

//			Minecraft.getMinecraft().displayGuiScreen(new ItemExporterGui());

			DRPCorePackets.sendToServer(new Packet_DebugKey());

//			Minecraft.getMinecraft().displayGuiScreen(new DrawingGui(new int[] {
//					0xFFFFFFFF,
//					0xFF136207, 0xFF004A7F, 0xFFFFC400, 0xFF7F0000,
//					0xFFAA7A46, 0xFF57007F, 0xFFD70270, 0xFF734F96
//					}, 16, 32, 16, (image) -> {return;}));

//			Minecraft.getMinecraft().displayGuiScreen(new ModelTestingGui());
//			File structure = new File("./test/just_a_test.blueprint");
//			try {
//				InputStream is = new BufferedInputStream(new FileInputStream(structure));
//				Blueprint bp = BlueprintUtil.readFromFile(is);
//				BlueprintPreview.buildModel(Minecraft.getMinecraft().player.getEntityWorld(), new BlockPos(0, 0, 0), bp);
//				BlueprintPreview.setPos(new BlockPos(0, 15, 0));
//				BlueprintPreview.activate();
//
////				BlueprintPreview.deactivate();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}

//			//Building Viewer
//			BuildingViewerHelper.initialize(Minecraft.getMinecraft().player.getPosition().add(0, 5, 0), Minecraft.getMinecraft().player.world.getTileEntity(Minecraft.getMinecraft().player.getPosition().down()));

//			System.out.println(	PermissionAPI.hasPermission(Minecraft.getMinecraft().player, "drpcore.test.number2"));

//			if(Minecraft.getMinecraft().player.isSneaking()) {
//				CraftingRegistry.clear();
//				CraftingRegistry.loadModRecipes();
//			}else {
//				Minecraft.getMinecraft().displayGuiScreen(new Crafting5(Minecraft.getMinecraft().player.getPosition(), Blocks.AIR));
//			}


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
		obj.addProperty("name", output.getItem().getRegistryName().getPath());
		obj.add("outputs", outputArr);
		obj.add("ingredients", inputArr);


//		File file = new File(References.FOLDER_RECIPES.getPath() + "/" + output.getItem().getRegistryName().getPath() + ".json");
//		int i = 1;
//		while(file.exists()){
//			file = new File(References.FOLDER_RECIPES.getPath() + "/" + output.getItem().getRegistryName().getPath() + i + ".json");
//			i++;
//		}

//		try {
//			file.createNewFile();
//
//
//
//			System.out.println(obj.toString());
//
//			FileWriter writer = new FileWriter(file);
//		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		    gson.toJson(obj, writer);
//		    writer.flush();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

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
