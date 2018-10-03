package net.dark_roleplay.core.testing.game_rules;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.minecraft.entity.player.EntityPlayerMP;

public class GameRuleListener {

	private static List<EntityPlayerMP> players = new ArrayList<EntityPlayerMP>();

	public static void addListener(EntityPlayerMP player) {
		players.add(player);
	}

	public static void removeListener(EntityPlayerMP player) {
		players.add(player);
	}

	public static void sendGameRuleChangeToServer(String gamerule, boolean value) {
		DRPCorePackets.sendToServer(new ListenerPackets.SyncBooleanRuleToListener(gamerule, value));
	}

	public static void sendGameRuleChangeToListeners(String gamerule, boolean value) {
		for(EntityPlayerMP player : players)
			DRPCorePackets.sendTo(new ListenerPackets.SyncBooleanRuleToListener(gamerule, value), player);
	}
}
