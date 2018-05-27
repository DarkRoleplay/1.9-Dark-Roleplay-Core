package net.dark_roleplay.drpcore.api.old.models.entity.animation;

import java.util.Map;

import net.dark_roleplay.drpcore.api.old.models.entity.Bone;

public class Animation {

	private int frames;
	private int fps;
	private float progress = 0F;
	private float max = 1F;
	
	private Map<Bone, BoneAnimation> bones;
	
	public Animation(int fps, int frames, Map<Bone, BoneAnimation> bones) {
		this.fps = fps;
		this.frames = frames;
		this.bones = bones;
		this.max = frames / (fps * 1F) * 20;
	}
	
	public void reset() {
		this.progress = 0F;
		for(Bone bone : bones.keySet()) {
			bones.get(bone).reset();
		}
	}
	
	public BoneAnimation getState(Bone bone) {
		if(bones.containsKey(bone))
			return bones.get(bone).setProgress(progress);
		return new NullAnimation();
	}
	
	public void progressAnimation(float partialTicks) {
		this.progress = this.progress + partialTicks > this.max ? this.progress + partialTicks - this.max : this.progress + partialTicks;
		for(Bone bone : bones.keySet()) {
			bones.get(bone).setProgress(progress);
		}
	}
}
