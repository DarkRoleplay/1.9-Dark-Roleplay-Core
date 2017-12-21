package net.dark_roleplay.drpcore.modules.wood;

import net.minecraft.util.ResourceLocation;

public class WoodenBlock {

	protected ResourceLocation baseBlockState;
	protected ResourceLocation[] baseModels;
	protected ResourceLocation textureGenerator;

	protected final String registryName;
	
	/**
	 * each file should contain %wood% in it's name. So it can be replaced for every wood type.
	 * @param blockRegistryName
	 */
	public WoodenBlock(ResourceLocation blockRegistryName){
		this.registryName = blockRegistryName.toString();
	}

	public ResourceLocation getBaseBlockState() {
		return baseBlockState;
	}

	public WoodenBlock setBaseBlockState(ResourceLocation baseBlockState) {
		this.baseBlockState = baseBlockState;
		return this;
	}

	public ResourceLocation[] getBaseModels() {
		return baseModels;
	}

	public WoodenBlock setBaseModels(ResourceLocation[] baseModels) {
		this.baseModels = baseModels;
		return this;
	}

	public String getRegistryName() {
		return registryName;
	}

	public ResourceLocation getTextureGenerator() {
		return textureGenerator;
	}

	public WoodenBlock setTextureGenerator(ResourceLocation textureGenerator) {
		this.textureGenerator = textureGenerator;
		return this;
	}
	
	
}
