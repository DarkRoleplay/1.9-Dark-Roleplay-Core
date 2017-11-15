package net.dark_roleplay.drpcore.client.modules.model_editor;

import net.dark_roleplay.drpcore.api.models.entity.Bone;
import net.dark_roleplay.drpcore.api.models.entity.Json_Bones;
import net.dark_roleplay.drpcore.api.models.entity.Json_Models;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui_EntityEdit extends GuiScreen {

	Bone[] bones;
	
	public Gui_EntityEdit(){
		this.bones = Json_Bones.readBonesFromJson(new ResourceLocation(DRPCoreInfo.MODID, "entities/test/bones.json"));
		Json_Models.readModelFromJson(new ResourceLocation(DRPCoreInfo.MODID, "entities/test/model.json"), this.bones);
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("drpcore:entities/test/steve.png"));//this.loc
		this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		GlStateManager.pushMatrix();

		
		GlStateManager.translate(this.width/2, this.height/2 + 24, 20);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		GlStateManager.rotate(5F, 1F, 0F, 0F);
		GlStateManager.rotate(-45F, 0F, 1F, 0F);
		GlStateManager.scale(3, 3, 3);

//		GlStateManager.scale(0.5, 0.5, 0.5);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.disableCull();
		for(Bone bone : bones){
        	bone.render(bufferbuilder, 1F);
//        	System.out.println("bone");
        }

        GlStateManager.enableCull();
		
		GlStateManager.popMatrix();

		for (int i = 0; i < this.buttonList.size(); ++i) {
			((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}

		for (int j = 0; j < this.labelList.size(); ++j) {
			((GuiLabel) this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
		}
	}

}
