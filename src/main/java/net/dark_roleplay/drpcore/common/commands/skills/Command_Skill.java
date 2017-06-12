package net.dark_roleplay.drpcore.common.commands.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.api.commands.DRPCommand;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPlayerRecipeState;
import net.dark_roleplay.drpcore.common.network.packets.skills.SyncPacket_SkillPoint;
import net.dark_roleplay.drpcore.common.skills.SkillPointData;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class Command_Skill extends DRPCommand{
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getName() {
		return "drpskillpoint";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "drpskillpoint <xp/point/list> <point_name> <add/remove> [amount(default 1)] [player]";
	}

	@Override
	public List<String> getAliases() {
		 return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		
        if (!sender.getEntityWorld().isRemote){
        	
        	if(args.length < 1){
        		return;
        	}else if(!args[0].equals("list") && args.length < 3){
        		return;
        	}
            String type = args.length >= 1 ? args[0] : "";
            String point = args.length >= 2 ? args[1] : "";
            String operation = args.length >= 3 ? args[2] : "";
            int amount;
            EntityPlayerMP player;
            
            if(args.length >= 5){
            	amount = Integer.parseInt(args[3]);
            	player =  getPlayer(server, sender, args[4]);
            }else if(args.length >= 4){
            	if(args[3].matches("^-?\\d+$")){
                	amount = Integer.parseInt(args[3]);
                	player = getCommandSenderAsPlayer(sender);
            	}else{
            		amount = 1;
                	player =  getPlayer(server, sender, args[3]);
            	}
            }else{
            	amount = 1;
            	player = getCommandSenderAsPlayer(sender);
            }

            ISkillController cap = player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
            
            SkillPoint skillPoint;
            
        	switch(type){
        	case "xp":
        		if((skillPoint = SkillRegistry.getSkillPointByName(point)) != null){
        			switch(operation){
        			case "add":
        				cap.increaseSkillXP(skillPoint, amount);
        				break;
        			case "remove":
        				cap.increaseSkillXP(skillPoint, -amount);
        				break;
        			default:
                		sender.sendMessage(new TextComponentString("This operation cannot be found! Available ones: \"add\" & \"remove\""));
        				break;
        			}
        			SkillPointData data = cap.getSkillPointData(skillPoint);
    				DRPCorePackets.sendTo(new SyncPacket_SkillPoint(point, data.getAmount(), data.getLevel(), data.getXP()), player);
        		}
        		break;
        	case "point":
        		if((skillPoint = SkillRegistry.getSkillPointByName(point)) != null){
        			switch(operation){
        			case "add":
        				cap.addSkillPoint(skillPoint, amount);
        				break;
        			case "remove":
        				cap.addSkillPoint(skillPoint, -amount);
        				break;
        			default:
                		sender.sendMessage(new TextComponentString("This operation cannot be found! Available ones: \"add\" & \"remove\""));
        				break;
        			}
        			SkillPointData data = cap.getSkillPointData(skillPoint);
    				DRPCorePackets.sendTo(new SyncPacket_SkillPoint(point, data.getAmount(), data.getLevel(), data.getXP()), player);
        		}
        		break;
        	case "list":
				sender.sendMessage(new TextComponentString(cap.getSkillPoints().size() + ""));
        		for(SkillPointData data : cap.getSkillPoints()){
        			sender.sendMessage(new TextComponentString(data.getPoint().getRegistryName() + ": " + data.getAmount() + ", " + data.getLevel() + "," + data.getXP()));
        		}
        		break;
        	default:
        		sender.sendMessage(new TextComponentString("This type cannot be found! Available ones: \"xp\" & \"point\""));
        		break;
        	}
        }
    } 

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}


	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
