package net.dark_roleplay.drpcore.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.common.handler.DRPCoreBlocks;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATECHECK, guiFactory = DRPCoreInfo.CONFIG_GUI_FACTORY)
public class DarkRoleplayCore {

	public static final Logger LOGGER = LogManager.getLogger(DRPCoreInfo.MODID);

	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		DRPCoreConfigs.loadConfig(event.getSuggestedConfigurationFile());
		
		DRPCoreItems.preInit(event);
		DRPCoreBlocks.preInit(event);
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new Event_ConfigChange());
		
		DRPCoreItems.init(event);
		DRPCoreBlocks.init(event);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreItems.postInit(event);
		DRPCoreBlocks.postInit(event);
		proxy.postInit(event);
	}
}
