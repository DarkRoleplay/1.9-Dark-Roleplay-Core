package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.common.network.packets.blocks.Packet_LoadBlueprint;
import net.dark_roleplay.core.common.network.packets.blocks.Packet_SaveBlueprint;
import net.dark_roleplay.core.common.network.packets.blocks.SyncPacket_BlueprintBlock;
import net.dark_roleplay.core.common.network.packets.chunks.SyncPacket_LockHandler;
import net.dark_roleplay.core.common.network.packets.config.SyncPacket_Boolean;
import net.dark_roleplay.core.common.network.packets.crafting.Packet_InitSimpleRecipe;
import net.dark_roleplay.core.common.network.packets.crafting.SyncPacket_PlayerRecipeState;
import net.dark_roleplay.core.common.network.packets.debug.Packet_DebugKey;
import net.dark_roleplay.core.common.network.packets.skills.Packet_UnlockSkill;
import net.dark_roleplay.core.common.network.packets.skills.SyncPacket_SkillPoints;
import net.dark_roleplay.core.common.network.packets.weapons.Packet_ExtendedRangeAttack;
import net.dark_roleplay.core.modules.command_answer_gui.network.Packet_Answer;
import net.dark_roleplay.core.modules.command_answer_gui.network.Packet_OpenGui;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.FMLEventChannel;
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
		INSTANCE.registerMessage(SyncPacket_Boolean.class, SyncPacket_Boolean.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(SyncPacket_SkillPoints.class, SyncPacket_SkillPoints.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(Packet_UnlockSkill.class, Packet_UnlockSkill.class, i++, Side.SERVER);
//		INSTANCE.registerMessage(SyncPacket_Skills.class, SyncPacket_Skills.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(Packet_DebugKey.class, Packet_DebugKey.class, i++, Side.SERVER);
		INSTANCE.registerMessage(SyncPacket_BlueprintBlock.class, SyncPacket_BlueprintBlock.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_SaveBlueprint.class, Packet_SaveBlueprint.class, i++, Side.SERVER);		
		INSTANCE.registerMessage(Packet_LoadBlueprint.class, Packet_LoadBlueprint.class, i++, Side.SERVER);
		INSTANCE.registerMessage(SyncPacket_LockHandler.class, SyncPacket_LockHandler.class , i++, Side.CLIENT);
		

		INSTANCE.registerMessage(Packet_Answer.class, Packet_Answer.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_OpenGui.class, Packet_OpenGui.class , i++, Side.CLIENT);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {

		INSTANCE.sendTo(message, player);
	}

	public static void sendToServer(IMessage message) {

		INSTANCE.sendToServer(message);
	}
}