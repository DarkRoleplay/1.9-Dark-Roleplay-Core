package net.dark_roleplay.drpcore.client.resources;

import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;

public class CubeElement implements IElement{

	float posX, posY;
	float widht, height;
	
	Face[] faces = new Face[6];
	
	@Override
	public List<BakedQuad> bake() {
		// TODO Auto-generated method stu
		return null;
	}
	
	public static class Face{

		private boolean enabled;
		private EnumFacing culling;
		private float uMin, vMin, uMax, vMax;
		
		//Creates a disabled face
		public Face(){
			this.enabled = false;
		}
		
		public Face(EnumFacing culling, float uMin, float vMin, float uMax, float vMax) {
			this.enabled = true;
			this.culling = culling;
			this.uMin = uMin;
			this.vMin = vMin;
			this.uMax = uMax;
			this.vMax = vMax;
		}
		
//		public 
	}

}
