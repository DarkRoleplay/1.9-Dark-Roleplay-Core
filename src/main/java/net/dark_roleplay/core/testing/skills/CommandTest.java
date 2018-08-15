package net.dark_roleplay.core.testing.skills;

import java.util.Collection;
import java.util.List;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.library_old.commands.DRPCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandTest extends DRPCommand{

	public CommandTest() {
		super("drpskills", "drpcore.skills", "skills", "drpskill", "skill");
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayer) || args.length == 0) {
			return;
		}
		
		EntityPlayer player = (EntityPlayer) sender;
		SkillHandler handler = player.getCapability(DRPCoreCapabilities.SKILL_HANDLER, null);
		if(args.length == 1 && args[0].equals("list")) {
			Collection<SkillHolder> skills = handler.getSkillHolders();
			skills.forEach(skill ->{
				sender.sendMessage(new TextComponentString("Skill: " + skill.getSkill().getRegistryName().toString() + " Level: " + skill.getLevel() + " Experience: " + skill.getExperience()));
			});
		}else if(args.length == 2) {
			switch(args[0]) {
				case "unlock":
					if(SkillRegistries.SKILLS.containsKey(new ResourceLocation(args[1])))
						handler.unlockSkill(SkillRegistries.SKILLS.getValue(new ResourceLocation(args[1])));
					break;
				case "lock":
					if(SkillRegistries.SKILLS.containsKey(new ResourceLocation(args[1])))
						handler.lockSkill(SkillRegistries.SKILLS.getValue(new ResourceLocation(args[1])));
					break;
				case "info":
					if(SkillRegistries.SKILLS.containsKey(new ResourceLocation(args[1])) && References.SIDE.isClient())
						Minecraft.getMinecraft().displayGuiScreen(new SkillInfo(SkillRegistries.SKILLS.getValue(new ResourceLocation(args[1]))));
					break;
				default:
					break;
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return null;
	}

}
