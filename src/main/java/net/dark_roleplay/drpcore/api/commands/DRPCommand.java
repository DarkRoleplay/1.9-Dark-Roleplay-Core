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

	protected String name;
	
	public DRPCommand(String name){
		this.name = name;
	}
	
	/**
	 * Trys to get the Sender as a Player
	 * @param sender
	 * @return EntityPlayerMP
	 * @throws PlayerNotFoundException
	 */
	public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException{
        if (sender instanceof EntityPlayerMP){
            return (EntityPlayerMP)sender;
        }else{
            throw new PlayerNotFoundException("commands.generic.player.unspecified");
        }
    }
	
	/**
	 * Returns an Item by it's registry name
	 * @param sender
	 * @param id
	 * @return Item
	 * @throws NumberInvalidException
	 */
	public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException{
        ResourceLocation resourcelocation = new ResourceLocation(id);
        Item item = (Item)Item.REGISTRY.getObject(resourcelocation);

        if (item == null){
            throw new NumberInvalidException("commands.give.item.notFound", new Object[] {resourcelocation});
        }else{
            return item;
        }
    }
	
	/**
	 * Trys to get a Player Object by the Players name
	 * @param server
	 * @param sender
	 * @param target
	 * @return
	 * @throws PlayerNotFoundException
	 * @throws CommandException
	 */
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
	
	@Override
	public String getName() {
		return "drpskills";
	}
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
