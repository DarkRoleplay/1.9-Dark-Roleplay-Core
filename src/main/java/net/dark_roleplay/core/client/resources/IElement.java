package net.dark_roleplay.core.client.resources;

import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;

public interface IElement {

	public List<BakedQuad> bake();
	
}