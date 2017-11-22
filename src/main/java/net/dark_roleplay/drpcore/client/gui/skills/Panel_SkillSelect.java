package net.dark_roleplay.drpcore.client.gui.skills;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Panel;
import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillPoint;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Panel_SkillSelect extends Gui_Panel{

	private static final ResourceLocation skillBG = new ResourceLocation(DRPCoreReferences.MODID, "textures/guis/skill/skill_point_overview.png");
	
	private int offsetX, offsetY;
	private Gui_SkillOverview parent;
	private Skill skill;
	private Button_UnlockSkill unlockBtn;
	
	protected static final int COLOR_WHITE = new Color(255,255,255).getRGB();
	protected static final int COLOR_DARK_GRAY = new Color(55,55,55).getRGB();
	protected static final int COLOR_RED = new Color(255,50,50).getRGB();
	
	private String translatedName;
	private String translatedDesc;
	
	public Panel_SkillSelect(Gui_SkillOverview parent) {
		super((parent.width - 200) / 2, (parent.height - 150 ) / 2, 200, 150);
		this.parent = parent;
		this.addChild(unlockBtn = new Button_UnlockSkill(parent, 185, 126));
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float partialTick) {
		GlStateManager.disableLighting();

		ModularGui_Drawer.drawBackgroundCenter(0, 0, this.width, this.height, true);
		parent.mc.renderEngine.bindTexture(skillBG);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTick) {
//		if(this.skill != null){
//	        GlStateManager.disableLighting();
//
//			parent.getItemRenderer().renderItemIntoGUI(skill.getDisplayTexture(), 5, 5);
//			parent.getFontRenderer().drawStringWithShadow(translatedName, 25, 7, COLOR_WHITE);
//			parent.getFontRenderer().drawSplitString(translatedDesc, 6, 26, this.width - 10, COLOR_DARK_GRAY);
//			parent.getFontRenderer().drawSplitString(translatedDesc, 5, 25, this.width - 10, COLOR_WHITE);
//			
//			SkillRequirements sq = this.skill.getRequirements();
//			ItemStack stack;
//			int xOffset = 0;
//			boolean hasPoints;
//			parent.getFontRenderer().drawStringWithShadow(I18n.format("gui.skill_select.required"), 5, 112, COLOR_WHITE);
//			for(SkillPoint point : sq.getRequiredPoints()){
//				int amount = sq.getRequiredAmount(point);
//				hasPoints = parent.hasPoints(point, amount);
//				parent.getItemRenderer().renderItemIntoGUI(point.getDisplayStack(), 5 + xOffset, 129);
//                GlStateManager.disableDepth();
//                parent.getFontRenderer().drawStringWithShadow(String.valueOf(amount), (float)(5 + xOffset + 17 - parent.getFontRenderer().getStringWidth(String.valueOf(amount))), (float)(129 + 9), hasPoints ? COLOR_WHITE : COLOR_RED);
//                GlStateManager.color(1f, 1f, 1f);
//                GlStateManager.enableDepth();
//				xOffset += 22;
//			}
//		}
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
		if(skill != null){
			translatedName = I18n.format(skill.getUnlocalizedName());
//			translatedDesc = I18n.format(skill.getUnlocalizedDesc());
		}
	}

	public Skill getSkill() {
		return this.skill;
	}
	
}