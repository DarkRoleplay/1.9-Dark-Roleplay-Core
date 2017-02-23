package net.dark_roleplay.drpcore.client.items.models;

import com.google.common.primitives.Ints;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SmithableFinal implements IPerspectiveAwareModel {

	public SmithableFinal(IBakedModel i_parentModel, int i_numberOfChessPieces) {
		parentModel = i_parentModel;
		numberOfChessPieces = i_numberOfChessPieces;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (side != null) {
			return parentModel.getQuads(state, side, rand);
		}

		List<BakedQuad> combinedQuadsList = new ArrayList(parentModel.getQuads(state, side, rand));
		combinedQuadsList.addAll(getChessPiecesQuads(numberOfChessPieces));
		return combinedQuadsList;
		// FaceBakery.makeBakedQuad() can also be useful for generating quads
	}

	@Override
	public boolean isAmbientOcclusion() {return parentModel.isAmbientOcclusion();}

	@Override
	public boolean isGui3d() {return parentModel.isGui3d();}

	@Override
	public boolean isBuiltInRenderer() {return parentModel.isBuiltInRenderer();}

	@Override
	public TextureAtlasSprite getParticleTexture() {return parentModel.getParticleTexture();}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {return parentModel.getItemCameraTransforms();}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (parentModel instanceof IPerspectiveAwareModel) {
			Matrix4f matrix4f = ((IPerspectiveAwareModel) parentModel).handlePerspective(cameraTransformType).getRight();
			return Pair.of(this, matrix4f);
		} else {
			ItemCameraTransforms itemCameraTransforms = parentModel.getItemCameraTransforms();
			ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
			TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
			Matrix4f mat = null;
			if (tr != null) { // && tr != TRSRTransformation.identity()) {
				mat = tr.getMatrix();
			}
			return Pair.of(this, mat);
		}
	}

	@Override
	public ItemOverrideList getOverrides() {throw new UnsupportedOperationException("The finalised model does not have an override list.");}

	private List<BakedQuad> getChessPiecesQuads(int numberOfPieces) {
		final float PIXEL_WIDTH = 0.0625F;

		//Length   16
		//Widht    8
		float[][] smithed = new float[16][8];
		
		for(int i = 0; i < smithed.length; i++){//Length
			for(int j = 0; j < smithed[i].length; j++){//Width
				smithed[i][j] = ((float)i)/16F - (((float)i/16F) * j) / 16 + 0.5F;
			}
		}
		
		TextureAtlasSprite swordTexture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/diamond_block");
		// if you want to use your own texture, you can add it to the texture
		// map using code similar to this in your ClientProxy:
		// MinecraftForge.EVENT_BUS.register(new StitcherAddDigitsTexture());
		// public class StitcherAddDigitsTexture {
		// @SubscribeEvent
		// public void stitcherEventPre(TextureStitchEvent.Pre event) {
		// ResourceLocation digits = new
		// ResourceLocation("stickmod:items/digits");
		// event.map.registerSprite(digits);
		// }
		// }

		List<BakedQuad> returnList = new ArrayList<BakedQuad>(64);

		int row = 0;
		int col = 0;
		for(int i = 0; i < smithed.length; i++){//Length
			for(int j = 0; j < smithed[i].length; j++){//Width
				
				List<BakedQuad> cube = createCube(0.25F + (1F/16F) * j, (1F/16F) * i, 0.5F - smithed[i][j] / 2, 0.25F + (1F/16F) * j + 0.0625F, (1F/16F) * i  + 0.0625F, 0.5F + smithed[i][j] / 2, swordTexture);
				for(int k = 0; k < cube.size(); k++){
					returnList.add(cube.get(k));
				}
		
//				float pointWidth = (smithed[i][j]/2 - 0.5F);
//				BakedQuad piece = createBakedQuadForFace(j * (PIXEL_WIDTH) + 0.28125F, i * (PIXEL_WIDTH) + 0.03125F, pointWidth + 0.25F, swordTexture, EnumFacing.NORTH);
//				returnList.add(piece);
//				
//				piece = createBakedQuadForFace((j * (PIXEL_WIDTH) + 0.28125F), 0.0625F, ((15 - i) * (PIXEL_WIDTH) + 0.03125F), 0.0625F, -pointWidth - 0.25F, 0, swordTexture, EnumFacing.SOUTH);
//				returnList.add(piece);
//				
//				piece = createBakedQuadForFace(0.5F, pointWidth*2, -(i * 0.0625F) + 1 - 0.03125F, 0.0625F,-( 0.25F + ((j + 1) * 0.0625F)), 0, swordTexture, EnumFacing.EAST);
//				returnList.add(piece);
			}
		}
		return returnList;
	}

	private List<BakedQuad> createCube(float x1, float y1, float z1, float x2, float y2, float z2 , TextureAtlasSprite texture){
		List<BakedQuad> cube = new ArrayList<BakedQuad>();
		
		float length, width, height;
		length = x2 > x1 ? x2 - x1 : x1 - x2;
		width = z2 > z1 ? z2 - z1 : z1 - z2;
		height = y2 > y1 ? y2 - y1 : y1 - y2;
		
		cube.add(new BakedQuad(Ints.concat( //NORTH
				vertexToInts(x1, y1, z1 + width, Color.WHITE.getRGB(), texture, 16, 16),
				vertexToInts(x1 + length, y1, z1 + width, Color.WHITE.getRGB(), texture, 16, 0),
				vertexToInts(x1 + length, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 0),
				vertexToInts(x1, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 16)),
				0, EnumFacing.NORTH, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
				);
		
		cube.add(new BakedQuad(Ints.concat( //SOUTH
				vertexToInts(x1 + length, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 0),
				vertexToInts(x1, y1 + height, z1, Color.WHITE.getRGB(), texture, 0, 0),
				vertexToInts(x1 + length, y1 + height, z1, Color.WHITE.getRGB(), texture, 0, 16)),
				0, EnumFacing.SOUTH, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
				);
		
		cube.add(new BakedQuad(Ints.concat(
				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
				vertexToInts(x1, y1, z1 + width, Color.WHITE.getRGB(), texture, 16, 0),
				vertexToInts(x1, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 0),
				vertexToInts(x1, y1 + height, z1, Color.WHITE.getRGB(), texture, 0, 16)),
				0, EnumFacing.EAST, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
				);
		
		cube.add(new BakedQuad(Ints.concat(
				vertexToInts(x1 + length, y1, z1 + width, Color.WHITE.getRGB(), texture, 16, 16),
				vertexToInts(x1 + length, y1, z1, Color.WHITE.getRGB(), texture, 16, 0),
				vertexToInts(x1 + length, y1 + height, z1, Color.WHITE.getRGB(), texture, 0, 0),
				vertexToInts(x1 + length, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 16)),
				0, EnumFacing.WEST, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
				);
		
		cube.add(new BakedQuad(Ints.concat( //DOWN
			vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
			vertexToInts(x1 + length, y1, z1, Color.WHITE.getRGB(), texture, 16, 0),
			vertexToInts(x1 + length, y1, z1 + width, Color.WHITE.getRGB(), texture, 0, 0),
			vertexToInts(x1, y1, z1 + width, Color.WHITE.getRGB(), texture, 0, 16)),
			0, EnumFacing.DOWN, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
			);
		
		cube.add(new BakedQuad(Ints.concat( //UP
				vertexToInts(x1 + length, y1 + height, z1, Color.WHITE.getRGB(), texture, 16, 16),
				vertexToInts(x1, y1 + height, z1, Color.WHITE.getRGB(), texture, 16, 0),
				vertexToInts(x1, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 0),
				vertexToInts(x1 + length, y1 + height, z1 + width, Color.WHITE.getRGB(), texture, 0, 16)),
				0, EnumFacing.UP, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
				);
		
//		cube.add(new BakedQuad(Ints.concat(
//				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
//				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 0),
//				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 0, 0),
//				vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 0, 16)),
//				0, EnumFacing.NORTH, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM)
//				);
		
		
		return cube;
	}
	
	private BakedQuad createBakedQuadForFace(float centreLR, float centreUD,float forwardDisplacement, TextureAtlasSprite texture, EnumFacing face){
		return createBakedQuadForFace(centreLR, 0.0625F, centreUD, 0.0625F, forwardDisplacement, 0, texture, face);
	}

	
	private BakedQuad createBakedQuadForFace(float centreLR, float width, float centreUD, float height,float forwardDisplacement, int itemRenderLayer, TextureAtlasSprite texture, EnumFacing face) {
		float x1, x2, x3, x4;
		float y1, y2, y3, y4;
		float z1, z2, z3, z4;
		final float CUBE_MIN = 0.0F;
		final float CUBE_MAX = 1.0F;

		switch (face) {
		case UP: {
			x1 = x2 = centreLR + width / 2.0F;
			x3 = x4 = centreLR - width / 2.0F;
			z1 = z4 = centreUD + height / 2.0F;
			z2 = z3 = centreUD - height / 2.0F;
			y1 = y2 = y3 = y4 = CUBE_MAX + forwardDisplacement;
			break;
		}
		case DOWN: {
			x1 = x2 = centreLR + width / 2.0F;
			x3 = x4 = centreLR - width / 2.0F;
			z1 = z4 = centreUD - height / 2.0F;
			z2 = z3 = centreUD + height / 2.0F;
			y1 = y2 = y3 = y4 = CUBE_MIN - forwardDisplacement;
			break;
		}
		case WEST: {
			z1 = z2 = centreLR + width / 2.0F;
			z3 = z4 = centreLR - width / 2.0F;
			y1 = y4 = centreUD - height / 2.0F;
			y2 = y3 = centreUD + height / 2.0F;
			x1 = x2 = x3 = x4 = CUBE_MIN - forwardDisplacement;
			break;
		}
		case EAST: {
			z1 = z2 = centreLR - width / 2.0F;
			z3 = z4 = centreLR + width / 2.0F;
			y1 = y4 = centreUD - height / 2.0F;
			y2 = y3 = centreUD + height / 2.0F;
			x1 = x2 = x3 = x4 = CUBE_MAX + forwardDisplacement;
			break;
		}
		case NORTH: {
			x1 = x2 = centreLR - width / 2.0F;
			x3 = x4 = centreLR + width / 2.0F;
			y1 = y4 = centreUD - height / 2.0F;
			y2 = y3 = centreUD + height / 2.0F;
			z1 = z2 = z3 = z4 = CUBE_MIN - forwardDisplacement;
			break;
		}
		case SOUTH: {
			x1 = x2 = centreLR + width / 2.0F;
			x3 = x4 = centreLR - width / 2.0F;
			y1 = y4 = centreUD - height / 2.0F;
			y2 = y3 = centreUD + height / 2.0F;
			z1 = z2 = z3 = z4 = CUBE_MAX + forwardDisplacement;
			break;
		}
		default: {
			assert false : "Unexpected facing in createBakedQuadForFace:" + face;
			return null;
		}
		}

		return new BakedQuad(
				Ints.concat(vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16),
						vertexToInts(x2, y2, z2, Color.WHITE.getRGB(), texture, 16, 0),
						vertexToInts(x3, y3, z3, Color.WHITE.getRGB(), texture, 0, 0),
						vertexToInts(x4, y4, z4, Color.WHITE.getRGB(), texture, 0, 16)),
				itemRenderLayer, face, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM);
	}

	/**
	 * Converts the vertex information to the int array format expected by
	 * BakedQuads.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param color
	 *            RGBA colour format - white for no effect, non-white to tint
	 *            the face with the specified colour
	 * @param texture
	 *            the texture to use for the face
	 * @param u
	 *            u-coordinate of the texture (0 - 16) corresponding to [x,y,z]
	 * @param v
	 *            v-coordinate of the texture (0 - 16) corresponding to [x,y,z]
	 * @return
	 */
	private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v) {
		return new int[] { Float.floatToRawIntBits(x), Float.floatToRawIntBits(y), Float.floatToRawIntBits(z), color,
				Float.floatToRawIntBits(texture.getInterpolatedU(u)),
				Float.floatToRawIntBits(texture.getInterpolatedV(v)), 0 };
	}

	private int numberOfChessPieces;
	private IBakedModel parentModel;
}