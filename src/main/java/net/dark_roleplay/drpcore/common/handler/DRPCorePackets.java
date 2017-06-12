package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.common.network.packets.config.Packet_SyncBoolean;
import net.dark_roleplay.drpcore.common.network.packets.crafting.Initialize_SimpleRecipe;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPlayerRecipeState;
import net.dark_roleplay.drpcore.common.network.packets.skills.Packet_UnlockSkill;
import net.dark_roleplay.drpcore.common.network.packets.skills.SyncPacket_Skill;
import net.dark_roleplay.drpcore.common.network.packets.skills.SyncPacket_SkillPoint;
import net.dark_roleplay.drpcore.common.network.packets.weapons.Packet_ExtendedRangeAttack;
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
		INSTANCE.registerMessage(SyncPlayerRecipeState.class, SyncPlayerRecipeState.class , i++, Side.CLIENT);
		INSTANCE.registerMessage(Initialize_SimpleRecipe.class, Initialize_SimpleRecipe.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_ExtendedRangeAttack.class, Packet_ExtendedRangeAttack.class, i++, Side.SERVER);
		INSTANCE.registerMessage(Packet_SyncBoolean.class, Packet_SyncBoolean.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(SyncPacket_SkillPoint.class, SyncPacket_SkillPoint.class, i++, Side.CLIENT);
		INSTANCE.registerMessage(Packet_UnlockSkill.class, Packet_UnlockSkill.class, i++, Side.SERVER);
		INSTANCE.registerMessage(SyncPacket_Skill.class, SyncPacket_Skill.class, i++, Side.CLIENT);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {

		INSTANCE.sendTo(message, player);
	}

	public static void sendToServer(IMessage message) {

		INSTANCE.sendToServer(message);
	}
}