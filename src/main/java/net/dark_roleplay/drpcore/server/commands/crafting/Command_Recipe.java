package net.dark_roleplay.drpcore.server.commands.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPacket_PlayerRecipeState;
import net.dark_roleplay.library.commands.DRPCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class Command_Recipe extends DRPCommand{
	
	public Command_Recipe(String name) {
		super(name, "drpcore.command.recipe");
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "drprecipe <lock/unlock/progress/list> <recipe name> [progress value] [player]";
	}

	@Override
	public List<String> getAliases() {
		 return new ArrayList<String>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
		
        if (!sender.getEntityWorld().isRemote){
        	
            String mode = args.length >= 1 ? args[0] : "";
            String recipe = args.length >= 2 ? args[1] : "";
            int progress = 0;
            EntityPlayerMP player;
            
            if(mode.equals("progress")){
            	progress = args.length >= 3 ? Integer.valueOf(args[2]) : 0;
            	player =  args.length < 4 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[3]);
            }else{
            	player =  args.length < 3 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[2]);
            }

            IRecipeController cap = player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null);
            
        	switch(args[0]){
        		case "lock":
        			cap.lockRecipe(args[1]);
        			DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,1,0F), player);
        			if(!cap.isLocked(recipe)){
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been removed from locked recipes for " + player.getDisplayNameString())); 
        			}else{
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been added to locked recipes for " + player.getDisplayNameString())); 
        			}
        			break;
        			
        		case "unlock":
        			cap.unlockRecipe(args[1]);
    				DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,0,0F), player);
    				if(!cap.isUnlocked(recipe)){
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been removed from unlocked recipes for " + player.getDisplayNameString())); 
        			}else{
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been added to unlocked recipes for " + player.getDisplayNameString())); 
        			}
        			break;
        			
        		case "progress":
        			if(cap.isUnlocked(recipe)){
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" couldn't be progressed as it has already been unlocked for " + player.getDisplayNameString())); 
                        return;
        			}else if(progress == 0){
            			player.sendMessage(new TextComponentString("Progress mode requires an amount value! (0-100)"));
        			}
        			
        			cap.progressRecipe(args[1],Float.valueOf(progress) / 100);
    				DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,2,Float.valueOf(progress) / 100), player);
    				
    				if(cap.isUnlocked(recipe)){
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been unlocked for " + player.getDisplayNameString() + " as it was progressed by 100%")); 
                        return;
        			}else{
                        sender.sendMessage(new TextComponentString("Recipe \"" + recipe + "\" has been progressed for for " + player.getDisplayNameString() + " by "  + progress + "%, total progress: " + cap.getProgressRecipe(recipe) * 100 + "%")); 
        			}
                    sender.sendMessage(new TextComponentString("Progressed: [" + recipe + "] by [" + progress + "%]")); 
                    break;
                    
        		case "list":
        			List<String> locked = cap.getLockedRecipes();
        			List<String> unlocked = cap.getUnlockedRecipes();
        			Map<String, Float> progressed = cap.getProgressedRecipes();
        			
        			byte currentRecipe = 0;
        			String combinedRecipes = "";
        			
        			player.interactionManager.getGameType();
        			
        			if(locked.size() > 0){
	        			player.sendMessage(new TextComponentString("Locked Recipes:"));
	        			for(String str : locked){
	        				combinedRecipes = combinedRecipes + "," + str;
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.sendMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.sendMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			if(unlocked.size() > 0){
	        			player.sendMessage(new TextComponentString("Unlocked Recipes:"));
	        			for(String str : unlocked){
	        				combinedRecipes = combinedRecipes + "," + str;
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.sendMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.sendMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			if(progressed.size() > 0){
	        			player.sendMessage(new TextComponentString("Progressed Recipes:"));
	        			for(String str : progressed.keySet()){
	        				combinedRecipes = combinedRecipes + "," + str + "->" + progressed.get(str);
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.sendMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.sendMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			break;
        			
        		default:
        			player.sendMessage(new TextComponentString("Unknown mode! Available: lock, unlock, progress, list"));
        			break;
        	}
        }
    } 

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(4, "drp.command.recipe");
	}


	private static final ArrayList<String> args0 = new ArrayList<String>(){{add("lock"); add("unlock"); add("progress"); add("list");}};
	private static final ArrayList<String> args2 = new ArrayList<String>(){{add("add"); add("remove");}};
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
//		return "drprecipe <lock/unlock/progress/list> <recipe name> [progress value] [player]";

		if(args.length == 1){
			return args0;
		}else if(args.length == 2 && !args[0].equals("list")){
			return CraftingRegistry.getRecipeNames();
		}else if(args.length == 2 && args[0].equals("list")){
			return Arrays.asList(server.getOnlinePlayerNames());
		}else if(args.length == 3 && args[0].equals("progress")){
			return null;
		}else if(args.length == 3 || (args.length == 4 && !args[2].equals("progress"))){
			return Arrays.asList(server.getOnlinePlayerNames());
		}
		
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
