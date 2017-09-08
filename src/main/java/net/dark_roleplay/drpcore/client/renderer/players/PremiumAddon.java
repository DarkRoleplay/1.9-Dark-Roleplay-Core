package net.dark_roleplay.drpcore.client.renderer.players;

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

public class PremiumAddon extends ModelBase{

	protected ModelRenderer headAttachment;
	protected ModelRenderer bodyAttachment;
	protected ModelRenderer leftArmAttachment;
	protected ModelRenderer rightArmAttachment;
	protected ModelRenderer leftLegAttachment;
	protected ModelRenderer rightLegAttachment;

	private ResourceLocation texture;
	
	public PremiumAddon(ResourceLocation texture) {
		this.texture = texture;
	}

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

	public ResourceLocation getTexture() {
		return texture;
	}

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
}
