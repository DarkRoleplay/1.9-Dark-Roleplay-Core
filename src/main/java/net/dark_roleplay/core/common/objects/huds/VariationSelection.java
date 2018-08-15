package net.dark_roleplay.core.common.objects.huds;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.modules.hud.Hud;
import net.dark_roleplay.library_old.items.ItemVariantBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class VariationSelection  extends Hud{
	
	public VariationSelection(ResourceLocation registryName) {
		super(registryName);
		this.posX = 90;
		this.posY =-16;
		this.alignment = ALIGNMENT.BOTTOM;
	}

	private static ResourceLocation BG = new ResourceLocation(References.MODID, "textures/hud/variant_selection.png");
	
	public void render(int width, int height, float partialTicks){
		Minecraft mc = Minecraft.getMinecraft();
		
		if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemVariantBlock))
			return;
		
		mc.renderEngine.bindTexture(BG);
		int baseX = this.alignment.getBaseX(width, 28) + this.posX + 16;
		int baseY = this.alignment.getBaseY(height, 28) + this.posY + 16;
		Gui.drawScaledCustomSizeModalRect(baseX, baseY, 0, 0, 28, 28, 28, 28, 28, 28);
	}

}