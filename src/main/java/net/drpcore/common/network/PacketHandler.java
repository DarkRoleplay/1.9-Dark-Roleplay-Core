package net.drpcore.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("DarkRoleplay".toLowerCase());
		
	public static void init()
	{
		//INSTANCE.registerMessage(/*PacketClass.class,PacketClass.class, ID, Side.Client bzw Side.server*/);

		INSTANCE.registerMessage(PacketOpenInventory.class,PacketOpenInventory.class, 0,Side.SERVER);
		INSTANCE.registerMessage(PacketOpenCraftingGui.class,PacketOpenCraftingGui.class, 1,Side.SERVER);
		INSTANCE.registerMessage(PacketCraft.class, PacketCraft.class, 2, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenPurse.class, PacketOpenPurse.class, 3, Side.SERVER);
	}
	
    public static void sendToServer(IMessage message){
    	INSTANCE.sendToServer(message);
    }
	
}