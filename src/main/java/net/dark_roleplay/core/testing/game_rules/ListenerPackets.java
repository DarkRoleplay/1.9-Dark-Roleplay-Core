package net.dark_roleplay.core.testing.game_rules;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.library.experimental.variables.wrappers.BooleanWrapper;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.server.permission.PermissionAPI;

public class ListenerPackets {

	public static class OpenGameRuleScreen extends PacketBase.Client<OpenGameRuleScreen>{

		public OpenGameRuleScreen() {}

		public OpenGameRuleScreen(EntityPlayerMP player) {
			GameRuleListener.addListener(player);
		}

		@Override
		public void fromBytes(ByteBuf buf) {}

		@Override
		public void toBytes(ByteBuf buf) {}

		@Override
		public void handleClientSide(OpenGameRuleScreen message, EntityPlayer player) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				Minecraft.getMinecraft().displayGuiScreen(new GameRuleSettings(Minecraft.getMinecraft().player.world));
			});
		}
	}

	public static class RemoveListener extends PacketBase.Server<RemoveListener>{

		@Override
		public void fromBytes(ByteBuf buf) {}

		@Override
		public void toBytes(ByteBuf buf) {}

		@Override
		public void handleServerSide(RemoveListener message, EntityPlayer player) {
			player.getServer().addScheduledTask(() -> {
				GameRuleListener.removeListener((EntityPlayerMP) player);
			});
		}
	}

	public static class SyncBooleanRuleToListener extends PacketBase<SyncBooleanRuleToListener>{

		private String rule = "none";
		private boolean value = false;

		public SyncBooleanRuleToListener() {
			this.rule = "none";
			this.value = false;
		}

		public SyncBooleanRuleToListener(String rule, boolean value) {
			this.rule = rule;
			this.value = value;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			this.value = buf.readBoolean();
			this.rule = ByteBufUtils.readUTF8String(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBoolean(this.value);
			ByteBufUtils.writeUTF8String(buf, this.rule);
		}

		@Override
		public void handleClientSide(SyncBooleanRuleToListener message, EntityPlayer player) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				if(Minecraft.getMinecraft().currentScreen instanceof GameRuleSettings) {
					GameRuleSettings settingGui = (GameRuleSettings) Minecraft.getMinecraft().currentScreen;
					settingGui.booleanRules.put(message.rule, new BooleanWrapper(message.value));
				}
			});
		}

		@Override
		public void handleServerSide(SyncBooleanRuleToListener message, EntityPlayer player) {
			player.getServer().addScheduledTask(() ->{
				if(PermissionAPI.hasPermission(player, "drpcore.access_gui.gamerules")) {
					player.getEntityWorld().getGameRules().setOrCreateGameRule(message.rule, String.valueOf(message.value));
					GameRuleListener.sendGameRuleChangeToListeners(message.rule, message.value);
				}
			});
		}
	}
}
