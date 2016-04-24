package net.drpcore.common.network;

import net.drpcore.common.network.packets.PacketCraft;
import net.drpcore.common.network.packets.PacketOpenCraftingGui;
import net.drpcore.common.network.packets.PacketOpenInventory;
import net.drpcore.common.network.packets.PacketOpenPurse;
import net.drpcore.common.network.packets.PacketSwitchArmor;
import net.drpcore.common.network.packets.PacketSyncAdvancedInventory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("DarkRoleplay".toLowerCase());

	public static void init() {
		// INSTANCE.registerMessage(/*PacketClass.class,PacketClass.class, ID,
		// Side.Client bzw Side.server*/)

		int i = 0;

		INSTANCE.registerMessage(PacketSyncAdvancedInventory.class, PacketSyncAdvancedInventory.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(PacketOpenInventory.class, PacketOpenInventory.class, i++, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenCraftingGui.class, PacketOpenCraftingGui.class, i++, Side.SERVER);
		INSTANCE.registerMessage(PacketCraft.class, PacketCraft.class, i++, Side.SERVER);
		INSTANCE.registerMessage(PacketOpenPurse.class, PacketOpenPurse.class, i++, Side.SERVER);
		INSTANCE.registerMessage(PacketSwitchArmor.class, PacketSwitchArmor.class, i++, Side.SERVER);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {

		INSTANCE.sendTo(message, player);
	}

	public static void sendToServer(IMessage message) {

		INSTANCE.sendToServer(message);
	}

}