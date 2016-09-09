package net.drpcore.api.guis.guis;

import org.lwjgl.opengl.GL11;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public abstract class DRPGuiScreen extends GuiScreen{
	
	protected ResourceLocation bgTexture; // = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png");

	protected int bgWidth = 0;
	protected int bgHeight = 0;
	
	protected int bgOffsetX = 0;
	protected int bgOffsetY = 0;

	protected int guiTop = 0;
	protected int guiLeft = 0;
	
	protected int startButtonID = 0;
	protected int startInfoFieldID = 50;
	
	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight){
		this(bgTexture,bgWidth,bgHeight,0,0);
	}
	
	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgOffsetX, int bgOffsetY){
		this.bgTexture = bgTexture;
		this.bgWidth = bgWidth;
		this.bgHeight = bgHeight;
	}
	
	private void reAdjust(){
		this.guiLeft = ((this.width - this.bgWidth) / 2) + this.bgOffsetX;
    	this.guiTop = ((this.height - this.bgHeight) / 2) + this.bgOffsetY;
	}
	
	 public void initGui(){
		 this.reAdjust();
	 }
	
	//-------------------------------------------------- Drawing --------------------------------------------------
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	this.reAdjust();

		RenderHelper.enableGUIStandardItemLighting();
		
		//Background Texture
		this.drawBackground(mouseX, mouseY, partialTicks);
    	
		//Slots & Buttons
    	this.drawMiddleground(mouseX, mouseY, partialTicks);
    	
    	//Hovering Stuff and other
    	this.drawForeground(mouseX, mouseY, partialTicks);

    }
    
    protected void drawBackground(int mouseX, int mouseY, float partialTicks){
    	this.drawBGTexture();
    }

	
    protected void drawMiddleground(int mouseX, int mouseY, float partialTicks){
    	this.drawButtons(mouseX, mouseY, partialTicks);
    }
    
    
    protected abstract void drawForeground(int mouseX, int mouseY, float partialTicks);
	
    
    protected void drawBGTexture(){
    	GL11.glDisable(GL11.GL_LIGHTING);
    	this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(this.bgTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.bgWidth, this.bgHeight);
		GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    protected void drawButtons(int mouseX, int mouseY, float partialTicks){
        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }
    }
}
