package net.dark_roleplay.drpcore.common.commands.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.api.commands.DRPCommand;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPlayerRecipeState;
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

public class Command_Recipe extends DRPCommand{
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
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
        			DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,1,0F), player);
        			if(!cap.isLocked(recipe)){
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been removed from locked recipes for " + player.getDisplayNameString())); 
        			}else{
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been added to locked recipes for " + player.getDisplayNameString())); 
        			}
        			break;
        			
        		case "unlock":
        			cap.unlockRecipe(args[1]);
    				DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,0,0F), player);
    				if(!cap.isUnlocked(recipe)){
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been removed from unlocked recipes for " + player.getDisplayNameString())); 
        			}else{
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been added to unlocked recipes for " + player.getDisplayNameString())); 
        			}
        			break;
        			
        		case "progress":
        			if(cap.isUnlocked(recipe)){
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" couldn't be progressed as it has already been unlocked for " + player.getDisplayNameString())); 
                        return;
        			}else if(progress == 0){
            			player.addChatComponentMessage(new TextComponentString("Progress mode requires an amount value! (0-100)"));
        			}
        			
        			cap.progressRecipe(args[1],Float.valueOf(progress) / 100);
    				DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,2,Float.valueOf(progress) / 100), player);
    				
    				if(cap.isUnlocked(recipe)){
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been unlocked for " + player.getDisplayNameString() + " as it was progressed by 100%")); 
                        return;
        			}else{
                        sender.addChatMessage(new TextComponentString("Recipe \"" + recipe + "\" has been progressed for for " + player.getDisplayNameString() + " by "  + progress + "%, total progress: " + cap.getProgressRecipe(recipe) * 100 + "%")); 
        			}
                    sender.addChatMessage(new TextComponentString("Progressed: [" + recipe + "] by [" + progress + "%]")); 
                    break;
                    
        		case "list":
        			List<String> locked = cap.getLockedRecipes();
        			List<String> unlocked = cap.getUnlockedRecipes();
        			Map<String, Float> progressed = cap.getProgressedRecipes();
        			
        			byte currentRecipe = 0;
        			String combinedRecipes = "";
        			
        			if(locked.size() > 0){
	        			player.addChatComponentMessage(new TextComponentString("Locked Recipes:"));
	        			for(String str : locked){
	        				combinedRecipes = combinedRecipes + "," + str;
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.addChatComponentMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.addChatComponentMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			if(unlocked.size() > 0){
	        			player.addChatComponentMessage(new TextComponentString("Unlocked Recipes:"));
	        			for(String str : unlocked){
	        				combinedRecipes = combinedRecipes + "," + str;
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.addChatComponentMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.addChatComponentMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			if(progressed.size() > 0){
	        			player.addChatComponentMessage(new TextComponentString("Progressed Recipes:"));
	        			for(String str : progressed.keySet()){
	        				combinedRecipes = combinedRecipes + "," + str + "->" + progressed.get(str);
	        				currentRecipe ++;
	        				if(currentRecipe == 5){
	        					player.addChatComponentMessage(new TextComponentString(combinedRecipes));
	        					combinedRecipes = "";
	        					currentRecipe = 0;
	        				}
	        			}
	        			if(currentRecipe != 0){
	        				player.addChatComponentMessage(new TextComponentString(combinedRecipes));
        					combinedRecipes = "";
        					currentRecipe = 0;
	        			}

        			}
        			
        			break;
        			
        		default:
        			player.addChatComponentMessage(new TextComponentString("Unknown mode! Available: lock, unlock, progress, list"));
        			//player.sendMessage(new TextComponentString("Unknown mode! Available: lock, unlock, progress, list"));
        			break;
        	}
        }
    } 

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

	@Override
	public String getCommandName() {

		return "recipe";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "recipe <lock/unlock/progress/list> <recipe name> [progress value] [player]";
	}

	@Override
	public List<String> getCommandAliases() {
		 return new ArrayList<String>();
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,BlockPos pos) {
		return null;
	}
}
