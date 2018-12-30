package net.dark_roleplay.core;


import net.dark_roleplay.core.api.old.modules.Module;
import net.dark_roleplay.core.common.IProxy;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.core.common.handler.DRPCoreCrafting;
import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core.modules.Modules;
import net.dark_roleplay.core.testing.crafting.CraftingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = References.MODID, version = References.VERSION, name = References.NAME, acceptedMinecraftVersions = References.ACCEPTEDVERSIONS, updateJSON = References.UPDATE_JSON, certificateFingerprint = "893c317856cf6819b3a8381c5664e4b06df7d1cc")
public class DarkRoleplayCore {

	public static boolean isServerSide = false;

	@SidedProxy(modId = References.MODID, clientSide = "net.dark_roleplay.core.client.ClientProxy")
	public static IProxy proxy;

	@Mod.Instance(References.MODID)
	public static DarkRoleplayCore instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		References.init(event);

		proxy.preInit(event);

		Modules.CRAFTING2.enable();
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
		DRPCorePackets.init();
		proxy.init(event);

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
//		event.registerServerCommand(new CommandTest());
//		event.registerServerCommand(new Command_Recipe("drprecipes"));
//		event.registerServerCommand(new Command_Skill("drpskills"));
//		event.registerServerCommand(new CommandOpenGui());
//		event.registerServerCommand(new CommandSpectatorBuilding());
	}

	public static class ServerProxy implements IProxy{
		@Override
		public void preInit(FMLPreInitializationEvent event) {}

		@Override
		public void init(FMLInitializationEvent event) {}

		@Override
		public void postInit(FMLPostInitializationEvent event) {}
	}
}
