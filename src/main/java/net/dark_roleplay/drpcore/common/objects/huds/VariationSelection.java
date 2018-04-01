package net.dark_roleplay.drpcore.common.objects.huds;

import java.util.Calendar;

import net.dark_roleplay.drpcore.api.old.modules.hud.Hud;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class VariationSelection  extends Hud{
	
	public VariationSelection(ResourceLocation registryName) {
		super(registryName);
	}

	private static ResourceLocation BG = new ResourceLocation(References.MODID, "textures/hud/variant_selection.png");
	
	public void render(int width, int height, float partialTicks){
		Minecraft.getMinecraft().renderEngine.bindTexture(BG);
		int baseX = this.alignment.getBaseX(width, 28) + this.posX + 16;
		int baseY = this.alignment.getBaseY(height, 28) + this.posY + 16;
		this.drawScaledCustomSizeModalRect(baseX, baseY, 0, 0, 28, 28, 28, 28, 28, 28);
	}

}