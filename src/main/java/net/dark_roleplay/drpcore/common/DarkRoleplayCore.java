package net.dark_roleplay.drpcore.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.util.DRPUtil;
import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.client.renderer.players.PremiumRegistry;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.events.capabilities.Event_CapabilityEntity;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerClone;
import net.dark_roleplay.drpcore.common.events.entity.player.Event_PlayerLoggedIn;
import net.dark_roleplay.drpcore.common.handler.DRPCoreBlocks;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCrafting;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEvents;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.handler.DRPCoreSkills;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.server.commands.crafting.Command_Recipe;
import net.dark_roleplay.drpcore.server.commands.skills.Command_Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeVersion.Status;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATE_JSON)
public class DarkRoleplayCore {
	
	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		DRPUtil.createRegistries(event);
		DRPCoreInfo.init(event);
		
		SyncedConfigRegistry.setSide(event.getSide());
		
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		
		
		GameRegistry.registerTileEntity(TE_BlueprintController.class, DRPCoreInfo.MODID + ":" + "tileentity_structure_controller");

		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Event_ConfigChange());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerClone());
		MinecraftForge.EVENT_BUS.register(new Event_CapabilityEntity());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerLoggedIn());


		DRPCoreCrafting.init(event);
		
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		DRPCoreEvents.init(event);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreCapabilities.postInit(event);
		DRPCoreGuis.postInit(event);
		DRPCoreEvents.postInit(event);
		DRPCoreEvents.postInit(event);
		proxy.postInit(event);

		if(event.getSide().isClient()){
			PremiumRegistry.initialize();
			PremiumRegistry.initializeAddon(0);
			PremiumRegistry.initializeAddon(1);
		}
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new Command_Recipe());
		event.registerServerCommand(new Command_Skill());
	}
}
