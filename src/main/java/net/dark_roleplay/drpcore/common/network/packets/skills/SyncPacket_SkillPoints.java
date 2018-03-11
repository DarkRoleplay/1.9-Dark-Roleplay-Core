package net.dark_roleplay.drpcore.common.network.packets.skills;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncPacket_SkillPoints extends PacketBase<SyncPacket_SkillPoints>{

	private String skillPoint;
	private int amount = 0;
	private int level = 0;
	private int xp = 0;
	
	public SyncPacket_SkillPoints(){
		this.skillPoint = null;
		this.amount = 0;
		this.level = 0;
		this.xp = 0;
	}
	
	public SyncPacket_SkillPoints(String skillPoint, int amount, int level, int xp){
		this.skillPoint = skillPoint;
		this.amount = amount;
		this.level = level;
		this.xp = xp;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.skillPoint = ByteBufUtils.readUTF8String(buf);
		this.amount = buf.readInt();
		this.level = buf.readInt();
		this.xp = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.skillPoint);
		buf.writeInt(this.amount);
		buf.writeInt(this.level);
		buf.writeInt(this.xp);
	}

	@Override
	public void handleClientSide(SyncPacket_SkillPoints message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
			public void run() {
				processMessage(message);
			}
		});
	}

	@Override
	public void handleServerSide(SyncPacket_SkillPoints message, EntityPlayer player) {}

	@SideOnly(Side.CLIENT)
	public void processMessage(SyncPacket_SkillPoints message){
//		EntityPlayer player = Minecraft.getMinecraft().player;
//		
//		player = Minecraft.getMinecraft().player;
//		ISkillController controller = (ISkillController) Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
//		SkillPoint point = SkillRegistry.getSkillPointByName(message.skillPoint);
//		SkillPointData data = new SkillPointData(point, message.amount, message.level, message.xp);
//		controller.setSkillPointData(data);
	}
}
