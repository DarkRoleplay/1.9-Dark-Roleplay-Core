package net.dark_roleplay.core.client.resources;

import java.util.function.Function;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class AdvancedModel implements IModel {

	
	public AdvancedModel(JsonObject obj) {
//		this.location = location;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		//TOIMPLEMENT
		return null;
	}

//	private BakedQuad createBakedQuadForFace(float centreLR, float width, float centreUD, float height,
//			float forwardDisplacement, int itemRenderLayer, TextureAtlasSprite texture, EnumFacing face) {
//		float x1, x2, x3, x4;
//		float y1, y2, y3, y4;
//		float z1, z2, z3, z4;
//		int packednormal;
//		final float CUBE_MIN = 0.0F;
//		final float CUBE_MAX = 1.0F;
//
//		switch (face) {
//		case UP: {
//			x1 = x2 = centreLR + width / 2.0F;
//			x3 = x4 = centreLR - width / 2.0F;
//			z1 = z4 = centreUD + height / 2.0F;
//			z2 = z3 = centreUD - height / 2.0F;
//			y1 = y2 = y3 = y4 = CUBE_MAX + forwardDisplacement;
//			break;
//		}
//		case DOWN: {
//			x1 = x2 = centreLR + width / 2.0F;
//			x3 = x4 = centreLR - width / 2.0F;
//			z1 = z4 = centreUD - height / 2.0F;
//			z2 = z3 = centreUD + height / 2.0F;
//			y1 = y2 = y3 = y4 = CUBE_MIN - forwardDisplacement;
//			break;
//		}
//		case WEST: {
//			z1 = z2 = centreLR + width / 2.0F;
//			z3 = z4 = centreLR - width / 2.0F;
//			y1 = y4 = centreUD - height / 2.0F;
//			y2 = y3 = centreUD + height / 2.0F;
//			x1 = x2 = x3 = x4 = CUBE_MIN - forwardDisplacement;
//			break;
//		}
//		case EAST: {
//			z1 = z2 = centreLR - width / 2.0F;
//			z3 = z4 = centreLR + width / 2.0F;
//			y1 = y4 = centreUD - height / 2.0F;
//			y2 = y3 = centreUD + height / 2.0F;
//			x1 = x2 = x3 = x4 = CUBE_MAX + forwardDisplacement;
//			break;
//		}
//		case NORTH: {
//			x1 = x2 = centreLR - width / 2.0F;
//			x3 = x4 = centreLR + width / 2.0F;
//			y1 = y4 = centreUD - height / 2.0F;
//			y2 = y3 = centreUD + height / 2.0F;
//			z1 = z2 = z3 = z4 = CUBE_MIN - forwardDisplacement;
//			break;
//		}
//		case SOUTH: {
//			x1 = x2 = centreLR + width / 2.0F;
//			x3 = x4 = centreLR - width / 2.0F;
//			y1 = y4 = centreUD - height / 2.0F;
//			y2 = y3 = centreUD + height / 2.0F;
//			z1 = z2 = z3 = z4 = CUBE_MAX + forwardDisplacement;
//			break;
//		}
//		default: {
//			assert false : "Unexpected facing in createBakedQuadForFace:" + face;
//			return null;
//		}
//		}
//
//		packednormal = calculatePackedNormal(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
//		return new BakedQuad(
//				Ints.concat(vertexToInts(x1, y1, z1, Color.WHITE.getRGB(), texture, 16, 16, packednormal),
//						vertexToInts(x2, y2, z2, Color.WHITE.getRGB(), texture, 16, 0, packednormal),
//						vertexToInts(x3, y3, z3, Color.WHITE.getRGB(), texture, 0, 0, packednormal),
//						vertexToInts(x4, y4, z4, Color.WHITE.getRGB(), texture, 0, 16, packednormal)),
//				itemRenderLayer, face, texture, true, net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM);
//	}

	private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v, int normal) {
		return new int[] {
			Float.floatToRawIntBits(x), Float.floatToRawIntBits(y), Float.floatToRawIntBits(z), color, Float.floatToRawIntBits(texture.getInterpolatedU(u)), Float.floatToRawIntBits(texture.getInterpolatedV(v)), normal 
		};
	}
}
