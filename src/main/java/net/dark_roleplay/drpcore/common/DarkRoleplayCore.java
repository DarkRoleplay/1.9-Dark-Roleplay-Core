package net.dark_roleplay.drpcore.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DRPCoreInfo.MODID, version = DRPCoreInfo.VERSION, name = DRPCoreInfo.NAME, acceptedMinecraftVersions = DRPCoreInfo.ACCEPTEDVERSIONS, updateJSON = DRPCoreInfo.UPDATECHECK)
public class DarkRoleplayCore {

	public static final Logger LOGGER = LogManager.getLogger(DRPCoreInfo.MODID);

	public static boolean isServerSide = false;
	
	@SidedProxy(serverSide = "net.dark_roleplay.drpcore.common.proxy.CommonProxy", clientSide = "net.dark_roleplay.drpcore.client.ClientProxy")
	public static CommonProxy proxy;
	
	@net.minecraftforge.fml.common.Mod.Instance(DRPCoreInfo.MODID)
	public static DarkRoleplayCore instance;
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
