package net.dark_roleplay.drpcore.server.commands.skills;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.commands.DRPCommand;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.modules.work_in_progress.skill.Skill;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import scala.actors.threadpool.Arrays;

public class Command_Skill extends DRPCommand{

	private static List<String> skills;
	
	public Command_Skill(String name){
		super(name);
		skills = new ArrayList<String>();
//		for(Skill skill : Modules.SKILL.getSkillRegistry().getValues()){
//			skills.add(skill.toString());
//		}
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "drpskills <list> [all|player]\n"
			 + "drpskills <lock/unlock> <skill> [player]";
	}

	@Override
	public List<String> getAliases() {
		 return new ArrayList<String>();
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "drpcore.commands.skills");
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		System.out.println(args.length);
		if(args.length == 1){
			return Arrays.asList(new String[]{"list", "lock", "unlock"});
		}else if(args.length == 2){
			if(args[0].equals("list"))
				return Arrays.asList(new String[]{"all", "unlocked"});
			else if(args[0].equals("unlock"))
				return skills;
			else if(args[0].equals("lock"))
					return skills;
		}else if(args.length == 3){
			if(args[0].equals("list") && args[1].equals("unlocked"))
				return Arrays.asList(server.getOnlinePlayerNames());
			if(args[0].equals("unlock") || args[0].equals("lock"))
				return Arrays.asList(server.getOnlinePlayerNames());
		}
		
		return null;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		if(args.length == 0){
			sendMessage(sender, "You need to chose an action: list/unlock/lock");
		}else if(sender instanceof EntityPlayer){
			if(args[0].equals("list")){
				if(args.length >= 1){
					if(args[1].equals("all")){
						sendMessage(sender, "Available Skills:");
						sendMessage(sender, skills.toString());
					}else if(args[1].equals("unlocked")){
						if(args.length == 3){
							
						}else{
							sendMessage(sender, "You've unlocked following skills:");
//							sendMessage(sender, Modules.SKILL.getSkillController((EntityPlayer) sender).getSkills().toString());
						}
					}
				}
			}else if(args[0].equals("unlock")){
//				ISkillController controller = Modules.SKILL.getSkillController((EntityPlayer) sender);
//				controller.unlockSkill(getSkill(args[1]), (EntityPlayer) sender);
			}
		}else{
			if(args[0].equals("list")){
				if(args.length >= 1){
					if(args[1].equals("all")){
						sendMessage(sender, "Available Skills:");
						sendMessage(sender, skills.toString());
					}else if(args[1].equals("unlocked")){
						if(args.length >= 2){
							
						}
					}
				}
			}
		}
    }
	
	private void sendMessage(ICommandSender sender, String message){
		sender.sendMessage(new TextComponentString(message));
	}
	
	private Skill getSkill(String arg){
		return null;
//		return Modules.SKILL.getSkillRegistry().containsKey(new ResourceLocation(arg)) ? Modules.SKILL.getSkillRegistry().getValue(new ResourceLocation(arg)) : null;
	}
	
	private ISkillController getSkills(MinecraftServer server, ICommandSender sender, String arg){
		EntityPlayerMP player;
		try {
			player = getPlayer(server, sender, arg);
			return player.hasCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null) ? player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null) : null;
		} catch (CommandException e) {
			e.printStackTrace();
		}
		return null;
	}
}
