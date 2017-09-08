package net.dark_roleplay.drpcore.client.renderer.players;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerPremium implements LayerRenderer<EntityPlayer> {

	RenderPlayer renderer;
	private ModelPlayer mainModel;
	private PremiumAddon pa;
	
	
	public RenderPlayerPremium(RenderPlayer renderer){
		this.renderer = renderer;
		this.mainModel = renderer.getMainModel();
		pa = new Zylinder(new ResourceLocation(DRPCoreInfo.MODID, "textures/premium/test.png"));
		pa.isChild = false;
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(EntityPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		pa.setModelAttributes(this.mainModel);

        this.renderer.bindTexture(this.pa.getTexture());
        pa.setRotationAngles(mainModel);
		pa.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}
