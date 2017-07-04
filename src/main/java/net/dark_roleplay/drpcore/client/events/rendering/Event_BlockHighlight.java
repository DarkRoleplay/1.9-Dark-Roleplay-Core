package net.dark_roleplay.drpcore.client.events.rendering;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_BlockHighlight {

	/**
	 * Block Preview
	 */
	@SubscribeEvent
	public void highlightGhostBlock(DrawBlockHighlightEvent event){
		if(!DRPCoreConfigs.ENABLE_PLACEMENT_PREVIEW || event.getTarget().getBlockPos() == null || event.getPlayer().getEntityWorld().isAirBlock(event.getTarget().getBlockPos()))
			return;
		
		EntityPlayer player = event.getPlayer();
		if(!player.isSneaking())
			return;

		ItemStack stack = player.getHeldItemMainhand();
		
		if (!(stack.getItem() instanceof ItemBlock))
			return;
		
		BlockPos position = event.getTarget().getBlockPos().offset(event.getTarget().sideHit);

		IBlockState stateToRender = ((ItemBlock)stack.getItem()).getBlock().getStateForPlacement(event.getPlayer().getEntityWorld(), position, event.getTarget().sideHit, (float)event.getTarget().hitVec.x, (float)event.getTarget().hitVec.y,  (float)event.getTarget().hitVec.z, event.getPlayer().getHeldItemMainhand().getMetadata(), event.getPlayer(), EnumHand.MAIN_HAND);
		if(stateToRender == null)
			return;
	
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	           
	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder vertexbuffer = tessellator.getBuffer();
	    vertexbuffer.begin(7, DefaultVertexFormats.BLOCK);
	            
	    double playerX = event.getPlayer().prevPosX + (event.getPlayer().posX - event.getPlayer().prevPosX) * event.getPartialTicks();
	    double playerY = event.getPlayer().prevPosY + (event.getPlayer().posY - event.getPlayer().prevPosY) * event.getPartialTicks();
	    double playerZ = event.getPlayer().prevPosZ + (event.getPlayer().posZ - event.getPlayer().prevPosZ) * event.getPartialTicks();
	            
	    GL11.glTranslated(-playerX, -playerY, -playerZ);
	           
	    BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
	    BlockModelRenderer renderer = blockrendererdispatcher.getBlockModelRenderer();
	
	    renderer.renderModel(event.getPlayer().getEntityWorld(), blockrendererdispatcher.getModelForState(stateToRender), stateToRender, position, vertexbuffer, false,0l/* MathHelper.getPositionRandom(position)*/);
	
	    tessellator.draw();
	    GlStateManager.disableAlpha();
	    GlStateManager.popMatrix();
	}
	
}
