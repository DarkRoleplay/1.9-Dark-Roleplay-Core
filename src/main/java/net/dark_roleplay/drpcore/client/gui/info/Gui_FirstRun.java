package net.dark_roleplay.drpcore.client.gui.info;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.Map;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class Gui_FirstRun extends GuiScreen{

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		
		FontRenderer font;
		font = this.fontRenderer;
		
		font.drawString("press \"ESC\" to close", 5, 5, new Color(255, 255, 255).getRGB());
		
		String message = "Thank you for downloading and installing one of our Dark Roleplay Mods!\n\n\n";
		message += "This is just a small information/warning,\n"
				+ "you can ignore this when you downloaded the mod from \"curse.com\" or \"curseforge.com\"\n"
				+ "(some other sites link to the curse files, but you should always check it)\n\n"
				+ "In case you downloadet it from anywhere else there is the risk that the files contains a virus/trojan.\n\n"
				+ "This warning was mainly implemented because we got multiple complaints that our mods contained viruses/trojans.\n"
				+ "(this information is not going to show up again)\n\n"
				+ "(When you want to stay up to day join our Discord: discord.dark-roleplay.net)";

		font.drawSplitString(message, (this.width / 2) - ((this.width - 100) / 2), (this.height / 2) - ((this.height - 50) / 2), this.width - 100, new Color(255, 255, 255).getRGB());
		
	}
	
}
