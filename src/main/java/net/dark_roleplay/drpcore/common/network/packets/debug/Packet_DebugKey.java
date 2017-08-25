package net.dark_roleplay.drpcore.common.network.packets.debug;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.api.schematic.Schematic;
import net.dark_roleplay.drpcore.api.schematic.SchematicUtil;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipeSerializationUtil;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.dark_roleplay.drpcore.common.network.packets.config.SyncPacket_Boolean;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Packet_DebugKey extends PacketBase<Packet_DebugKey>{

	public Packet_DebugKey(){}
	
	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleClientSide(Packet_DebugKey message, EntityPlayer player) {}

	@Override
	public void handleServerSide(Packet_DebugKey message, EntityPlayer player) {

		player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_EXTENDED_INVENTORY, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
	}
}