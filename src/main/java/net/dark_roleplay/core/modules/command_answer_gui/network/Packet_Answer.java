package net.dark_roleplay.core.modules.command_answer_gui.network;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.dark_roleplay.library.networking.PacketBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class Packet_Answer extends PacketBase.Server<Packet_Answer>{

	String scoreboard;
	int answer;
	
	public Packet_Answer() {
		this.answer = 0;
		this.scoreboard = "";
	}
	
	public Packet_Answer(String scoreboard, int answer) {
		this.scoreboard = scoreboard;
		this.answer = answer;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.answer = buf.readInt();
		this.scoreboard = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.answer);
		ByteBufUtils.writeUTF8String(buf, scoreboard);
		
	}

	@Override
	public void handleServerSide(Packet_Answer message, EntityPlayer player) {
		player.getServer().addScheduledTask(new Runnable(){
			@Override
			public void run() {
				Scoreboard board = player.getWorldScoreboard();
				ScoreObjective objective = board.getObjective(message.scoreboard);
				Map<ScoreObjective, Score> scores = board.getObjectivesForEntity(player.getName());
				if(scores.containsKey(objective)) {
					Score score = scores.get(objective);
					score.setScorePoints(message.answer);
				}
			}
		});
	}
}
