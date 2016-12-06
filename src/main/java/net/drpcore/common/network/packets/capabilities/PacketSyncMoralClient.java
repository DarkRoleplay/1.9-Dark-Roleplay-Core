package net.drpcore.common.network.packets.capabilities;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.capabilitiesOld.DRPCoreCapabilities;
import net.drpcore.common.capabilitiesOld.entities.player.moral.MoralHandler;
import net.drpcore.common.network.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public class PacketSyncMoralClient extends PacketBase<PacketSyncMoralClient>{

	public float moralLevel;
	
	public PacketSyncMoralClient(float moralLevel){
		this.moralLevel = moralLevel;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer packetBuffer = new PacketBuffer(buf);
        
        this.moralLevel = packetBuffer.readFloat();

	}

	@Override
	public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        
        packetBuffer.writeFloat(this.moralLevel);
	}

	@Override
	public void handleClientSide(PacketSyncMoralClient message, EntityPlayer player) {

        if (player != null)
        {
            MoralHandler temperatureStats = (MoralHandler)player.getCapability(DRPCoreCapabilities.MORAL, null);
           /* TemperatureDebugger debugger = temperatureStats.debugger;
            
            debugger.temperatureTimer = message.temperatureTimer;
            debugger.changeTicks = message.changeTicks;
            debugger.targetTemperature = message.targetTemperature;
            debugger.modifiers = message.modifiers;*/
        }
	}

	@Override
	public void handleServerSide(PacketSyncMoralClient message, EntityPlayer player) {}
	
}
