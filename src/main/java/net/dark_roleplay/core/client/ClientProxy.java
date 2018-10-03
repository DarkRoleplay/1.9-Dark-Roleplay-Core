package net.dark_roleplay.core.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.dark_roleplay.core.client.events.player.Event_Mouse;
import net.dark_roleplay.core.client.events.rendering.Event_BlockHighlight;
import net.dark_roleplay.core.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.core.client.resources.DRPModelLoader;
import net.dark_roleplay.core.common.IProxy;
import net.dark_roleplay.core.common.handler.DRPCoreGuis;
import net.dark_roleplay.library_old.items.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy{

	public static final TutorialToast CRAFTING_TUT = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, new TextComponentTranslation("drpcore.tutorial.craft.title"), new TextComponentTranslation("drpcore.tutorial.craft.desc", "C"), false);

	public static boolean useRecipeData = false;
	public static int recipePage = 0;
	public static int categoryOffset = 0;
	public static short selectedCategory = 0;
	public static byte currentTick = 0;

//	public static List<ModularGui_Template> modularGuis = new ArrayList<ModularGui_Template>();


	@Override
	public void preInit(FMLPreInitializationEvent event) {
		DRPCoreKeybindings.preInit(event);
		ItemUtil.registerItemMeshs();


		ModelLoaderRegistry.registerLoader(new DRPModelLoader());

		//MinecraftForge.EVENT_BUS.register(Event_ModelBaked.instance);
		MinecraftForge.EVENT_BUS.register(new Event_Mouse());
		MinecraftForge.EVENT_BUS.register(new Event_BlockHighlight());

	    // model to be used for rendering this item
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("drpcore:medicine", "inventory");
	}

	@Override
	public void init(FMLInitializationEvent event) {
		DRPCoreKeybindings.init(event);

		DRPCoreGuis.init(event);

//		RenderPlayer steve = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default"));
//		RenderPlayer alex = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim"));
//		steve.addLayer(new RenderLayer_PremiumAddon(steve));
//		alex.addLayer(new RenderLayer_PremiumAddon(alex));
//		steve.addLayer(layer)
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreKeybindings.postInit(event);
//	    Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ColorHandler(), DRPCoreItems.TEST_PAINT);
	}

	public static IResource getResource(ResourceLocation location){
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        IResource res = null;
		try {
			res = manager.getResource(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return res;
    }

	public static List<IResource> getResources(ResourceLocation location){
		List<IResource> resources = new ArrayList<IResource>();

		File folder;
		try {
			folder = new File(ClientProxy.class.getClassLoader().getResource("/assets/" + location.getNamespace() + "/" + location.getPath()).toURI());

			for (final File file : folder.listFiles()) {
		        if (file.isDirectory()) {
		        } else {
		        	resources.add(getResource(new ResourceLocation(location.getNamespace(), location.getPath() + file.getName())));
		        }
		    }
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return resources;
	}

	public static JsonElement getResourceAsJson(ResourceLocation location){
		IResource res = getResource(location);
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		return je;
	}
}
