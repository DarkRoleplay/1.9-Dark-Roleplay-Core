package net.dark_roleplay.core.testing.skills;

import java.util.Set;

import net.dark_roleplay.core.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class SkillInfo extends GuiScreen{

	private static ResourceLocation bg = new ResourceLocation(References.MODID,"textures/guis/skill_info.png");

	private Skill skill;
	private Set<SkillPoint> requiredSkillPoints;
	
	public SkillInfo(Skill skill) {
		this.skill = skill;
		this.requiredSkillPoints = skill.getRequiredSkillPointsToUnlock();
	}
	
	@Override
	public void initGui(){
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(bg);
		int left = (this.width) / 2 - 100;
		int top = (this.height) / 2 - 60;
		this.drawTexturedModalRect(left, top, 0, 0, 200, 121);
		
		if(mouseX > left + 182 && mouseX < left + 194 && mouseY > top + 4 && mouseY < top + 24) {
			this.drawTexturedModalRect(left + 182, top + 4, 232, 0, 12, 21);
		}else {
			this.drawTexturedModalRect(left + 182, top + 4, 244, 0, 12, 21);
		}
		if(requiredSkillPoints != null) {
			System.out.println(requiredSkillPoints);
			if(requiredSkillPoints.size() == 1) {
				this.drawTexturedModalRect(left + 7, top + 91, 0, 121, 178, 22);
			}else {
				this.drawTexturedModalRect(left + 7, top + 91, 0, 143, 89, 22);
				this.drawTexturedModalRect(left + 96, top + 91, 0, 143, 89, 22);
			}
		}
		
		this.fontRenderer.drawStringWithShadow(this.skill.getRegistryName().toString(), left + 28, top + 11, 0xFFFFFFFF);

		this.fontRenderer.drawStringWithShadow(this.skill.getRegistryName().toString() + " description", left + 8, top + 29, 0xFFFFFFFF);
		
		this.fontRenderer.drawStringWithShadow("Required Skill Points: ", left + 6, top + 80, 0xFFFFFFFF);
	}
}

















