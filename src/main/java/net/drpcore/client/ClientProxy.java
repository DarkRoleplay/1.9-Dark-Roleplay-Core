package net.drpcore.client;

import net.drpcore.client.events.OnPlayerTick;
import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.proxy.CommonProxy;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy{
	
	public void checkForUpdates(){
		UpdateCheck.checkForUpdate();
		FMLCommonHandler.instance().bus().register(new OnPlayerTick());
	}
	
	public void registerEvents(){
		ClientRegistry.registerKeyBinding(KeyBindingManager.openInventory);
		ClientRegistry.registerKeyBinding(KeyBindingManager.openCrafting);
		ClientRegistry.registerKeyBinding(KeyBindingManager.openPurse);
		FMLCommonHandler.instance().bus().register(new KeyBindingManager());
	}
	
	public void registerRandom(){
		
	}
}
