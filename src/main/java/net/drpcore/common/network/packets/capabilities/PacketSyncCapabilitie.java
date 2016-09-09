package net.drpcore.common.network.packets.capabilities;

import io.netty.buffer.ByteBuf;
import net.drpcore.common.network.PacketBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketSyncCapabilitie extends PacketBase<PacketSyncCapabilitie>{
	
    public String identifier;
    public NBTTagCompound data;
    
    public PacketSyncCapabilitie() {}
    
    public PacketSyncCapabilitie(Capability<?> capability, NBTTagCompound data)
    {
        if (data == null) throw new IllegalArgumentException("Data cannot be null!");
        
        this.identifier = capability.getName();
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.identifier = ByteBufUtils.readUTF8String(buf);
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.identifier);
        ByteBufUtils.writeTag(buf, this.data);
    }
    
    /*@Override
    public IMessage onMessage(MessageUpdateStat message, MessageContext ctx)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        
        
        
        return null;
    }
*/
	@Override
	public void handleClientSide(PacketSyncCapabilitie message, EntityPlayer player) {

		if (player != null)
        {
           // Capability<IPlayerStat> capability = (Capability<IPlayerStat>)PlayerStatRegistry.getCapability(message.identifier);
          //  StatHandlerBase stat = (StatHandlerBase)player.getCapability(capability, null);
            
           // capability.getStorage().readNBT(capability, stat, null, message.data);
        }
	}

	@Override
	public void handleServerSide(PacketSyncCapabilitie message, EntityPlayer player) {

		// TODO Auto-generated method stub
		
	}}
