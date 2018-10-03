package net.dark_roleplay.core.testing.game_rules;

import java.util.List;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.library_old.commands.DRPCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandOpenGui extends DRPCommand{

	public CommandOpenGui() {
		super("gamerulegui", "drpcore.access_gui.gamerules", "Allows the player to use the gamerule gui");
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayer)) {
			return;
		}

		EntityPlayerMP player = (EntityPlayerMP) sender;
		DRPCorePackets.sendTo(new ListenerPackets.OpenGameRuleScreen(player), player);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return null;
	}
}
