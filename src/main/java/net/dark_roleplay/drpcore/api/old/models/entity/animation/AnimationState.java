package net.dark_roleplay.drpcore.api.old.models.entity.animation;

import java.util.Map;

import net.dark_roleplay.drpcore.api.old.models.entity.Bone;

public class AnimationState {

	private Map<String, Animation> animations;
	private Animation currentAnimation;
	
	public AnimationState(Map<String, Animation> animations, Animation defaultAnimation) {
		this.animations = animations;
		this.currentAnimation = defaultAnimation;
	}
	
	public void progressAnimation(float partialTicks) {
		this.currentAnimation.progressAnimation(partialTicks);
	}
	
	public BoneAnimation getState(Bone bone) {
		return currentAnimation.getState(bone);
	}
	
	public void setAnimation(String animation) {
		if(this.animations.containsKey(animation)) {
			this.currentAnimation.reset();
			this.currentAnimation = this.animations.get(animation);
		}
	}
	
}
