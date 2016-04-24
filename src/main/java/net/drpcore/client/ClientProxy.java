package net.drpcore.client;

import net.drpcore.client.events.ClientConnectedToServer;
import net.drpcore.client.events.ClientDisconnectFromServer;
import net.drpcore.client.events.OnPlayerTick;
import net.drpcore.client.events.RenderPlayer;
import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

	public void checkForUpdates() {

		UpdateCheck.checkForUpdate();

	}

	public void registerEvents() {}

	public void registerRandom() {}
}
