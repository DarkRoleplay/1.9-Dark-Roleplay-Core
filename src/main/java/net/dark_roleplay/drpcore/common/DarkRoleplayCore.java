package net.dark_roleplay.drpcore.common;


import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.modules.Module;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCrafting;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.handler.DRPCorePerms;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.modules.command_answer_gui.commands.CommandGui;
import net.dark_roleplay.drpcore.testing.crafting.CraftingRegistry;
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
	
	@SidedProxy(clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static ClientProxy proxy;
	
	@Mod.Instance(References.MODID)
	public static DarkRoleplayCore instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){				
		References.init(event);
		
		SyncedConfigRegistry.setSide(event.getSide());
		
		GameRegistry.registerTileEntity(TE_BlueprintController.class, References.MODID + ":" + "tileentity_structure_controller");

		proxy.preInit(event);
		
		Modules.HUD.enable();
		Modules.CRAFTING2.enable();
		Modules.MATERIALS.enable();
		Modules.UPDATE_CHECKER.enable();
		ProgressBar progressBar = ProgressManager.push("Pre Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
	        progressBar.step(module.getName());
			if(module.isEnabled())
		        module.preInit(event);
		}
		
		ProgressManager.pop(progressBar);
		
//		PermissionAPI.setPermissionHandler(new DRPPermissionHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		DRPCoreCrafting.init(event);
		
		//TODO REMOVE?
//		CraftingRegistry.loadRecipes();
		
		DRPCoreCapabilities.init(event);
		DRPCoreGuis.init(event);
		DRPCorePackets.init();
		proxy.init(event);
		
		DRPCorePerms.init(event);

		ProgressBar progressBar = ProgressManager.push("Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
			progressBar.step(module.getName());
			if(module.isEnabled())
				module.init(event);
		}
		ProgressManager.pop(progressBar);
		//TODO MOVE
		
//		for(int i = 0; i < 25; i ++)
//			PermissionAPI.registerNode("drpcore.test.number" + i, DefaultPermissionLevel.OP, "A Simple test Permission, not really doing anything (Number: " + i + ")");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		CraftingRegistry.loadModRecipes();
		proxy.postInit(event);
		
		ProgressBar progressBar = ProgressManager.push("Post Initializing Modules", Module.getModules().size());
		for(Module module : Module.getModules()){
			progressBar.step(module.getName());
			if(module.isEnabled())
				module.postInit(event);
		}
		ProgressManager.pop(progressBar);
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandGui("drpopengui", "drpcore.commands.opengui", "Allows to use /drpopengui to show the player a GUI and get's it's selection into a scoreboard", "opengui"));
//		event.registerServerCommand(new CommandTest());
//		event.registerServerCommand(new Command_Recipe("drprecipes"));
//		event.registerServerCommand(new Command_Skill("drpskills"));
	}
}
