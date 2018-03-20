package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.models;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class MeshModel implements IModel {

	
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return null;
	}
	
	public class BakedMeshModel implements IBakedModel{

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
//			BakedQuad
			return null;
		}

		@Override
		public boolean isAmbientOcclusion() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isGui3d() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isBuiltInRenderer() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ItemOverrideList getOverrides() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
