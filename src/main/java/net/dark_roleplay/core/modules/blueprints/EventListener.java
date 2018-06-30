package net.dark_roleplay.core.modules.blueprints;

import net.dark_roleplay.core.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = References.MODID)
public class EventListener {

	@SubscribeEvent
	public static void renderWorld(RenderWorldLastEvent event){
		if(BlueprintPreview.isActivated()) {
				Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	
				EntityPlayer player = Minecraft.getMinecraft().player;
				
				double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.getPartialTicks();
				double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.getPartialTicks();
				double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.getPartialTicks();
				
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
				BlockModelRenderer renderer = blockrendererdispatcher.getBlockModelRenderer();
				
				BlockPos pos = BlueprintPreview.getPos();
				
				GlStateManager.pushMatrix();
				GlStateManager.translate(pos.getX() - playerX, pos.getY() - playerY, pos.getZ() - playerZ);
				
				//Custom Tesselator without reseting the ByteBuffer as it's not changing between majority of codes, need to reuse bytebuffer though.
				BlueprintPreview.getBuffer().draw();
				
				GlStateManager.popMatrix();
//			}
		}
	}
}
