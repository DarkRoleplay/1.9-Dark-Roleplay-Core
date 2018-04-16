package net.dark_roleplay.drpcore.common;


import net.dark_roleplay.drpcore.api.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.modules.Module;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCrafting;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEntities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreEvents;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.handler.DRPCorePerms;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.testing.skills.CommandTest;
import net.dark_roleplay.drpcore.testing.skills.SkillRegistries;
import net.minecraftforge.common.MinecraftForge;
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

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME, acceptedMinecraftVersions = References.ACCEPTEDVERSIONS, updateJSON = References.UPDATE_JSON)
public class DarkRoleplayCore {
	
	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(References.MODID)
	public static DarkRoleplayCore instance;
	
	public DarkRoleplayCore(){
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
//		System.out.println("Prtinging Recipe Files");
				
		References.init(event);
		
		SyncedConfigRegistry.setSide(event.getSide());
		
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		DRPCoreEntities.init(event);
		
		GameRegistry.registerTileEntity(TE_BlueprintController.class, References.MODID + ":" + "tileentity_structure_controller");

		proxy.preInit(event);
		
		Modules.HUD.enable();
		
		Modules.MATERIALS.enable();
		ProgressBar progressBar = ProgressManager.push("Pre Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.preInit(event);
		}
		
		Modules.CRAFTING2.addMod("drpcore");
		
		ProgressManager.pop(progressBar);
		
//		PermissionAPI.setPermissionHandler(new DRPPermissionHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		DRPCoreCrafting.init(event);
		
		//TODO REMOVE?
		CraftingRegistry.loadRecipes();
		
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		DRPCoreEvents.init(event);
		proxy.init(event);
		
		DRPCorePerms.init(event);

		ProgressBar progressBar = ProgressManager.push("Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.init(event);
		}
		ProgressManager.pop(progressBar);
		//TODO MOVE
		
		
//		for(int i = 0; i < 25; i ++)
//			PermissionAPI.registerNode("drpcore.test.number" + i, DefaultPermissionLevel.OP, "A Simple test Permission, not really doing anything (Number: " + i + ")");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreCapabilities.postInit(event);
		DRPCoreGuis.postInit(event);
		DRPCoreEvents.postInit(event);
		proxy.postInit(event);
		
		ProgressBar progressBar = ProgressManager.push("Post Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.postInit(event);
		}
		ProgressManager.pop(progressBar);
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandTest());
//		event.registerServerCommand(new Command_Recipe("drprecipes"));
//		event.registerServerCommand(new Command_Skill("drpskills"));
	}
}
