package net.dark_roleplay.drpcore.client.gui.skills2;

import java.awt.Color;
import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Button;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Icon;
import net.dark_roleplay.drpcore.api.gui.advanced.IGuiElement;
import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.client.gui.advanced.wrappers.Variable_Object;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class Button_Skill extends Gui_Button{

	private static ResourceLocation background_texture = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/skill/skill_background.png");

	private Variable_Object<Skill> var;
	private Skill skill;
	private Gui_Icon icon;
	
	public Button_Skill(Variable_Object<Skill> var, Skill skill, int posX, int posY) {
		super(posX, posY, 28, 28);
		this.var = var;
		this.skill = skill;
		this.icon = this.skill.getIcon();
		this.icon.setPos(this.posX + 2, this.posY + 2);
		this.icon.setSize(this.width - 4, this.height - 4);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		super.draw(mouseX,mouseY, partialTicks);
		this.icon.draw(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.var.set(this.skill);
		return true;
	}
	
	@Override
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.icon.setPos(this.posX + 2, this.posY + 2);
	}

}
