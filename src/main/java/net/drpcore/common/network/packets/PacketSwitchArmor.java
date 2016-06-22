package net.drpcore.common.network.packets;

import java.util.List;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.items.templates.PurseBase;
import net.drpcore.common.network.PacketBase;
import net.drpcore.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;


public class PacketSwitchArmor extends PacketBase<PacketSwitchArmor> {

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleClientSide(PacketSwitchArmor message, EntityPlayer player) {}

	@Override
	public void handleServerSide(PacketSwitchArmor message, EntityPlayer player) {

		AdvancedPlayerInventory advancedInventory = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
		ItemStack[] armorInventory = player.inventory.armorInventory;
		ItemStack[] backUpStack = armorInventory.clone();
		for(int i = 0; i < 4; i++ ) {
			if(advancedInventory.getStackInSlot(advancedInventory.getSizeInventory() - 1 - i) == null)
				player.inventory.removeStackFromSlot(36 + i);
			else
				player.inventory.setInventorySlotContents(36 + i, advancedInventory.getStackInSlot(advancedInventory.getSizeInventory() - 1 - i));
			advancedInventory.setInventorySlotContents(advancedInventory.getSizeInventory() - 1 - i, backUpStack[i]);
		}
		if(DarkRoleplayCore.isServer == Side.SERVER)
			for(EntityPlayerMP other : (List<EntityPlayerMP>) FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList()) {
				for(int i = 0; i < advancedInventory.getSizeInventory(); i++ )
					PacketHandler.sendTo(new PacketSyncAdvancedInventory(i, player), other);
			}
	}
}
