package net.dark_roleplay.drpcore.api.old.models.entity;

import net.dark_roleplay.drpcore.api.old.models.TexturedQuad;
import net.minecraft.client.model.PositionTextureVertex;

public class ModelCube extends Model{

	public ModelCube(float xMin, float yMin, float zMin, float dX, float dY, float dZ, int uMin, int vMin, float textureWidth, float textureHeight) {
		super(6);
		
		float xMax = xMin + dX, yMax = yMin + dY, zMax = zMin + dZ;
		
		TexturedQuad[] quads = new TexturedQuad[6];
		
		PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(xMin, yMin, zMin, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex0 = new PositionTextureVertex(xMax, yMin, zMin, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(xMax, yMax, zMin, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(xMin, yMax, zMin, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(xMin, yMin, zMax, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(xMax, yMin, zMax, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(xMax, yMax, zMax, 0.0F, 0.0F);
		PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(xMin, yMax, zMax, 0.0F, 0.0F);
		
		float ceilX = (float) Math.ceil(dX), ceilY = (float) Math.ceil(dY), ceilZ = (float) Math.ceil(dZ);
		
		float baseX = 0.0F, baseY = 0.0F;
		
		//Left
		baseX = ceilZ + ceilX + uMin;
		baseY = ceilZ + vMin;
		quads[0] = new TexturedQuad(new PositionTextureVertex[] {
				positiontexturevertex4.setTexturePosition(baseX, baseY + dY), 
				positiontexturevertex0.setTexturePosition(baseX + dZ, baseY + dY), 
				positiontexturevertex1.setTexturePosition(baseX + dZ, baseY), 
				positiontexturevertex5.setTexturePosition(baseX, baseY) },textureWidth, textureHeight);
		
		//Right
		baseX = uMin;
		baseY = ceilZ + vMin;
		quads[1] = new TexturedQuad(new PositionTextureVertex[] {
				positiontexturevertex7.setTexturePosition(baseX, baseY + dY),
				positiontexturevertex3.setTexturePosition(baseX + dZ, baseY + dY),
				positiontexturevertex6.setTexturePosition(baseX + dZ, baseY),
				positiontexturevertex2.setTexturePosition(baseX, baseY) }, textureWidth, textureHeight);
		
		//Bottom
		baseX = ceilZ + ceilX + uMin;
		baseY = vMin;
		quads[2] = new TexturedQuad(new PositionTextureVertex[] { 
				positiontexturevertex4.setTexturePosition(baseX + dX, baseY + dZ), 
				positiontexturevertex3.setTexturePosition(baseX, baseY + dZ), 
				positiontexturevertex7.setTexturePosition(baseX, baseY), 
				positiontexturevertex0.setTexturePosition(baseX + dX, baseY) }, textureWidth, textureHeight);
		
		//Top
		baseX = ceilZ + uMin;
		baseY = vMin;
		quads[3] = new TexturedQuad(new PositionTextureVertex[] { 
				positiontexturevertex1.setTexturePosition(baseX + dX, baseY), 
				positiontexturevertex2.setTexturePosition(baseX , baseY), 
				positiontexturevertex6.setTexturePosition(baseX, baseY + dZ), 
				positiontexturevertex5.setTexturePosition(baseX + dX , baseY + dZ) }, textureWidth, textureHeight);
		
		//Back
		baseX = ceilZ + ceilX + ceilZ + uMin;
		baseY = ceilZ + vMin;
		quads[4] = new TexturedQuad(new PositionTextureVertex[] { 
				positiontexturevertex0.setTexturePosition(baseX , baseY + dY),
				positiontexturevertex7.setTexturePosition(baseX + dX, baseY + dY), 
				positiontexturevertex2.setTexturePosition(baseX + dX, baseY)
				, positiontexturevertex1.setTexturePosition(baseX, baseY) }, textureWidth, textureHeight);
		
		//Front
		baseX = ceilZ + uMin;
		baseY = ceilZ + vMin;
		quads[5] = new TexturedQuad(new PositionTextureVertex[] {
				positiontexturevertex3.setTexturePosition(baseX, baseY + dY), 
				positiontexturevertex4.setTexturePosition(baseX + dX, baseY + dY), 
				positiontexturevertex5.setTexturePosition(baseX + dX, baseY), 
				positiontexturevertex6.setTexturePosition(baseX, baseY)}, textureWidth, textureHeight);
		
		for(TexturedQuad quad : quads){
			this.addTexturedQuad(quad);
		}
	}


}
