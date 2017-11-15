package net.dark_roleplay.drpcore.api.entities.renderer;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.models.TexturedQuad;
import net.dark_roleplay.drpcore.api.models.entity.Bone;
import net.dark_roleplay.drpcore.api.models.entity.Json_Bones;
import net.dark_roleplay.drpcore.api.models.entity.Json_Models;
import net.dark_roleplay.drpcore.api.models.entity.Model;
import net.dark_roleplay.drpcore.api.models.entity.ModelCube;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.objects.entities.util.sitting.Sittable;
import net.dark_roleplay.drpcore.testing.Testing_Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RendererTest extends Render<Testing_Entity> {

	ResourceLocation loc = new ResourceLocation(DRPCoreInfo.MODID + ":textures/testing/wheel.png");
	
	Bone[] bones;
	
	ResourceLocation bonesFile, modelFile, animationsFile;
	
	public RendererTest(RenderManager renderManager, ResourceLocation bones, ResourceLocation model, ResourceLocation animationsFolder) {
		super(renderManager);
		
		this.bones = Json_Bones.readBonesFromJson(bones);
		Json_Models.readModelFromJson(model, this.bones);
		this.bonesFile = bones;
		this.modelFile = model;
		this.animationsFile = animationsFolder;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Testing_Entity entity) {
		return null;
	}

	public void doRender(Testing_Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
//		this.bones = Json_Bones.readBonesFromJson(bonesFile);
//		Json_Models.readModelFromJson(modelFile, this.bones);
//		this.bones = Json_Bones.readBonesFromJson(new ResourceLocation(DRPCoreInfo.MODID + ":textures/testing/testing_bones.json"));
//		Json_Models.readModelFromJson(new ResourceLocation(DRPCoreInfo.MODID + ":textures/testing/testing_model.json"), this.bones);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("drpcore:entities/test/steve.png"));//this.loc
		
		GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        
        GlStateManager.translate(x, y, z);
		GlStateManager.scale(0.0625, 0.0625, 0.0625);
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        for(Bone bone : bones){
        	bone.render(bufferbuilder, 1F);
        }
        
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
		GlStateManager.popMatrix();
		
		if (!this.renderOutlines) {
			this.renderName(entity, x, y, z);
		}
	}

}
