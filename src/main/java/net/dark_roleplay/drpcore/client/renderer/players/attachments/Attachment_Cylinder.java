package net.dark_roleplay.drpcore.client.renderer.players.attachments;

import net.dark_roleplay.drpcore.client.renderer.players.Attachment_Premium;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class Attachment_Cylinder extends Attachment_Premium{

	public Attachment_Cylinder(){
		this.headAttachment = new ModelRenderer(this, 0, 0);
		this.headAttachment.setTextureSize(64, 32);
        this.headAttachment.setRotationPoint(0.0F, 0.0F, 0.0F);
        
		this.headAttachment.setTextureOffset(0, 17);
        this.headAttachment.addBox(-6.0F, -9.0F, -6.0F, 12, 1, 12);
        
		this.headAttachment.setTextureOffset(0, 0);
        this.headAttachment.addBox(-4F, -18.0F, -4F, 8, 9, 8);
	}
	
	
	
}
