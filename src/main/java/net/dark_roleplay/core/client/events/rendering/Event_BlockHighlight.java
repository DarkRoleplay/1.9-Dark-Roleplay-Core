package net.dark_roleplay.core.client.events.rendering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.config.Client;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MODID)
public class Event_BlockHighlight {

	private static Map<IBlockState, IBakedModel> cache = new HashMap<>();
	
	private static Set<Block> erroringBlocks = new HashSet<Block>();
	
	private static Item item;
	
	/*
	 * Block Preview
	 */
	@SubscribeEvent
	public static void highlightGhostBlock(DrawBlockHighlightEvent event){
		if (!Client.BUILDING.PLACEMENT_PREVIEW || !event.getPlayer().isSneaking() || event.getTarget().getBlockPos() == null || event.getPlayer().getEntityWorld().isAirBlock(event.getTarget().getBlockPos()))
			return;

		EntityPlayer player = event.getPlayer();

		ItemStack stack = player.getHeldItemMainhand();
		if (stack.isEmpty())
			return;

		Block block = stack.getItem() instanceof ItemBlock ? ((ItemBlock) stack.getItem()).getBlock() : null;
		if (block == null || erroringBlocks.contains(block))
			return;
		
		if(item != stack.getItem()){
			cache = new HashMap<>();
			item = stack.getItem();
		}

		BlockPos position = event.getTarget().getBlockPos();
		World world = player.getEntityWorld();
		RayTraceResult target = event.getTarget();

		if(!world.getBlockState(position).getBlock().isReplaceable(world, position)){
			position = position.offset(target.sideHit);
		}
		
		IBlockState state = block.canPlaceBlockOnSide(world, position, target.sideHit) ? block.getStateForPlacement(world, position, target.sideHit,(float) target.hitVec.x,(float) target.hitVec.y, (float) target.hitVec.z, player.getHeldItem(EnumHand.MAIN_HAND).getMetadata(), player, EnumHand.MAIN_HAND) : null;
		if(state != null)
			state = block.getActualState(state, world, position);
		
		if(state == null || block.getRenderType(state) != EnumBlockRenderType.MODEL)
			return;
		
//		position = player.getEntityWorld().getBlockState(position).getBlock().isReplaceable(world, position) ? position : event.getTarget().getBlockPos().offset(event.getTarget().sideHit);

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.getPartialTicks();
		double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.getPartialTicks();
		double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.getPartialTicks();

		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockModelRenderer renderer = blockrendererdispatcher.getBlockModelRenderer();

		GlStateManager.pushMatrix();

        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.translate(-playerX, -playerY, -playerZ);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(7, DefaultVertexFormats.BLOCK);
		
		try {
			if(!cache.containsKey(state))
				cache.put(state, blockrendererdispatcher.getModelForState(state));		
			renderer.renderModel(event.getPlayer().getEntityWorld(), cache.get(state), state, position, buffer, true, MathHelper.getPositionRandom(position));
		}catch(Exception e) {
			erroringBlocks.add(block);
			return;
		}finally {
			tessellator.draw();
			
		    GlStateManager.disableBlend();
			GlStateManager.disableAlpha();
			
			GlStateManager.popMatrix();
		}
	}
	
}
