package net.dark_roleplay.drpcore.common.network.packets.crafting;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Initialize_SimpleRecipe  extends PacketBase<Initialize_SimpleRecipe>{

	private String recipeID;
	
	public Initialize_SimpleRecipe(){
		this.recipeID = null;
	}
	
	/**
	 * User to sync RecipeControllerCapability to Player
	 * @param recipe registryName of Recipe
	 */
	public Initialize_SimpleRecipe(String recipe){
		this.recipeID = recipe;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.recipeID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.recipeID);
	}

	@Override
	public void handleClientSide(Initialize_SimpleRecipe message, EntityPlayer player) {
		
	}

	@Override
	public void handleServerSide(Initialize_SimpleRecipe message, EntityPlayer player) {
		SimpleRecipe recipe = CraftingRegistry.getRecipe(message.recipeID);
		recipe.getCrafter().initializeCrafting(player, recipe);
	}

}