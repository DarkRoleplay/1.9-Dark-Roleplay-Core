package net.dark_roleplay.drpcore.common;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.common.commands.crafting.Command_Recipe;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.dark_roleplay.drpcore.common.events.capabilities.Event_CapabilityEntity;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerClone;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerLoggedIn;
import net.dark_roleplay.drpcore.common.handler.DRPCoreBlocks;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEvents;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATECHECK, guiFactory = DRPCoreInfo.CONFIG_GUI_FACTORY)
public class DarkRoleplayCore {

	public static File configDir;
	
	public static final Logger LOGGER = LogManager.getLogger(DRPCoreInfo.MODID);

	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		configDir = event.getSuggestedConfigurationFile();
		DRPCoreConfigs.loadConfig(event.getSuggestedConfigurationFile());

		DRPCoreItems.preInit(event);
		DRPCoreBlocks.preInit(event);
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//TODO Create EVENT HANDLER
		FMLCommonHandler.instance().bus().register(new Event_ConfigChange());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerClone());
		MinecraftForge.EVENT_BUS.register(new Event_CapabilityEntity());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerLoggedIn());
		
		DRPCoreItems.init(event);
		DRPCoreBlocks.init(event);
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		DRPCoreEvents.init(event);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreItems.postInit(event);
		DRPCoreBlocks.postInit(event);
		DRPCoreCapabilities.postInit(event);
		DRPCoreGuis.postInit(event);
		DRPCoreEvents.postInit(event);
		proxy.postInit(event);

//		CraftingRegistry.registerRecipe(Blocks.AIR, "DEBUG", new SimpleRecipe(new ResourceLocation(DRPCoreInfo.MODID, "RECIPE1"),
//				new ItemStack[]{new ItemStack(Items.APPLE)},
//				new ItemStack[]{new ItemStack(Items.ARROW,4),
//						new ItemStack(Item.getItemFromBlock(Blocks.STONE),4)}), false);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		//ServerCommandManager commandManager = (ServerCommandManager) event.getServer().getCommandManager(); 
		//commandManager.registerCommand(new Command_Recipe());
		event.registerServerCommand(new Command_Recipe());
	}
}
