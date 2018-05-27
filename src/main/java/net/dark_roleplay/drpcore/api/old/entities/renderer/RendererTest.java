package net.dark_roleplay.drpcore.api.old.entities.renderer;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.models.entity.Bone;
import net.dark_roleplay.drpcore.api.old.models.entity.animation.AnimationState;
import net.dark_roleplay.drpcore.api.old.models.entity.serialization.Json_Animation;
import net.dark_roleplay.drpcore.api.old.models.entity.serialization.Json_Bones;
import net.dark_roleplay.drpcore.api.old.models.entity.serialization.Json_Models;
import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.testing.Testing_Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class RendererTest extends Render<Testing_Entity> {

	ResourceLocation loc = new ResourceLocation(References.MODID + ":textures/testing/wheel.png");
	
	Bone[] bones;
	Bone[] fullBoneList;
	
	ResourceLocation bonesFile, modelFile, animationsFile;
	
	AnimationState animationState;
	
	public RendererTest(RenderManager renderManager, ResourceLocation registryName) {
		super(renderManager);
		
		String modid = registryName.getResourceDomain();
		String entityID = registryName.getResourcePath();
		

		this.bonesFile = new ResourceLocation(modid, "entities/" + entityID + "/bones.json");
		this.modelFile = new ResourceLocation(modid, "entities/" + entityID + "/model.json");
		this.animationsFile = new ResourceLocation(modid, "entities/" + entityID + "/animationstate.json");

		this.bones = Json_Bones.readBonesFromJson(this.bonesFile);
		List<Bone> fullBoneList = new ArrayList<Bone>();
		for(Bone bone : bones) {
			fullBoneList.add(bone);
			addChildBones(bone, fullBoneList);
		}
		this.fullBoneList = fullBoneList.toArray(new Bone[fullBoneList.size()]);
		
		Json_Models.readModelFromJson(this.modelFile, this.bones);
		this.animationState = Json_Animation.readStateFromJson(this.animationsFile, this.fullBoneList);
	}
	
	private List<Bone> addChildBones(Bone bone, List<Bone> bones){
		Bone[] subBones = bone.getChildren();
		for(Bone subBone : subBones) {
			addChildBones(subBone, bones);
			bones.add(subBone);
		}
		return bones;
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
        
        this.animationState.progressAnimation(partialTicks);
        for(Bone bone : bones){
        	bone.render(bufferbuilder, 1F, this.animationState);
        }
        
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
		GlStateManager.popMatrix();
		
		if (!this.renderOutlines) {
			this.renderName(entity, x, y, z);
		}
	}

}
