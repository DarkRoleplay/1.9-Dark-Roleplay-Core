package net.dark_roleplay.core.client.items.models;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class SmithableSword implements IBakedModel {

	  private SmithableSword_OverrideList swordOverrideList;
	
	  /**
	   * Create our model, using the given baked model as a base to add extra BakedQuads to.
	   * @param i_baseChessboardModel the base model
	   */
	  public SmithableSword(IBakedModel i_baseChessboardModel){
	    baseChessboardModel = i_baseChessboardModel;
	    swordOverrideList = new SmithableSword_OverrideList(Collections.EMPTY_LIST);
	  }

	  public static final ModelResourceLocation modelResourceLocation = new ModelResourceLocation("drpcore:medicine", "inventory");

	  @Override
	  public TextureAtlasSprite getParticleTexture() {
	    return baseChessboardModel.getParticleTexture();
	  }

	  /**  Returns the quads for the base chessboard model only
	   * @param state
	   * @param side
	   * @param rand
	   * @return
	   */
	  @Override
	  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
	    return baseChessboardModel.getQuads(state, side, rand);
	  }

	  // not needed for items, but hey
	  @Override
	  public boolean isAmbientOcclusion() {
	    return baseChessboardModel.isAmbientOcclusion();
	  }

	  @Override
	  public boolean isGui3d() {
	    return baseChessboardModel.isGui3d();
	  }

	  @Override
	  public boolean isBuiltInRenderer() {
	    return false;
	  }

	  @Override
	  public ItemCameraTransforms getItemCameraTransforms() {
	    return baseChessboardModel.getItemCameraTransforms();  // NB this is not enough for BakedItemModels, must do handlePerspective as well
	  }

	  @Override
	  public ItemOverrideList getOverrides() {
	    return swordOverrideList;
	  }

	  private IBakedModel baseChessboardModel;

//	  @Override
//	  public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
//	    if (baseChessboardModel instanceof IPerspectiveAwareModel) {
//	      Matrix4f matrix4f = ((IPerspectiveAwareModel)baseChessboardModel).handlePerspective(cameraTransformType).getRight();
//	      return Pair.of(this, matrix4f);
//	    } else {
//	      ItemCameraTransforms itemCameraTransforms = baseChessboardModel.getItemCameraTransforms();
//	      ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
//	      TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
//	      Matrix4f mat = null;
//	      if (tr != null) {
//	        mat = tr.getMatrix();
//	      }
//	      return Pair.of(this, mat);
//	    }
//	  }
	}