package net.dark_roleplay.drpcore.common;

import net.dark_roleplay.drpcore.api.util.sitting.Modules;
import net.dark_roleplay.drpcore.client.renderer.players.PremiumRegistry;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCrafting;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEntities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEvents;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.modules.Module;
import net.dark_roleplay.drpcore.modules.hud.HudLoader;
import net.dark_roleplay.drpcore.server.commands.crafting.Command_Recipe;
import net.dark_roleplay.drpcore.server.commands.skills.Command_Skill;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATE_JSON)
public class DarkRoleplayCore {
	
	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;

	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		DRPCoreInfo.init(event);
		
		Modules.HUD.enable();
		HudLoader.initializeHuds();
		
		SyncedConfigRegistry.setSide(event.getSide());
		
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		DRPCoreEntities.init(event);
		
		GameRegistry.registerTileEntity(TE_BlueprintController.class, DRPCoreInfo.MODID + ":" + "tileentity_structure_controller");

		proxy.preInit(event);
		
		ProgressBar progressBar = ProgressManager.push("Pre Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.preInit(event);
		}
		ProgressManager.pop(progressBar);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		DRPCoreCrafting.init(event);
		
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		DRPCoreEvents.init(event);
		proxy.init(event);
		
		ProgressBar progressBar = ProgressManager.push("Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.init(event);
		}
		ProgressManager.pop(progressBar);
		//TODO MOVE
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreCapabilities.postInit(event);
		DRPCoreGuis.postInit(event);
		DRPCoreEvents.postInit(event);
		proxy.postInit(event);

		if(event.getSide().isClient()){
			PremiumRegistry.initialize();
			PremiumRegistry.initializeAddon(0);
			PremiumRegistry.initializeAddon(1);
		}
		
		ProgressBar progressBar = ProgressManager.push("Post Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.postInit(event);
		}
		ProgressManager.pop(progressBar);
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new Command_Recipe());
		event.registerServerCommand(new Command_Skill());
	}
}
