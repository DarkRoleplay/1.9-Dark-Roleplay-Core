package net.dark_roleplay.core.modules.command_answer_gui;

import java.io.IOException;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core.modules.command_answer_gui.network.AnswerType;
import net.dark_roleplay.core.modules.command_answer_gui.network.Packet_Answer;
import net.dark_roleplay.core.testing.gui_testing.Gui_Test;
import net.dark_roleplay.core.testing.gui_testing.components.CheckBox;
import net.dark_roleplay.core.testing.gui_testing.components.TextBox;
import net.dark_roleplay.library_old.wrapper.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;

public class Gui_CommandAnswer extends Gui_Test{

	String message;
	String scoreboard;
	AnswerType type;
	String[] answers;
	
	public Gui_CommandAnswer(String message, String scoreboard, AnswerType type, String... answers) {
		this.message = message;
		this.type = type;
		this.answers = answers;
		this.scoreboard = scoreboard;
	}
	
	@Override
	public void initGui() {
		comps.clear();
		
		int id = 0;
		
		GuiLabel lblRB = new GuiLabel(this.fontRenderer, id++, 20, 20, 100, 24, 0xFFFFFFFF);
		String[] message = this.message.split("\n");
		for(String line : message) {
			lblRB.addLine(line);
		}
		this.labelList.add(lblRB);

		switch(type) {
			case BUTTONS:
				
				break;
			case INT_INPUT:
				TextBox tb = new TextBox(this.fontRenderer, 25, 200);
				tb.setWidth(20);
				comps.add(tb);
				CheckBox box = new CheckBox(440, 200, new BooleanWrapper(false)) {
					@Override
					public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
						String input = tb.getText();   
						try{
							int i = Integer.valueOf(input);
							
							DRPCorePackets.sendToServer(new Packet_Answer(Gui_CommandAnswer.this.scoreboard, i));
							Minecraft.getMinecraft().displayGuiScreen(null);
						}catch(Exception e) {
							GuiLabel lblError = new GuiLabel(Gui_CommandAnswer.this.fontRenderer, 30, 25, 240, 100, 24, 0xFFFFFFFF);
							lblError.addLine(I18n.format("drpcore.commands.answer_gui.invalid_input.no_number"));
							Gui_CommandAnswer.this.labelList.add(lblError);
						}
						
						Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
						
						return this.isHovered(mouseX, mouseY);
					}
				};

				comps.add(box);
				break;
			default:
				break;
		}
	}
	
}
