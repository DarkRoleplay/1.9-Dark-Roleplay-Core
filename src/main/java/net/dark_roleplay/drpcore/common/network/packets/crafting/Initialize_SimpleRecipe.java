package net.dark_roleplay.drpcore.common.network.packets.crafting;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Initialize_SimpleRecipe extends PacketBase<Initialize_SimpleRecipe> {

	private String recipeID;

	private int multiplier;

	public Initialize_SimpleRecipe() {
		this.recipeID = null;
		this.multiplier = 1;
	}

	/**
	 * User to sync RecipeControllerCapability to Player
	 * 
	 * @param recipe
	 *            registryName of Recipe
	 */
	public Initialize_SimpleRecipe(String recipe, int multiplier) {
		this.recipeID = recipe;
		this.multiplier = multiplier;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.multiplier = buf.readInt();
		this.recipeID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.multiplier);
		ByteBufUtils.writeUTF8String(buf, this.recipeID);
	}

	@Override
	public void handleClientSide(Initialize_SimpleRecipe message, EntityPlayer player) {

	}

	@Override
	public void handleServerSide(Initialize_SimpleRecipe message, EntityPlayer player) {
		player.getServer().addScheduledTask(
			new Runnable() {
				public void run() {
					SimpleRecipe recipe = CraftingRegistry.getRecipe(message.recipeID);
					recipe.getCrafter().initializeCrafting(player, recipe, message.multiplier);
				}
			}
		);
	}
}