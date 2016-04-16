package net.drpcore.common.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("DarkRoleplay".toLowerCase());
		
	public static void init()
	{
		//INSTANCE.registerMessage(/*PacketClass.class,PacketClass.class, ID, Side.Client bzw Side.server*/);

		//INSTANCE.registerMessage(PacketSyncAdvancedInventory.class, PacketSyncAdvancedInventory.class, 0, Side.CLIENT);
		//INSTANCE.registerMessage(PacketSyncAdvancedInventory.class, PacketSyncAdvancedInventory.class, 1, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenInventory.class,PacketOpenInventory.class, 2,Side.SERVER);
		INSTANCE.registerMessage(PacketOpenCraftingGui.class,PacketOpenCraftingGui.class, 3,Side.SERVER);
		INSTANCE.registerMessage(PacketCraft.class, PacketCraft.class, 4, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenPurse.class, PacketOpenPurse.class, 5, Side.SERVER);
	}
	
	public static void sendTo(IMessage message, EntityPlayerMP player){
		INSTANCE.sendTo(message, player);
	}
	
    public static void sendToServer(IMessage message){
    	INSTANCE.sendToServer(message);
    }
	
}