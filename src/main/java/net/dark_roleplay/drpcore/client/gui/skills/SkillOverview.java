package net.dark_roleplay.drpcore.client.gui.skills;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.jna.platform.win32.WinUser.POINT;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.api.gui.utility.ModularBackground;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillRequirements;
import net.dark_roleplay.drpcore.api.skills.SkillTree;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.Packet_InitSimpleRecipe;
import net.dark_roleplay.drpcore.common.network.packets.skills.Packet_UnlockSkill;
import net.dark_roleplay.drpcore.common.skills.SkillPointData;
import net.dark_roleplay.drpcore.common.skills.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class SkillOverview extends DRPGuiScreen {
	
	private static final ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill/skill_point_overview.png");

	private List<SkillTree> skillTrees;
	
	private List<Skill> skills;
	private HashMap<Skill, Boolean> drawnSkills = Maps.newHashMap();
	
	private Skill hoveredSkill;
	private Skill selectedSkill;
	
	private int skillTree = 0;
	
	private int gridWidth = 0, gridHeight = 0;
	
	private int pixelXOffset = 0,  pixelYOffset = 0;
	
	private int lastMouseX = -1, lastMouseY = -1;
	
	private int skillTop = 7, skillLeft = 7;
	private int displayWidth = 0, displayHeight = 0;
	
	private final int minPosX = -24, minPosY = -24, maxPosX = 24, maxPosY = 24;
	
//	private Set<SkillPoint> availableSkills;
	private List<SkillPointData> skillPoints;
	
	private ISkillController skillCap;
	
	private Button_UnlockSkill unlockSkill;
	private Button_Change skillTreePrev;
	private Button_Change skillTreeNext;

	private Button_Change skillPointPrev;
	private Button_Change skillPointNext;

	
	public SkillOverview() {
		super(bg, 1000, 500);
		skillTrees = SkillRegistry.getSkillTrees();
		skills = skillTrees.get(skillTree).getSkills();
		for(int i = 0; i < skills.size(); i++){
			drawnSkills.put(skills.get(i), false);
		}
		
		skillCap =  (ISkillController) Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
		skillPoints = skillCap.getSkillPoints();

	}
	
	@Override
	public void initGui() {
		super.initGui();
		short buttonID = 0;
		int framePosX = 120 + 7;
		int framePosY = this.height - 101 + 7;

		int i = 0;
		
		unlockSkill = new Button_UnlockSkill(i++, framePosX + this.width - 268, framePosY + 63);
		this.buttonList.add(unlockSkill);
		
		framePosX = 125;
		framePosY = 6;
		
		skillTreePrev = new Button_Change(i++, framePosX + 2, framePosY + this.height - 127, false);
		skillTreeNext = new Button_Change(i++, this.width - 18, framePosY + this.height - 127, true);
		this.buttonList.add(skillTreePrev);
		this.buttonList.add(skillTreeNext);
		
		framePosX = 7;
		framePosY = 7;
		
		skillPointPrev = new Button_Change(i++, framePosX + 1, framePosY + this.height - 26, false);
		skillPointNext = new Button_Change(i++, framePosX + 94, framePosY + this.height - 26, true);
		this.buttonList.add(skillPointPrev);
		this.buttonList.add(skillPointNext);
		
	}
	
	protected void drawBackground(int mouseX, int mouseY, float partialTicks) {
		skillPoints = skillCap.getSkillPoints();

		if(this.selectedSkill == null){
			unlockSkill.enabled = false;
		}else{
			unlockSkill.enabled = true;
		}
		
		this.displayWidth = this.width - 262;
		this.displayHeight = this.height - 26;
		
		if (Mouse.isButtonDown(0)){
			if(lastMouseX == -1) lastMouseX = mouseX;
			if(lastMouseY == -1) lastMouseY = mouseY;
			
            int moveX = -(lastMouseX - mouseX);
            int moveY = -(lastMouseY - mouseY);
			
            pixelXOffset += pixelXOffset + moveX < minPosX * 24 ? 0 : pixelXOffset + moveX > maxPosX * 24 ? 0 : moveX;
            pixelYOffset += pixelYOffset + moveY < minPosY * 24 ? 0 : pixelYOffset + moveY > maxPosY * 24 ? 0 : moveY;


            lastMouseX = mouseX;
            lastMouseY = mouseY;

        }else{
        	lastMouseX = -1;
            lastMouseY = -1;
        }
		
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(this.bgTexture);
		
		//LEFT Field
		ModularBackground.drawModular(this, 1, 1, 117, this.height - 2, 6, 22, 6, 22, 28, 28);
		
		//CENTER TOP Field
		ModularBackground.drawModular(this, 120             , 1                , this.width - 121, this.height - 104, 6, 22, 6, 22, 28, 28);
		
		//CENTER BOTTOM Field
		ModularBackground.drawModular(this, 120             , this.height - 101, this.width - 240, 100              , 6, 22, 6, 22, 28, 28);
		
		//RIGHT BOTTOM Field
		ModularBackground.drawModular(this, this.width - 118, this.height - 101, 117             , 100              , 6, 22, 6, 22, 28, 28);
		
		//233
//		for(int i = 0; i < 9; i++){
//			this.drawTexturedModalRect(7, 7 + i * 27, 0, 229, 105,27);
//		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks) {
		int framePosX = 0;
		int framePosY = 0;
		
		gridWidth = this.width - 131;
		gridHeight = this.height - 114;
		
		//Skills
		
		/** SKILL TREE WINDOW**/
		framePosX = 125;
		framePosY = 6;
		
		for(int i = 0; i < skills.size(); i++){
			Skill skill = skills.get(i);
			Point p = calcPos(skill.getPosX(), skill.getPosY());
			if(p.getX() > framePosX && p.getX() + 24 < framePosX + gridWidth && p.getY() > framePosY && p.getY() + 24 < framePosY + gridHeight){
				this.drawnSkills.put(skill, true);
			}else{
				this.drawnSkills.put(skill, false);
			}		
		}
		
		for(Skill skill : skills){
			if(this.drawnSkills.get(skill)){
				for(Skill parent : skill.getParents()){
					if(this.drawnSkills.get(parent)){
						Point p = calcPos(skill.getPosX(), skill.getPosY());
						Point p2 = calcPos(parent.getPosX(), parent.getPosY());
						this.drawLine((int) p.getX() + 12, (int) p.getY() + 12, (int) p2.getX() + 12, (int) p2.getY() + 12, COLOR_WHITE);
					}
				}
			}
		}
		
		//Skill Overview and info
		this.hoveredSkill = null;

		for(Skill skill : skills){
			if(this.drawnSkills.get(skill)){
				Point p = calcPos(skill.getPosX(), skill.getPosY());
				
				drawSkill((int) p.getX(), (int) p.getY(), skill);
				//Skill Overview and info
				if(isMouseBetween(mouseX, mouseY, (int) p.getX(), (int) p.getY(), (int) p.getX() + 24, (int) p.getY() + 24)){
					this.hoveredSkill = skill;
				}
			}
		}
		/** END SKILL WINDOW**/
		
		/** SKILL INFO **/

		framePosX = 120 + 7;
		framePosY = this.height - 101 + 7;
		
		this.mc.renderEngine.bindTexture(this.bgTexture);

		if(this.hoveredSkill != null){
			
			String localizedName = I18n.translateToLocal(this.hoveredSkill.getUnlocalizedName());
			String localizedDesc = I18n.translateToLocal(this.hoveredSkill.getUnlocalizedDesc());
			SkillRequirements points = this.hoveredSkill.getRequirements();
			int lineHeight = this.fontRenderer.getWordWrappedHeight(localizedDesc, 101) / 8;

			//Desc
			this.itemRender.renderItemIntoGUI(hoveredSkill.getDisplayTexture(), framePosX + 2, framePosY + 2);
			
			this.fontRenderer.drawStringWithShadow(localizedName, framePosX + 25, framePosY + 5, COLOR_WHITE);
			
			this.fontRenderer.drawSplitString(localizedDesc, framePosX + 3, framePosY + 26, this.width - 248, (COLOR_WHITE & 16579836) >> 2);

			this.fontRenderer.drawSplitString(localizedDesc, framePosX + 2, framePosY + 25, this.width - 248, COLOR_WHITE);

			int i = 0;

			for(SkillPoint point : points.getRequiredPoints()){
				short pointX = (short) (this.width - 112);
				short pointY = (short) (this.height -95 + (i * 22));
				
				GlStateManager.color(255, 255, 255);;
				
				this.mc.renderEngine.bindTexture(this.bgTexture);
				this.drawTexturedModalRect(pointX, pointY, 0, 234,  105, 22);
				this.itemRender.renderItemIntoGUI(point.getDisplayStack(), pointX + 3, pointY + 3);
				this.fontRenderer.drawString(I18n.translateToLocal(point.getUnlocalizedName()), pointX + 21, pointY + 2, COLOR_DARK_GRAY);
				this.fontRenderer.drawString(I18n.translateToLocal("drpcore.skill.required") + ": " + points.getRequiredAmount(point), pointX + 21, pointY + 11, COLOR_DARK_GRAY);
				i++;
			}
		}
		
		if(this.selectedSkill != null && this.hoveredSkill == null){
			
			String localizedName = I18n.translateToLocal(this.selectedSkill.getUnlocalizedName());
			String localizedDesc = I18n.translateToLocal(this.selectedSkill.getUnlocalizedDesc());
			SkillRequirements points = this.selectedSkill.getRequirements();
			int lineHeight = this.fontRenderer.getWordWrappedHeight(localizedDesc, 101) / 8;

			//Desc
			this.itemRender.renderItemIntoGUI(selectedSkill.getDisplayTexture(), framePosX + 2, framePosY + 2);
			
			this.fontRenderer.drawStringWithShadow(localizedName, framePosX + 25, framePosY + 5, COLOR_WHITE);
			
			this.fontRenderer.drawSplitString(localizedDesc, framePosX + 3, framePosY + 26, this.width - 248, (COLOR_WHITE & 16579836) >> 2);

			this.fontRenderer.drawSplitString(localizedDesc, framePosX + 2, framePosY + 25, this.width - 248, COLOR_WHITE);

			int i = 0;

			for(SkillPoint point : points.getRequiredPoints()){
				short pointX = (short) (this.width - 112);
				short pointY = (short) (this.height -95 + (i * 22));
				
				GlStateManager.color(255, 255, 255);;
				
				this.mc.renderEngine.bindTexture(this.bgTexture);
				this.drawTexturedModalRect(pointX, pointY, 0, 234,  105, 22);
				this.itemRender.renderItemIntoGUI(point.getDisplayStack(), pointX + 3, pointY + 3);
				this.fontRenderer.drawString(I18n.translateToLocal(point.getUnlocalizedName()), pointX + 21, pointY + 2, COLOR_DARK_GRAY);
				this.fontRenderer.drawString(I18n.translateToLocal("drpcore.skill.required") + ": " + points.getRequiredAmount(point), pointX + 21, pointY + 11, COLOR_DARK_GRAY);
				i++;
			}
		}
		
		/** END SKILL INFO **/
		
		/** SKILL POINTS **/

		framePosX = 7;
		framePosY = 7;
		
		this.mc.renderEngine.bindTexture(this.bgTexture);
		List<SkillPoint> points = SkillRegistry.getSkillPoints();
		for(int i = 0; i < (this.height - 16) / 27 && i < skillPoints.size(); i++){
			SkillPointData data = skillPoints.get(i);
			GlStateManager.color(255, 255, 255);;
			this.mc.renderEngine.bindTexture(this.bgTexture);
			this.drawTexturedModalRect(framePosX, framePosY + (i * 27), 105, 229, 105, 27);

			this.itemRender.renderItemIntoGUI(data.getPoint().getDisplayStack(), framePosX + 3, framePosY + (i * 27) + 3);
 			this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, data.getPoint().getDisplayStack(), framePosX + 3, framePosY + (i * 27) + 3, String.valueOf(data.getAmount()));
            GlStateManager.disableLighting();
			this.fontRenderer.drawStringWithShadow(I18n.translateToLocal(data.getPoint().getUnlocalizedName()), framePosX + 21, framePosY + (i * 27) + 2, COLOR_WHITE);

			this.fontRenderer.drawStringWithShadow("LvL: " + data.getLevel(), framePosX + 21, framePosY + (i * 27) + 10, COLOR_WHITE);
			this.mc.renderEngine.bindTexture(this.bgTexture);
			this.drawTexturedModalRect(framePosX + 3, framePosY + (i * 27) + 21, 108, 226, (int)(99 * (((float) data.getXP())/ (data.getPoint().getRequiredXP(data.getLevel()) + 1f))), 3);
		}
		
		
		/** END SKILL POINTS **/
	}
	
	private void drawSkill(int posX, int posY, Skill skill){
		this.mc.renderEngine.bindTexture(this.bgTexture);
        GlStateManager.disableLighting();

		if(!this.skillCap.hasSkill(skill)){
			this.drawTexturedModalRect(posX, posY, 1, 40,  24, 24);
		}else{
			this.drawTexturedModalRect(posX, posY, 1, 66,  24, 24);
		}
		
		this.itemRender.renderItemIntoGUI(skill.getDisplayTexture(), posX + 4, posY + 4);
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		this.mc.renderEngine.bindTexture(this.bgTexture);
	}
	
	private Point calcPos(int posX, int posY){
		Point p = new Point();
		p.x = 114 + ((this.width - 134)  / 2) + (24 * posX) + this.pixelXOffset;
		p.y = -5 + ((this.height - 114) / 2) + (24 * posY) + this.pixelYOffset;
		return p;
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	for(Skill skill : skills){
        		if(drawnSkills.get(skill)){
        			Point p = calcPos(skill.getPosX(), skill.getPosY());
        			if(mouseX > p.getX() && mouseX < p.getX() + 24 && mouseY > p.getY() && mouseY < p.getY() +24){
        				this.selectedSkill = skill;
        			}
        		}
        	}
        	
            for (int i = 0; i < this.buttonList.size(); ++i){
                GuiButton guibutton = (GuiButton)this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)){
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                        break;
                    guibutton = event.getButton();
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.getButton(), this.buttonList));
                }
            }
        }
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		if (button.id == unlockSkill.id) {
			if(this.selectedSkill != null){
				if(!this.skillCap.hasSkill(this.selectedSkill)){
					SkillRequirements req = this.selectedSkill.getRequirements();
					for(SkillPoint point : req.getRequiredPoints()){
						SkillPointData data = this.skillCap.getSkillPointData(point);
						if(data == null || data.getAmount() < req.getRequiredAmount(point)){
							return;
						}
					}
					for(Skill parent : this.selectedSkill.getParents()){
						if(!this.skillCap.hasSkill(parent)){
							return;
						}
					}
					DRPCorePackets.sendToServer(new Packet_UnlockSkill(this.selectedSkill.getRegistryName()));
					for(SkillPoint point : req.getRequiredPoints()){
						this.skillCap.consumeSkillPoint(point, req.getRequiredAmount(point));
					}
					this.skillCap.unlockSkill(this.selectedSkill);
					this.selectedSkill.unlock(Minecraft.getMinecraft().player);
				}
			}
		}else if(button.id == skillTreePrev.id){
			pixelXOffset = 0;  pixelYOffset = 0;
			skillTree--;
			if(skillTree < 0){
				 skillTree = skillTrees.size();
			}
			skills = skillTrees.get(skillTree).getSkills();
		}else if(button.id == skillTreeNext.id){
			pixelXOffset = 0;  pixelYOffset = 0;
			skillTree++;
			if(skillTree >= skillTrees.size()){
				skillTree = 0;
			}
			skills = skillTrees.get(skillTree).getSkills();
		}
	}
}
