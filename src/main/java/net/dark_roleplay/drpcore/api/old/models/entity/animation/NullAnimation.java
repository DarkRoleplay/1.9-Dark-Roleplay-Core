package net.dark_roleplay.drpcore.api.old.models.entity.animation;

public class NullAnimation extends BoneAnimation{

	public NullAnimation() {
		super(1, 1, new float[1], new float[1], new float[1]);
	}
	
	public float getRotationX() {
		return 0F;
	}
	
	public float getRotationY() {
		return 0F;
	}
	
	public float getRotationZ() {
		return 0F;
	}

}
