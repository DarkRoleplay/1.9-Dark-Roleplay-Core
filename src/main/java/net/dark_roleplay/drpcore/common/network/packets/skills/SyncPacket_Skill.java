package net.dark_roleplay.drpcore.common.network.packets.skills;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncPacket_Skill extends PacketBase<SyncPacket_Skill>{

	private String skill;
	
	public SyncPacket_Skill(){
		this.skill = null;
	}
	
	public SyncPacket_Skill(String skill){
		this.skill = skill;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.skill = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.skill);
	}

	@Override
	public void handleClientSide(SyncPacket_Skill message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
			public void run() {
				processMessage(message);
			}
		});
	}

	@Override
	public void handleServerSide(SyncPacket_Skill message, EntityPlayer player) {}

	@SideOnly(Side.CLIENT)
	public void processMessage(SyncPacket_Skill message){
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		player = Minecraft.getMinecraft().player;
		Skill skillToUnlock = SkillRegistry.getSkillByName(message.skill);
		if(skillToUnlock == null)
			return;
		ISkillController controller = (ISkillController) Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
		controller.unlockSkill(skillToUnlock);
	}

}
