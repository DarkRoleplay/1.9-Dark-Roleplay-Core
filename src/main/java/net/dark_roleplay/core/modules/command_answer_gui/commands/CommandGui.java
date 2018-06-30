package net.dark_roleplay.core.modules.command_answer_gui.commands;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core.modules.command_answer_gui.network.AnswerType;
import net.dark_roleplay.core.modules.command_answer_gui.network.Packet_OpenGui;
import net.dark_roleplay.library_old.commands.DRPCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import scala.actors.threadpool.Arrays;

public class CommandGui extends DRPCommand{

	public CommandGui(String name, String permissionsNode, String permissionDescription, String... aliases) {
		super(name, permissionsNode, permissionDescription, aliases);
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		AnswerType type;
		try{
			type = AnswerType.valueOf(args[1]);
		}catch(Exception e) {
			return;
		}
		
		DRPCorePackets.sendTo(new Packet_OpenGui(StringUtils.join(Arrays.copyOfRange(args, Math.min(args.length, 4), args.length)," "), args[0], type, (String[]) Arrays.copyOfRange(args, 2, Math.min(args.length, 4))), (EntityPlayerMP) sender);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		// TODO Auto-generated method stub
		return null;
	}

}
