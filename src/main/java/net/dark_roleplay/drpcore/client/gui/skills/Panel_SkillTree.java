package net.dark_roleplay.drpcore.client.gui.skills;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Panel;
import net.dark_roleplay.drpcore.api.gui.utility.modulars.ModularBackground;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Panel_SkillTree extends Gui_Panel{

	private static final ResourceLocation skillBG = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill/skill_point_overview.png");
	
	private int offsetX, offsetY;
	private Gui_SkillOverview parent;
	
	protected static final int COLOR_WHITE = new Color(255,255,255).getRGB();
	protected static final int COLOR_DARK_GRAY = new Color(55,55,55).getRGB();
	
	private int lastMouseX = -1, lastMouseY = -1;
	
	public Panel_SkillTree(Gui_SkillOverview parent, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.parent = parent;
	}

	public void drawBackground(int mouseX, int mouseY, float partialTick) {
		//SCrolling
		if (Mouse.isButtonDown(0) && parent.selectedSkill == null){
			if(lastMouseX == -1) lastMouseX = mouseX;
			if(lastMouseY == -1) lastMouseY = mouseY;
			
            int moveX = -(lastMouseX - mouseX);
            int moveY = -(lastMouseY - mouseY);
			
            offsetX += offsetX + moveX < -10 * 24 ? 0 : offsetX + moveX > 10 * 24 ? 0 : moveX;
            offsetY += offsetY + moveY < -10 * 24 ? 0 : offsetY + moveY > 10 * 24 ? 0 : moveY;


            lastMouseX = mouseX;
            lastMouseY = mouseY;

        }else{
        	lastMouseX = -1;
            lastMouseY = -1;
        }
		
		GlStateManager.disableLighting();

		parent.mc.renderEngine.bindTexture(skillBG);
		ModularBackground.drawModularCenter(this, 0, 0, this.width, this.height, true);
		
        GlStateManager.disableLighting();
                
		for(Skill skill : parent.skills){
			for(Skill parent : skill.getParents()){
				
				Point p = new Point(skill.getPosX() * 24 + 12 + this.width / 2, skill.getPosY() * 24 + 12 + this.height / 2);
				Point p2 = new Point(parent.getPosX() * 24 + 12 + this.width / 2, parent.getPosY() * 24 + 12 + this.height / 2);
				this.drawLine((int) p.getX() + 12, (int) p.getY() + 12, (int) p2.getX() + 12, (int) p2.getY() + 12, COLOR_WHITE);
			}
			  
			parent.mc.renderEngine.bindTexture(skillBG);

			int posX = 24 * skill.getPosX() + this.width / 2 + offsetX;
			int posY = 24 * skill.getPosY() + this.height / 2  + offsetY;
			
			this.drawTexturedModalRect(posX, posY, 1, parent.unlockedSkills.contains(skill) ? 40 : 66,  24, 24);
			  		
			parent.getItemRenderer().renderItemIntoGUI(skill.getDisplayTexture(), posX + 4, posY + 4);
		}
	}
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
		for(Skill skill : parent.skills){
			int posX = 24 * skill.getPosX() + this.width / 2  + offsetX;
			int posY = 24 * skill.getPosY() + this.height / 2  + offsetY;

	        if((mouseX >= posX && mouseX <= posX + 24) && (mouseY >= posY && mouseY <= posY + 24)){
	        	parent.selectSkill(skill);
	        }
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
		return true;
    }

	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTick) {}
}
