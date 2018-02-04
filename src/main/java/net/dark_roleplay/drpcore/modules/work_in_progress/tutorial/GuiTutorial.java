package net.dark_roleplay.drpcore.modules.work_in_progress.tutorial;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiTutorial extends GuiScreen{
	
	protected GuiScreen bg;

	public GuiTutorial(GuiScreen bg){
		this.bg = bg;
		this.bg.setWorldAndResolution(Minecraft.getMinecraft(), width, height);
	}
	
	@Override
	public void initGui(){
		this.bg.width = this.width;
		this.bg.height = this.height;
		this.bg.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.mc.getFramebuffer().framebufferClear();
		super.drawScreen(mouseX, mouseY, partialTicks);
		bg.drawScreen(mouseX, mouseY, partialTicks);
	
//		this.drawGradientRect(400, 0, 500, 100, 0xFFFF0000, 0xFF00FF00);
    }
	
	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height){
		super.setWorldAndResolution(mc, width, height);
		this.bg.setWorldAndResolution(mc, width, height);
	}
}
