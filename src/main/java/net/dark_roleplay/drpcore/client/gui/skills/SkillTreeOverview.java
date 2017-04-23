package net.dark_roleplay.drpcore.client.gui.skills;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.api.gui.ITimedGui;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.skills.SkillItem;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.dark_roleplay.drpcore.common.skills.SkillTree;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SkillTreeOverview extends DRPGuiScreen implements ITimedGui{

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill_tree.png");
	private static ResourceLocation skill_tree_bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill_tree_default_background.png");

	private int lastX = 0;
	private int lastY = 0;
	private int skill_tree_bg_offsetX = 7;
	private int skill_tree_bg_offsetY = 69;
	
	private int hoveringOverUnlock;
	
	private SkillTree tree;
	
	public SkillTreeOverview() {
		super(bg, 256, 218);
		tree = SkillRegistry.getSkillTree(0);
		//this.skill_tree_bg = tree.
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float partialTicks){
		
    	super.drawBackground(mouseX, mouseY, partialTicks);
        GlStateManager.color(1f, 1f, 1f, 1f);
    	
    	if (Mouse.isButtonDown(0)){
            int moveX = lastX - mouseX;
            int moveY = lastY - mouseY;
            
            if(this.skill_tree_bg_offsetX + moveX > 14) this.skill_tree_bg_offsetX = 14;
            else if(this.skill_tree_bg_offsetX + moveX < 0) this.skill_tree_bg_offsetX = 0;
            else this.skill_tree_bg_offsetX = this.skill_tree_bg_offsetX + moveX;
            
            if(this.skill_tree_bg_offsetY + moveY > 117) this.skill_tree_bg_offsetY = 117;
            else if(this.skill_tree_bg_offsetY + moveY < 0) this.skill_tree_bg_offsetY = 0;
            else this.skill_tree_bg_offsetY = this.skill_tree_bg_offsetY + moveY;
        }
    	
        lastX = mouseX;
        lastY = mouseY;
    	
    	GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.renderEngine.bindTexture(this.skill_tree_bg);
		this.drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 23, this.skill_tree_bg_offsetX, this.skill_tree_bg_offsetY, 242, 139);
    	this.mc.renderEngine.bindTexture(this.bg);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	
    	for(SkillItem item : tree.getSkills()){
    		int x = 7 + (item.getX() - 1) * 16 - skill_tree_bg_offsetX;
    		int y = 23 + (item.getY() - 1) * 16 - skill_tree_bg_offsetY;
    		
    		for(SkillItem parent : item.getParents()){
    			int xParent = 7 + (parent.getX() - 1) * 16 - skill_tree_bg_offsetX;
        		int yParent = 23 + (parent.getY() - 1) * 16 - skill_tree_bg_offsetY;
    			this.drawLine(this.guiLeft + x + 8, this.guiTop + y + 8, this.guiLeft + xParent + 8, this.guiTop + yParent + 8, new Color(255,255,255).getRGB());
    		}
    		
    		if(x >= 7 && y >= 23){
    	    	this.mc.renderEngine.bindTexture(this.bg);
    			this.drawTexturedModalRect(this.guiLeft + x - 5, this.guiTop + y - 5, 0, 218, 26, 26);
    			this.itemRender.renderItemIntoGUI(new ItemStack(item.getDisplayTexture(),1,0), this.guiLeft + x, this.guiTop + y);

    		}
    	}    	
    }

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		this.mc.renderEngine.bindTexture(this.bgTexture);
		
	}

	@Override
	public void tick() {
		hoveringOverUnlock++;
	}

}
