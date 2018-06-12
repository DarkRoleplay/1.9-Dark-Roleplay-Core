package net.dark_roleplay.core.api.old.modules.work_in_progress.premium;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.xml.dtd.ELEMENTS;

public class Attachment_Premium extends ModelBase{

	protected ModelRenderer headAttachment;
	protected ModelRenderer bodyAttachment;
	protected ModelRenderer leftArmAttachment;
	protected ModelRenderer rightArmAttachment;
	protected ModelRenderer leftLegAttachment;
	protected ModelRenderer rightLegAttachment;
	
	public Attachment_Premium() {}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        GlStateManager.pushMatrix();

        if (this.isChild){
            float f = 2.0F;
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            if(headAttachment != null)
            	this.headAttachment.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            if(this.bodyAttachment != null)
            	this.bodyAttachment.render(scale);
            if(this.leftArmAttachment != null)
            	this.leftArmAttachment.render(scale);
            if(this.rightArmAttachment != null)
            	this.rightArmAttachment.render(scale);
            if(this.leftLegAttachment != null)
            	this.leftLegAttachment.render(scale);
            if(this.rightLegAttachment != null)
            	this.rightLegAttachment.render(scale);
        }else{
            if (entity.isSneaking()){
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            if(headAttachment != null)
            	this.headAttachment.render(scale);
            if(this.bodyAttachment != null)
            	this.bodyAttachment.render(scale);
            if(this.leftArmAttachment != null)
            	this.leftArmAttachment.render(scale);
            if(this.rightArmAttachment != null)
            	this.rightArmAttachment.render(scale);
            if(this.leftLegAttachment != null)
            	this.leftLegAttachment.render(scale);
            if(this.rightLegAttachment != null)
            	this.rightLegAttachment.render(scale);
        }

        GlStateManager.popMatrix();    
	}
	
	private ModelPlayer mainModel;

	public void setRotationAngles(ModelPlayer mainModel){
        if(headAttachment != null)
        	copyModelAngles(mainModel.bipedHead, this.headAttachment);
        if(bodyAttachment != null)
        	copyModelAngles(mainModel.bipedBody, this.bodyAttachment);
        if(rightArmAttachment != null)
        	copyModelAngles(mainModel.bipedRightArm, this.rightArmAttachment);
        if(rightLegAttachment != null)
        	copyModelAngles(mainModel.bipedRightLeg, this.rightLegAttachment);
        if(leftArmAttachment != null)
        	copyModelAngles(mainModel.bipedLeftArm, this.leftArmAttachment);
        if(leftLegAttachment != null)
        	copyModelAngles(mainModel.bipedLeftLeg, this.leftLegAttachment);
	}
	
	public void addHeadBox(int u, int v, float offX, float offY, float offZ, int x, int y, int z){
		this.headAttachment.setTextureOffset(u, v);
        this.headAttachment.addBox(offX - 8, -(offY+y), offZ - 8, x, y, z);
	}
	
	public Attachment_Premium loadFromJson(JsonObject json){
		JsonElement textureSize = json.get("texture_size");
		JsonElement head = json.get("head");
		
		if(head != null){
			this.headAttachment = new ModelRenderer(this, 0, 0);
			JsonArray parts = head.getAsJsonArray();
			for(int i = 0; i < parts.size(); i++){
				JsonObject element = parts.get(i).getAsJsonObject();
				JsonArray position = element.get("position").getAsJsonArray();
				JsonArray size = element.get("size").getAsJsonArray();
				JsonArray texture = element.get("texture").getAsJsonArray();

				this.addHeadBox(texture.get(0).getAsInt(), texture.get(1).getAsInt(), position.get(0).getAsFloat(), position.get(1).getAsFloat(), position.get(2).getAsFloat(), size.get(0).getAsInt(), size.get(1).getAsInt(), size.get(2).getAsInt());
			}
		}
		
		if(textureSize != null){
			if(this.headAttachment != null){
				JsonArray size = textureSize.getAsJsonArray();
				this.headAttachment.setTextureSize(size.get(0).getAsInt(), size.get(1).getAsInt());
			}
		}
		return this;
	}
}
