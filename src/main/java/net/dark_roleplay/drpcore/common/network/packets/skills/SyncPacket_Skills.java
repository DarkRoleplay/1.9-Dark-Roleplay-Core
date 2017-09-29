package net.dark_roleplay.drpcore.common.network.packets.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillData;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.network.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncPacket_Skills extends PacketBase.Client<SyncPacket_Skills>{

	List<SkillData> skills;
	
	public SyncPacket_Skills(){
		this.skills = new ArrayList<SkillData>();
	}

	public SyncPacket_Skills(EntityPlayer player){
		ISkillController oldSkills = Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
		this.skills = oldSkills.getSkills();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(skills.size());
		for(SkillData data : skills){
			data.writeToBuf(buf);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int size = buf.readInt();
		for(int i = 0; i < size; i++){
			SkillData data = new SkillData().readFromBuf(buf);
			skills.add(data);
		}
	}

	@Override
	public void handleClientSide(SyncPacket_Skills message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable(){
			public void run() {
				processMessage(message);
			}
		});
	}

	@SideOnly(Side.CLIENT)
	public void processMessage(SyncPacket_Skills message){
		ISkillController newSkills = Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
		newSkills.setSkills(message.skills);
	}

}