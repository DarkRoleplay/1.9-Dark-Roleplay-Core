package net.dark_roleplay.drpcore.common.proxy;

import java.util.HashMap;
import java.util.UUID;

import net.dark_roleplay.drpcore.api.old.items.DRPItem;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {}
	
	public void init(FMLInitializationEvent event) {}
	
	public void postInit(FMLPostInitializationEvent event) {}
	
	public void registerItemMesh(String MODID, DRPItem item) {}
}
