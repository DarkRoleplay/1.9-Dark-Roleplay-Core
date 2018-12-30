package net.dark_roleplay.core.common.objects.packets.debug;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.entity.player.EntityPlayer;

public class Packet_DebugKey extends PacketBase.Server<Packet_DebugKey>{

	public Packet_DebugKey(){}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	@Override
	public void handleServerSide(Packet_DebugKey message, EntityPlayer player) {

//		World world = player.world;
//
//        Vec3d vec3d2 = player.getLookVec();
//
//        EntityDragonFireball dragonfire = new EntityDragonFireball(world, player, vec3d2.x * 0.2, vec3d2.y * 0.2, vec3d2.z * 0.2);
//        world.spawnEntity(dragonfire);


//		player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.EXTENDED_INVENTORY, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

//		player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_EXTENDED_INVENTORY, player.getEntityWorld(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
	}
}