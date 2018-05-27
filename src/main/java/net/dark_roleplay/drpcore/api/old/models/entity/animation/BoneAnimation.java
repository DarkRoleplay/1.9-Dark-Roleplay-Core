package net.dark_roleplay.drpcore.api.old.models.entity.animation;

public class BoneAnimation {

	private float frames;
	private int fps;
	
	private float[] rotationX;
	private float[] rotationY;
	private float[] rotationZ;
	
	private float stepX;
	private float stepY;
	private float stepZ;
	
	private float progress = 0;
	
	public BoneAnimation(int fps, int frames, float[] rotX, float[] rotY, float[] rotZ) {
		this.frames = frames;
		this.fps = fps;
		this.rotationX = rotX;
		this.rotationY = rotY;
		this.rotationZ = rotZ;
		this.stepX = (1F / rotationX.length * (this.frames / this.fps)) * 20F;
		this.stepY = (1F / rotationY.length * (this.frames / this.fps)) * 20F;
		this.stepZ = (1F / rotationZ.length * (this.frames / this.fps)) * 20F;
	}
	
	public BoneAnimation setProgress(float progress) {
		this.progress = progress;
		return this;
	}

	public void reset() {
		this.progress = 0F;
	}
	
	public float getRotationX() {
		if(rotationX.length == 1)
			return rotationX[0];
		int step = (int) Math.floor(progress / stepX);
		float interpolation = progress % stepX / stepX;
		return rotationX[step] * (1 - interpolation) + rotationX[step + 1 >= rotationX.length ? 0 : step + 1] * interpolation; 
	}
	
	public float getRotationY() {
		if(rotationY.length == 1)
			return rotationY[0];
		int step = (int) Math.floor(progress / stepY);
		float interpolation = progress % stepY / stepY;
		return rotationY[step] * (1 - interpolation) + rotationY[step + 1 >= rotationY.length ? 0 : step + 1] * interpolation; 
	}
	
	public float getRotationZ() {
		if(rotationZ.length == 1)
			return rotationZ[0];
		int step = (int) Math.floor(progress / stepZ);
		float interpolation = progress % stepZ / stepZ;
		return rotationZ[step] * (1 - interpolation) + rotationZ[step + 1 >= rotationZ.length ? 0 : step + 1] * interpolation; 
	}
}
