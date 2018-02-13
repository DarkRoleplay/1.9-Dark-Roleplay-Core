package net.dark_roleplay.drpcore.modules.materials;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.dark_roleplay.drpcore.api.Modules;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public class MaterialWood extends Material{

	protected BufferedImage bark;
	protected BufferedImage plank;
	protected BufferedImage cleanPlank;
	protected BufferedImage logTop;
	
	public MaterialWood(String name, ResourceLocation bark, ResourceLocation plank, ResourceLocation cleanPlank, ResourceLocation logTop) {
		super("wood", "%wood%", name, Material.ResourceGeneratorType.TEXTURES);
		
		if(Modules.MATERIALS.side.isClient()){
			ProgressBar progressBar = ProgressManager.push("Loading wood Textures for " + name, 4);
			progressBar.step("bark: " + bark.toString());
			try{
				this.bark = ImageIO.read(ClientProxy.getResource(bark).getInputStream());
			}catch(IOException e) {
				System.out.println("Failed to load bark texture: " + bark.toString());
			}
	
			progressBar.step("plank: " + plank.toString());
			try{
				this.plank = ImageIO.read(ClientProxy.getResource(plank).getInputStream());
			}catch(IOException e) {
				System.out.println("Failed to load plank texture: " + plank.toString());
			}
	
			progressBar.step("cleanPlank: " + cleanPlank.toString());
			try{
				this.cleanPlank = ImageIO.read(ClientProxy.getResource(cleanPlank).getInputStream());
			}catch(IOException e) {
				System.out.println("Failed to load cleanPlank texture: " + cleanPlank.toString());
			}
	
			progressBar.step("logTop: " + logTop.toString());
			try{
				this.logTop = ImageIO.read(ClientProxy.getResource(logTop).getInputStream());
			}catch(IOException e) {
				System.out.println("Failed to load logTop texture: " + logTop.toString());
			}
			ProgressManager.pop(progressBar);
		}
	}

	@Override
	public int getTint() {
		return 0;
	}

	@Override
	public BufferedImage getTexture(String key) {
		switch(key){
			case "bark":
				return this.bark;
			case "plank":
				return this.plank;
			case "clean_plank":
				return this.cleanPlank;
			case "log_top":
				return this.logTop;
			default:
				return null;
		}
	}

	@Override
	public void cleanTextures() {
		this.bark = null;
		this.plank = null;
		this.cleanPlank = null;
		this.logTop = null;
	}
}
