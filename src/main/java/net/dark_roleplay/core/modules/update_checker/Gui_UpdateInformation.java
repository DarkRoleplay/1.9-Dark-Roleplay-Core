package net.dark_roleplay.core.modules.update_checker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.common.collect.Sets;

import net.dark_roleplay.core.modules.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;

public class Gui_UpdateInformation extends GuiScreen{

	
	private String localizedTitle;
	
	private int currentMod = 0;
	
	private GuiButton btnDownload;
	private GuiButton btnNext;
	private GuiButton btnPrev;
	private GuiButton btnClose;

    private URI clickedLinkURI;
    
	public Gui_UpdateInformation(){
		
		
		if(Modules.UPDATE_CHECKER.mods.isEmpty()) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public void initGui() {
		int buttonWidht = (this.width - 20) /5;
		this.addButton(btnDownload = new GuiButton(0, this.width - (buttonWidht + 5), this.height - 25, buttonWidht, 20, "Download"));
		this.addButton(btnNext = new GuiButton(1, this.width - ((buttonWidht + 5) * 2), this.height - 25, buttonWidht, 20, "Next"));
		this.addButton(btnPrev = new GuiButton(2, buttonWidht + 10, this.height - 25, buttonWidht, 20, "Prev"));
		this.addButton(btnClose = new GuiButton(3, 5, this.height - 25, buttonWidht, 20, "Exit"));

	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		if(this.currentMod >= Modules.UPDATE_CHECKER.mods.size()) {
			this.mc.displayGuiScreen(null);
		}
		this.drawBackground(0);
		
		if((Modules.UPDATE_CHECKER.mods.size()) == 1) {
			this.fontRenderer.drawString("There's 1 mod outdated!", 7, 5,0xFFFFFFFF);
		}else {
			this.fontRenderer.drawString("There are " + Modules.UPDATE_CHECKER.mods.size() + " mods outdated!", 7, 5,0xFFFFFFFF);
		}
		
		this.drawGradientRect(0, 30, this.width, this.height - 30, 0x80000000, 0x80000000);
		
		int offsetTop = 25;
		
		UpdateInfo info = Modules.UPDATE_CHECKER.mods.get(this.currentMod);
		if(info.getChangelog().isEmpty()) {
			this.fontRenderer.drawString("No Changelog provided :(", 15, offsetTop + 10, 0xFFFFFFFF);
		}
		
		this.fontRenderer.drawString("Changelog - " + info.getModname(), 7, 18, 0xFFFFFFFF);
		
		this.fontRenderer.drawString(this.currentMod + 1 + "/" + Modules.UPDATE_CHECKER.mods.size(), (this.width - this.fontRenderer.getStringWidth(this.currentMod + 1 + "/" + Modules.UPDATE_CHECKER.mods.size())) / 2, this.height - 20, 0xFFFFFFFF);
		
		for(int i = 0; i < info.getVersions().size(); i++) {
			this.fontRenderer.drawString(info.getVersions().get(i), 15, offsetTop += 10, 0xFFFFFFFF);
			this.fontRenderer.drawSplitString(info.getChangelog().get(i), 20, offsetTop += 10, this.width - 30, 0xFFFFFFFF);
			offsetTop += this.fontRenderer.listFormattedStringToWidth(info.getChangelog().get(i), this.width - 30).size() * 8;
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	protected void actionPerformed(GuiButton button) throws IOException{
		if(button == btnDownload) {
			downloadMod();
		}else if(button == btnNext && this.currentMod + 1 < Modules.UPDATE_CHECKER.mods.size()) {
			this.currentMod ++;
		}else if(button == btnPrev && this.currentMod - 1 >= 0) {
			this.currentMod --;
		}else if(button == btnClose) {
			Minecraft.getMinecraft().displayGuiScreen(null);;
		}
		
    }
	
    private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
	private void downloadMod() {
		try{
            URI uri = Modules.UPDATE_CHECKER.mods.get(this.currentMod).getUpdateURL();
            String s = uri.getScheme();

            if (s == null) {
                throw new URISyntaxException(uri.toString(), "Missing protocol");
            }else if (!PROTOCOLS.contains(s.toLowerCase(Locale.ROOT))){
                throw new URISyntaxException(uri.toString(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
            }else if (this.mc.gameSettings.chatLinksPrompt){
                this.clickedLinkURI = uri;
                this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, uri.toString(), 31102009, false));
            }else{
                this.openWebLink(uri);
            }
        }catch (URISyntaxException urisyntaxexception){}
	}
	
	private void openWebLink(URI url){
        try{
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke((Object)null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        }catch (Throwable throwable1){
            Throwable throwable = throwable1.getCause();
        }
    }
}
