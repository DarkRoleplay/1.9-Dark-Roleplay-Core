package net.dark_roleplay.drpcore.api.commands;

import java.util.UUID;

import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public abstract class DRPCommand implements ICommand{

	
	public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException{
        if (sender instanceof EntityPlayerMP){
            return (EntityPlayerMP)sender;
        }else{
            throw new PlayerNotFoundException("commands.generic.player.unspecified");
        }
    }
	
	public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException{
        ResourceLocation resourcelocation = new ResourceLocation(id);
        Item item = (Item)Item.REGISTRY.getObject(resourcelocation);

        if (item == null){
            throw new NumberInvalidException("commands.give.item.notFound", new Object[] {resourcelocation});
        }else{
            return item;
        }
    }
	
	public static EntityPlayerMP getPlayer(MinecraftServer server, ICommandSender sender, String target) throws PlayerNotFoundException, CommandException{
        EntityPlayerMP entityplayermp = EntitySelector.matchOnePlayer(sender, target);

        if (entityplayermp == null){
            try{
                entityplayermp = server.getPlayerList().getPlayerByUUID(UUID.fromString(target));
            }catch (IllegalArgumentException var5){}
        }

        if (entityplayermp == null){
            entityplayermp = server.getPlayerList().getPlayerByUsername(target);
        }

        if (entityplayermp == null){
            throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] {target});
        }else{
            return entityplayermp;
        }
    }
	
}
