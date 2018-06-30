package net.dark_roleplay.core.modules.command_answer_gui.network;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.core.modules.command_answer_gui.Gui_CommandAnswer;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Packet_OpenGui extends PacketBase.Client<Packet_OpenGui>{

	String message;
	String scoreboard;
	
	AnswerType type;
	String[] answers;
	
	public Packet_OpenGui() {
		this.message = "";
		this.type = AnswerType.INT_INPUT;
		this.answers = new String[0];
		this.scoreboard = "";
	}
	
	public Packet_OpenGui(String message, String scoreboard, AnswerType type, String... answers) {
		this.message = message;
		this.type = type;
		this.answers = answers;
		this.scoreboard = scoreboard;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.type = AnswerType.values()[buf.readInt()];
		this.message = ByteBufUtils.readUTF8String(buf);
		this.scoreboard = ByteBufUtils.readUTF8String(buf);
		int answers = buf.readInt();
		this.answers = new String[answers];
		for(int i = 0; i < answers; i++) {
			this.answers[i] = ByteBufUtils.readUTF8String(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.type.ordinal());
		ByteBufUtils.writeUTF8String(buf, this.message);
		ByteBufUtils.writeUTF8String(buf, this.scoreboard);
		buf.writeInt(this.answers.length);
		for(int i = 0; i < answers.length; i++) {
			ByteBufUtils.writeUTF8String(buf, message);
		}
	}

	@Override
	public void handleClientSide(Packet_OpenGui message, EntityPlayer player) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			@Override
			public void run() {
				Minecraft.getMinecraft().displayGuiScreen(new Gui_CommandAnswer(message.message, message.scoreboard, message.type, message.answers));
			}
		});
	}

}
