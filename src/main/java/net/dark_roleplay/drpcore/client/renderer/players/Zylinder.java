package net.dark_roleplay.drpcore.client.renderer.players;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class Zylinder extends PremiumAddon{

	public Zylinder(ResourceLocation texture){
		super(texture);
		this.headAttachment = new ModelRenderer(this, 0, 0);
		this.headAttachment.setTextureSize(64, 32);
		this.headAttachment.setTextureOffset(0, 0);
        this.headAttachment.addBox(-3.5F, -16.0F, -3.5F, 7, 7, 7);
		this.headAttachment.setTextureOffset(0, 14);
        this.headAttachment.addBox(-5.0F, -9.0F, -5.0F, 10, 1, 10);
        this.headAttachment.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
}
