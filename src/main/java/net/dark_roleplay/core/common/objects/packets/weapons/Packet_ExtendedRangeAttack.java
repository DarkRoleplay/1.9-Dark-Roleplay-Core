package net.dark_roleplay.core.common.objects.packets.weapons;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.core.api.old.items.weapons.IExtendedRange;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Packet_ExtendedRangeAttack extends PacketBase.Server<Packet_ExtendedRangeAttack>{

	private int entityID ;

    public Packet_ExtendedRangeAttack(){}

    public Packet_ExtendedRangeAttack(int entityID){
    	this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf){
    	this.entityID = ByteBufUtils.readVarInt(buf, 4);
    }

    @Override
    public void toBytes(ByteBuf buf){
    	ByteBufUtils.writeVarInt(buf, this.entityID, 4);
    }

	@Override
	public void handleServerSide(Packet_ExtendedRangeAttack message, EntityPlayer player) {
		player.getServer().addScheduledTask(
				() ->	{
                        Entity entity = player.getEntityWorld().getEntityByID(message.entityID);

                        if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof IExtendedRange){

                        	IExtendedRange weapon = (IExtendedRange)player.getHeldItemMainhand().getItem();
                            double distanceSq = player.getDistanceSq(entity);
                            double reachSq = Math.pow(weapon.getRange(), 2);
                            if (reachSq >= distanceSq){
                                player.attackTargetEntityWithCurrentItem(entity);
                            }
                        }
                }
          );
	}
}
