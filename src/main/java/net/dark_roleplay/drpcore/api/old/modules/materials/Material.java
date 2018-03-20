package net.dark_roleplay.drpcore.api.old.modules.materials;

import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Material {

	protected String name;
	protected String formatKey;
	protected String formatValue;
	
	protected ResourceGeneratorType resourceGeneratorType;
	
	public Material(String name, String formatKey, String formatValue, ResourceGeneratorType type){
		this.name = name;
		this.formatKey = formatKey;
		this.formatValue = formatValue;
		this.resourceGeneratorType = type;
	}
	
	public String getFormatted(String text){
		return text.replaceAll(formatKey, formatValue);
	}
	
	public ResourceGeneratorType getType(){
		return resourceGeneratorType;
	}
	
	public abstract int getTint();
	
	public abstract BufferedImage getTexture(String key);
	
	public abstract void cleanTextures();
	
	public String getFormatValue(){
		return this.formatValue;
	}
	
	public enum ResourceGeneratorType{
		TEXTURES,
		TINT
	}
}
