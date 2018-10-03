package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.common.objects.packets.crafting.Packet_InitSimpleRecipe;
import net.dark_roleplay.core.common.objects.packets.crafting.SyncPacket_PlayerRecipeState;
import net.dark_roleplay.core.common.objects.packets.debug.Packet_DebugKey;
import net.dark_roleplay.core.common.objects.packets.skills.Packet_UnlockSkill;
import net.dark_roleplay.core.common.objects.packets.skills.SyncPacket_SkillPoints;
import net.dark_roleplay.core.common.objects.packets.weapons.Packet_ExtendedRangeAttack;
import net.dark_roleplay.core.testing.game_rules.ListenerPackets;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class DRPCorePackets {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("dark_roleplay_core".toLowerCase());

	public static void init() {
		// INSTANCE.registerMessage(/*PacketClass.class,PacketClass.class, ID,

		// Side.Client bzw Side.server*/)
		int i = 0;
		INSTANCE.registerMessage(SyncPacket_PlayerRecipeState.class, SyncPacket_PlayerRecipeState.class , i++, Side.CLIENT);
		INSTANCE.registerMessage(Packet_InitSimpleRecipe.class, Packet_InitSimpleRecipe.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_ExtendedRangeAttack.class, Packet_ExtendedRangeAttack.class, i++, Side.SERVER);
		INSTANCE.registerMessage(SyncPacket_SkillPoints.class, SyncPacket_SkillPoints.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(ListenerPackets.SyncBooleanRuleToListener.class, ListenerPackets.SyncBooleanRuleToListener.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(ListenerPackets.SyncBooleanRuleToListener.class, ListenerPackets.SyncBooleanRuleToListener.class, i++, Side.SERVER);
		INSTANCE.registerMessage(ListenerPackets.OpenGameRuleScreen.class, ListenerPackets.OpenGameRuleScreen.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(ListenerPackets.RemoveListener.class, ListenerPackets.RemoveListener.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_UnlockSkill.class, Packet_UnlockSkill.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_DebugKey.class, Packet_DebugKey.class, i++, Side.SERVER);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {

		INSTANCE.sendTo(message, player);
	}

	public static void sendToServer(IMessage message) {

		INSTANCE.sendToServer(message);
	}
}