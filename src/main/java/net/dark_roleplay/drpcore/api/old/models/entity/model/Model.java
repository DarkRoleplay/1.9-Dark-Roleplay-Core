package net.dark_roleplay.drpcore.api.old.models.entity.model;

import net.dark_roleplay.drpcore.api.old.models.TexturedQuad;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.BufferBuilder;

public class Model{
	
	protected final TexturedQuad[] quadList;
	private int freeQuads = 0;
	
	public Model(int quadAmount){
		this.quadList = new TexturedQuad[quadAmount];
		this.freeQuads = quadAmount;
	}
	
	public Model(TexturedQuad[] quads){
		this.quadList = quads;
	}
	
	public boolean addTexturedQuad(PositionTextureVertex[] vertices){
		if(freeQuads <= 0 && vertices.length < 4){
			return false;
		}else{
			quadList[quadList.length - freeQuads] = new TexturedQuad(vertices);
			freeQuads--;
			return true;
		}
	}
	
	public boolean addTexturedQuad(TexturedQuad quad){
		if(freeQuads <= 0){
			return false;
		}else{
			quadList[quadList.length - freeQuads] = quad;
			freeQuads--;
			return true;
		}
	}
	
	public void render(BufferBuilder render, float scale) {
		for(TexturedQuad quad : quadList){
			quad.draw(render, scale);
		}
	}
}
