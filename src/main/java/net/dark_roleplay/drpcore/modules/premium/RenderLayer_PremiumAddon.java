package net.dark_roleplay.drpcore.modules.premium;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.modules.premium.Module_Premium.Equiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderLayer_PremiumAddon implements LayerRenderer<EntityPlayer> {

	RenderPlayer renderer;
	private ModelPlayer mainModel;
	private Attachment_Premium pa;
	
	
	public RenderLayer_PremiumAddon(RenderPlayer renderer){
		this.renderer = renderer;
		this.mainModel = renderer.getMainModel();
//		pa = new Attachment_Cylinder();
		pa.isChild = false;
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(EntityPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Module_Premium.Equiped equiped = Module_Premium.getEquiped(entity);
		if(equiped != null){
			pa = equiped.getEquiped().getAttachment();
			
			pa.setModelAttributes(this.mainModel);
	
	        this.renderer.bindTexture(new ResourceLocation(DRPCoreReferences.MODID, "textures/premium/full_black.png"));
	        pa.setRotationAngles(mainModel);
			pa.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}
}
