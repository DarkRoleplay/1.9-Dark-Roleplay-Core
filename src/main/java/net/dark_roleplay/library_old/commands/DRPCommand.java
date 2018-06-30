package net.dark_roleplay.library_old.commands;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public abstract class DRPCommand implements ICommand{

	protected String name;
	protected List<String> aliases;
	protected String permissionsNode;
	
	/**
	 * You should register the permissions node {@link net.minecraftforge.server.permission.PermissionAPI#registerNode(String, net.minecraftforge.server.permission.DefaultPermissionLevel, String) PermissionApi.registerNode(String, DefaultPermissionLevel, String)}
	 * When using this. It's usefull to keep track of all permissions and also makes it possible for Permission Handling mods to have an ingame List with all available permissions.
	 * @param name
	 * @param permissionsNode
	 * @param aliases
	 */
	public DRPCommand(String name, String permissionsNode, String permissionDescription, String... aliases){
		this.name = name;
		this.permissionsNode = permissionsNode;
		this.aliases = Lists.<String>newArrayList(aliases);
		PermissionAPI.registerNode(permissionsNode, DefaultPermissionLevel.OP, permissionDescription);
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
	 * @param registerName
	 * @return Item
	 * @throws NumberInvalidException
	 */
	public static Item getItemByText(ICommandSender sender, String registerName) throws NumberInvalidException{
        ResourceLocation resourcelocation = new ResourceLocation(registerName);
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
    public List<String> getAliases() {
		return this.aliases;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public int compareTo(ICommand o) {
		return 0;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if(sender instanceof EntityPlayer)
			return PermissionAPI.hasPermission((EntityPlayer) sender, this.permissionsNode);
		else return true;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
