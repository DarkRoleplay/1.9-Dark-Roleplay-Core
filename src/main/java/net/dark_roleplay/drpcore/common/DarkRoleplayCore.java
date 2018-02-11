package net.dark_roleplay.drpcore.common;


import net.dark_roleplay.drpcore.api.Modules;
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
import net.dark_roleplay.drpcore.modules.Module;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = DRPCoreReferences.MODID, version = DRPCoreReferences.VERSION, name = DRPCoreReferences.NAME, acceptedMinecraftVersions = DRPCoreReferences.ACCEPTEDVERSIONS, updateJSON = DRPCoreReferences.UPDATE_JSON)
public class DarkRoleplayCore {
	
	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance(DRPCoreReferences.MODID)
	public static DarkRoleplayCore instance;
	
	public DarkRoleplayCore(){
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		DRPCoreReferences.init(event);
		
		SyncedConfigRegistry.setSide(event.getSide());
		
		DRPCoreCapabilities.preInit(event);
		DRPCoreGuis.preInit(event);
		DRPCoreEvents.preInit(event);
		DRPCoreEntities.init(event);
		
		
		GameRegistry.registerTileEntity(TE_BlueprintController.class, DRPCoreReferences.MODID + ":" + "tileentity_structure_controller");

		proxy.preInit(event);
		
		Modules.MATERIALS.enable();
		ProgressBar progressBar = ProgressManager.push("Pre Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.preInit(event);
		}
		
		Modules.CRAFTING2.addMod("drpcore");
		
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
		
		DRPCorePerms.init(event);

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
		
		ProgressBar progressBar = ProgressManager.push("Post Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
	        module.postInit(event);
		}
		ProgressManager.pop(progressBar);
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new Command_Recipe("drprecipes"));
		event.registerServerCommand(new Command_Skill("drpskills"));
	}
}
