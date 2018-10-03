package net.dark_roleplay.core.testing.item_exporter;

import java.util.List;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core_modules.guis.api.components.DRPGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemExporterGui extends DRPGuiScreen {

	List<Item> items;

	boolean hasSaved = false;

	public ItemExporterGui() {
		IForgeRegistry<Item> reg = GameRegistry.findRegistry(Item.class);
		this.items = reg.getValues();
	}

	@Override
    public void updateScreen(){
		if(!this.hasSaved) {
			try {
	//			ScreenShotHelper.saveScreenshot(References.FOLDER_RECIPES, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
				ImageHelper.saveScreenshot(0, 0, 50, 50, References.FOLDER_RECIPES, null, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
			}catch(Exception e) {
				System.out.println(e);
			}
		this.hasSaved = true;
		}
    }


	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTicks) {

		Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, this.items.get(0).getDefaultInstance(), 0, 0);


//		if(simpleImage == null) return;
//
//		Minecraft.getMinecraft().getTextureManager().bindTexture(simpleImage.getResource());
//		GlStateManager.color(1f, 1f, 1f, 1.0f);
//		GlStateManager.enableBlend();
//		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 64, 128, 64, 128);
	}

}
