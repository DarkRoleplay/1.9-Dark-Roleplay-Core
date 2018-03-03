package net.dark_roleplay.drpcore.modules.work_in_progress.update_check;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.google.common.collect.Sets;

import net.dark_roleplay.drpcore.api.Modules;
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

	private List<UpdateInfo> mods = new ArrayList<UpdateInfo>();
	
	private String localizedTitle;
	
	private int currentMod = 0;
	
	private GuiButton btnDownload;
	private GuiButton btnLater;
	private GuiButton btnSkip;
	private GuiButton btnDont;

    private URI clickedLinkURI;
    
	public Gui_UpdateInformation(){
		Set<ModContainer> mods = Modules.UPDATE_CHECKER.results.keySet();
		
		for(ModContainer mod : mods) {
			CheckResult res = Modules.UPDATE_CHECKER.results.get(mod);
			if(res.status == ForgeVersion.Status.OUTDATED) {
				this.mods.add(new UpdateInfo(mod, res));
			}
		}
		
		if(mods.isEmpty()) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public void initGui() {
		this.addButton(btnDownload = new GuiButton(0, this.width - 105, this.height - 25, 100, 20, "Download"));
		this.addButton(btnLater = new GuiButton(1, this.width - 210, this.height - 25, 100, 20, "Later"));
		this.addButton(btnSkip = new GuiButton(2, 110, this.height - 25, 100, 20, "Skip"));
		this.addButton(btnDont = new GuiButton(3, 5, this.height - 25, 100, 20, "Don't show again"));

	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		if(this.currentMod >= this.mods.size()) {
			this.mc.displayGuiScreen(null);
		}
		this.drawBackground(0);
		
		if((mods.size() - this.currentMod) == 1) {
			this.fontRenderer.drawString("There's 1 mod outdated!", 7, 5,0xFFFFFFFF);
		}else {
			this.fontRenderer.drawString("There are " + (mods.size() - this.currentMod) + " mods outdated!", 7, 5,0xFFFFFFFF);
		}
		
		this.drawGradientRect(0, 30, this.width, this.height - 30, 0x80000000, 0x80000000);
		
		int offsetTop = 25;
		
		UpdateInfo info = this.mods.get(this.currentMod);
		
		this.fontRenderer.drawString("Changelog - " + info.getModname(), 7, 18, 0xFFFFFFFF);
		
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
		}
		
		this.currentMod += 1;
    }
	
    private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
	private void downloadMod() {
		try{
            URI uri = this.mods.get(this.currentMod).getUpdateURL();
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
