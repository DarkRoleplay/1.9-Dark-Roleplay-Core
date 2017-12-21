package net.dark_roleplay.drpcore.modules.wood;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Wood{
	
	protected ResourceLocation barkTexture;
	protected ResourceLocation logTopTexture;
	protected ResourceLocation plankTexture;
	protected ResourceLocation cleanPlankTexture;
	
	protected final String name;
	
	public Wood(String name){
		this.name = name;
	}

	public ResourceLocation getBarkTexture() {
		return barkTexture;
	}

	public Wood setBarkTexture(ResourceLocation barkTexture) {
		this.barkTexture = barkTexture;
		return this;
	}

	public ResourceLocation getLogTopTexture() {
		return logTopTexture;
	}

	public Wood setLogTopTexture(ResourceLocation logTopTexture) {
		this.logTopTexture = logTopTexture;
		return this;
	}

	public ResourceLocation getPlankTexture() {
		return plankTexture;
	}

	public Wood setPlankTexture(ResourceLocation plankTexture) {
		this.plankTexture = plankTexture;
		return this;
	}

	public ResourceLocation getCleanPlankTexture() {
		return cleanPlankTexture;
	}

	public Wood setCleanPlankTexture(ResourceLocation cleanPlankTexture) {
		this.cleanPlankTexture = cleanPlankTexture;
		return this;
	}

	public String getName() {
		return name;
	}
}
