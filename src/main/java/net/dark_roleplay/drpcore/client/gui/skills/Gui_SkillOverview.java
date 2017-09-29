package net.dark_roleplay.drpcore.client.gui.skills;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Panel;
import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.api.skills.SkillPointData;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.skills.Packet_UnlockSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Gui_SkillOverview extends DRPGuiScreen {
//
	private static final ResourceLocation skillBG = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill/skill_point_overview.png");
//	
//	private Panel_SkillTree skillTreePanel;
//	private Panel_SkillSelect skillSelectPanel;
//	
//	public List<SkillTree> skillTrees;
//	public List<Skill> skills;
//	
//	private int skillTree = 0;
//	private ItemStack treePreview;
//	private String localizedTree;
//	
//	public List<Skill> unlockedSkills;
//	private ISkillController skillCap;
//	
//	public Skill selectedSkill = null;
//	
	public Gui_SkillOverview() {
		super(skillBG, 100, 100);
	}
//
//	@Override
//	public void initGui() {
//		if(this.skillTreePanel == null){
//			this.skillTreePanel = new Panel_SkillTree(this, 7, 32, this.width - 14, this.height - 40);
//			this.skillSelectPanel = new Panel_SkillSelect(this);
//
////			skillTrees = SkillRegistry.getSkillTrees();
//			skillCap =  (ISkillController) Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
//		}else{
//			skillTreePanel.setSize(this.width - 14, this.height - 40);
//		}
//		
//		updateSkills();
//		super.initGui();
//	}
//	
//	@Override
//	protected void reAdjust() {
//		this.guiLeft = ((this.width - this.bgWidth) / 2) + this.bgOffsetX;
//		this.guiTop = ((this.height - this.bgHeight) / 2) + this.bgOffsetY;
//		skillTreePanel.setSize(this.width - 14, this.height - 40);
//	}
//	
//	@Override
//	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
//		ModularGui_Drawer.drawBackground(1, 26, this.width - 2, this.height - 28, false, true);
//		skillTreePanel.draw(mouseX - skillTreePanel.getPosX(), mouseY - skillTreePanel.getPosY(), partialTicks);
//		
//		this.mc.renderEngine.bindTexture(skillBG);
//		if(this.selectedSkill != null){
//			ModularGui_Drawer.drawBackground(skillSelectPanel.getPosX() - 6, skillSelectPanel.getPosY() - 6, skillSelectPanel.getWidth() + 12, skillSelectPanel.getHeight() + 12, false, true);
//			skillSelectPanel.draw(mouseX - skillSelectPanel.getPosX(), mouseY - skillSelectPanel.getPosY(), partialTicks);
//		}
//		
//		ModularGui_Drawer.drawBackground(1, 1, this.width / 2 - 2, 24);
//		ModularGui_Drawer.drawBackground(this.width / 2 + 1, 1, 16, 24);
//		ModularGui_Drawer.drawBackground(this.width / 2 + 18, 1, this.width / 2 - 36, 24);
//		ModularGui_Drawer.drawBackground(this.width - 17, 1, 16, 24);
//		
//		this.itemRender.renderItemIntoGUI(treePreview, this.width / 2 + 24, 5);
//		this.fontRenderer.drawStringWithShadow(localizedTree, this.width / 2 + 42, 9, COLOR_WHITE);
//	}
//	
//	
//	@Override
//	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
//        if(this.selectedSkill == null && (mouseX >= skillTreePanel.getPosX() && mouseX <= skillTreePanel.getPosX() + skillTreePanel.getWidth()) && (mouseY >= skillTreePanel.getPosY() && mouseY <= skillTreePanel.getPosY() + skillTreePanel.getHeight())){
//        	skillTreePanel.mouseClicked(mouseX - skillTreePanel.getPosX(), mouseY - skillTreePanel.getPosY(), mouseButton);
//        }else if(this.selectedSkill != null  && (mouseX >= skillSelectPanel.getPosX() && mouseX <= skillSelectPanel.getPosX() + skillSelectPanel.getWidth()) && (mouseY >= skillSelectPanel.getPosY() && mouseY <= skillSelectPanel.getPosY() + skillSelectPanel.getHeight())){
//        	skillSelectPanel.mouseClicked(mouseX - skillSelectPanel.getPosX(), mouseY - skillSelectPanel.getPosY(), mouseButton);
//        }else if(mouseX > this.width / 2 + 1 && mouseX < this.width / 2 + 17 && mouseY > 1 && mouseY < 25){
//        	this.skillTree++;
//        	updateSkills();
//        }else if(mouseX > this.width - 17 && mouseX < this.width - 1 && mouseY > 1 && mouseY < 25){
//        	this.skillTree++;
//        	updateSkills();
//        }
//    }
//	
//	@Override
//	protected void keyTyped(char typedChar, int keyCode) throws IOException {
//        if (keyCode == 1){
//        	if(this.selectedSkill == null){
//	            this.mc.displayGuiScreen((GuiScreen)null);
//	
//	            if (this.mc.currentScreen == null){
//	                this.mc.setIngameFocus();
//	            }
//        	}else{
//        		this.selectSkill(null);
//        	}
//        }
//    }
//	
//	public void selectSkill(Skill skill) {
//		this.selectedSkill = skill;
//		this.skillSelectPanel.setSkill(skill);
//	}
//	
//	public RenderItem getItemRenderer(){
//		return this.itemRender;
//	}
//	
//	public FontRenderer getFontRenderer(){
//		return this.fontRenderer;
//	}
//	
//	private void updateSkills(){
//		if(this.skillTree >= skillTrees.size())
//			this.skillTree = 0;
//		if(this.skillTree < 0)
//			this.skillTree = skillTrees.size()-1;
//		
//		this.skills = skillTrees.get(skillTree).getSkills();
//		this.unlockedSkills = new ArrayList<Skill>();
//		for(Skill skill : skills){
//			if(skillCap.hasSkill(skill))
//				unlockedSkills.add(skill);
//		}
//		this.treePreview = this.skillTrees.get(skillTree).getDisplayTexture();
//		this.localizedTree = I18n.format(this.skillTrees.get(skillTree).getUnlocalizedName());
//	}
//	
//	public boolean tryUnlock(Skill skill){
//		if(skill != null){
//			if(!this.skillCap.hasSkill(this.selectedSkill)){
//				SkillRequirements req = this.selectedSkill.getRequirements();
//				for(SkillPoint point : req.getRequiredPoints()){
//					SkillPointData data = this.skillCap.getSkillPointData(point);
//					if(data == null || data.getAmount() < req.getRequiredAmount(point)){
//						return false;
//					}
//				}
//				for(Skill parent : this.selectedSkill.getParents()){
//					if(!this.skillCap.hasSkill(parent)){
//						return false;
//					}
//				}
//				DRPCorePackets.sendToServer(new Packet_UnlockSkill(this.selectedSkill.getRegistryName()));
//				for(SkillPoint point : req.getRequiredPoints()){
//					this.skillCap.consumeSkillPoint(point, req.getRequiredAmount(point));
//				}
//				this.skillCap.unlockSkill(this.selectedSkill);
//				this.selectedSkill.unlock(Minecraft.getMinecraft().player);
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public boolean isUnlocked(Skill skill){
//		return this.skillCap.hasSkill(skill);
//	}
//	
//	public boolean hasPoints(SkillPoint point, int amount){
//		return this.skillCap.getSkillPointData(point).getAmount() >= amount;
//	}
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}
}
