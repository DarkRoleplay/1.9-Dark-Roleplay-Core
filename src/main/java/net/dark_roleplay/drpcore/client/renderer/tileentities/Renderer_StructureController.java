package net.dark_roleplay.drpcore.client.renderer.tileentities;

import net.dark_roleplay.drpcore.common.config.Client;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Renderer_StructureController extends TileEntitySpecialRenderer<TE_BlueprintController> {
	public void render(TE_BlueprintController te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (Minecraft.getMinecraft().player.canUseCommandBlock() || Minecraft.getMinecraft().player.isSpectator()) {
			super.render(te, x, y, z, partialTicks, destroyStage, alpha);
			BlockPos blockpos = te.getOffset();
			BlockPos blockpos1 = te.getSize();

			if (blockpos1.getX() >= 1 && blockpos1.getY() >= 1 && blockpos1.getZ() >= 1) {
				if (te.getMode() == TE_BlueprintController.Mode.SAVE || te.getMode() == TE_BlueprintController.Mode.LOAD) {
					double d0 = 0.01D;
					double d1 = (double) blockpos.getX();
					double d2 = (double) blockpos.getZ();
					double d6 = y + (double) blockpos.getY() - 0.01D;
					double d9 = d6 + (double) blockpos1.getY() + 0.02D;
					double d3;
					double d4;
						
					d3 = (double) blockpos1.getX() + 0.02D;
						d4 = (double) blockpos1.getZ() + 0.02D;

					double d5;
					double d7;
					double d8;
					double d10;

					d5 = x + (d3 < 0.0D ? d1 + 1.0D + 0.01D : d1 - 0.01D);
					d7 = z + (d4 < 0.0D ? d2 + 1.0D + 0.01D : d2 - 0.01D);
					d8 = d5 + d3;
					d10 = d7 + d4;

					int i = 255;
					int j = 223;
					int k = 127;
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					GlStateManager.disableFog();
					GlStateManager.disableLighting();
					GlStateManager.disableTexture2D();
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					this.setLightmapDisabled(true);

					if (te.getRenderMode() == TE_BlueprintController.RenderMode.BOUNDING_BOX) {
						this.renderBox(tessellator, bufferbuilder, d5, d6, d7, d8, d9, d10, 255, 223, 0);
					}else if(te.getRenderMode() == TE_BlueprintController.RenderMode.INVISIBLE){
						this.renderBox(tessellator, bufferbuilder, d5, d6, d7, d8, d9, d10, 255, 223, 0);
						if(Client.BUILDING.BLUEPRINTS.HIGHLIGHT_INVISIBLE_BLOCKS)
							this.renderInvisibleBlocks(te, x, y, z, blockpos, tessellator, bufferbuilder, true);
						this.renderInvisibleBlocks(te, x, y, z, blockpos, tessellator, bufferbuilder, false);
					}
					this.setLightmapDisabled(false);
					GlStateManager.glLineWidth(1.0F);
					GlStateManager.enableLighting();
					GlStateManager.enableTexture2D();
					GlStateManager.enableDepth();
					GlStateManager.depthMask(true);
					GlStateManager.enableFog();
				}
			}
		}
	}

	private void renderInvisibleBlocks(TE_BlueprintController te, double sizeX, double sizeY, double sizeZ, BlockPos pos, Tessellator tes, BufferBuilder bufBuilder, boolean inside) {
		GlStateManager.glLineWidth(inside ? 3.0F : 1.0F);
		bufBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		World world = te.getWorld();
		BlockPos blockpos = te.getPos();
		BlockPos blockpos1 = blockpos.add(pos);

		for (BlockPos blockpos2 : BlockPos.getAllInBox(blockpos1, blockpos1.add(te.getSize()).add(-1, -1, -1))) {
			IBlockState iblockstate = world.getBlockState(blockpos2);
			boolean flag = iblockstate == Blocks.AIR.getDefaultState();
			boolean flag1 = iblockstate == Blocks.STRUCTURE_VOID.getDefaultState();

			if (flag || flag1) {
				float f = flag ? 0.05F : 0.0F;
				double d0 = (double) ((float) (blockpos2.getX() - blockpos.getX()) + 0.45F) + sizeX - (double) f;
				double d1 = (double) ((float) (blockpos2.getY() - blockpos.getY()) + 0.45F) + sizeY - (double) f;
				double d2 = (double) ((float) (blockpos2.getZ() - blockpos.getZ()) + 0.45F) + sizeZ - (double) f;
				double d3 = (double) ((float) (blockpos2.getX() - blockpos.getX()) + 0.55F) + sizeX + (double) f;
				double d4 = (double) ((float) (blockpos2.getY() - blockpos.getY()) + 0.55F) + sizeY + (double) f;
				double d5 = (double) ((float) (blockpos2.getZ() - blockpos.getZ()) + 0.55F) + sizeZ + (double) f;

				if (inside) {
					RenderGlobal.drawBoundingBox(bufBuilder, d0, d1, d2, d3, d4, d5, 0.0F, 0.0F, 0.0F, 1.0F);
				} else if (flag) {
					RenderGlobal.drawBoundingBox(bufBuilder, d0, d1, d2, d3, d4, d5, 
							Client.BUILDING.BLUEPRINTS.INVISIBLE_BLOCKS_COLOR.RED, Client.BUILDING.BLUEPRINTS.INVISIBLE_BLOCKS_COLOR.GREEN, 
							Client.BUILDING.BLUEPRINTS.INVISIBLE_BLOCKS_COLOR.BLUE, Client.BUILDING.BLUEPRINTS.INVISIBLE_BLOCKS_COLOR.ALPHA);
				} else {
					RenderGlobal.drawBoundingBox(bufBuilder, d0, d1, d2, d3, d4, d5, 1.0F, 0.25F, 0.25F, 1.0F);
				}
			}
		}

		tes.draw();
	}

	private void renderBox(Tessellator tes, BufferBuilder bufBuilder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int alpha, int bright, int dark) {
		GlStateManager.glLineWidth(2.0F);
		bufBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufBuilder.pos(minX, minY, minZ).color((float) bright, (float) bright, (float) bright, 0.0F).endVertex();
		bufBuilder.pos(minX, minY, minZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, minY, minZ).color(bright, dark, dark, alpha).endVertex();
		bufBuilder.pos(maxX, minY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, minY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, minY, minZ).color(dark, dark, bright, alpha).endVertex();
		bufBuilder.pos(minX, maxY, minZ).color(dark, bright, dark, alpha).endVertex();
		bufBuilder.pos(maxX, maxY, minZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, maxY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, maxY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, maxY, minZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, maxY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(minX, minY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, minY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, maxY, maxZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, maxY, minZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, minY, minZ).color(bright, bright, bright, alpha).endVertex();
		bufBuilder.pos(maxX, minY, minZ).color((float) bright, (float) bright, (float) bright, 0.0F).endVertex();
		tes.draw();
		GlStateManager.glLineWidth(1.0F);
	}

	public boolean isGlobalRenderer(TileEntityStructure te) {
		return true;
	}
}
