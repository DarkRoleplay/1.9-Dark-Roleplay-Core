package net.dark_roleplay.drpcore.modules.work_in_progress.update_check;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Mod;

public class Gui_UpdateInformation extends GuiScreen{

	public List<UpdateInfo> mods = new ArrayList<UpdateInfo>();
	
	private String localizedTitle;
	
	public Gui_UpdateInformation(){
		for(Mod mod : Module_UpdateChecker.modsToCheck){
			CheckResult result = Module_UpdateChecker.results.get(mod.modid());
			if(result.status == ForgeVersion.Status.OUTDATED || result.status == ForgeVersion.Status.BETA_OUTDATED)
				mods.add(new UpdateInfo(mod, result));
		}
		this.localizedTitle = I18n.format("drp.update_checker.gui.title");
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		
		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
}
