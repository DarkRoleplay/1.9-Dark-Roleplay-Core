package net.dark_roleplay.drpcore.modules.wood;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class TextureGenerator {

	private BufferedImage[] requiredTextures;
	private JsonArray texturesArr;
	
	private Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();
	
	public TextureGenerator(JsonElement elm){
		if(elm.isJsonObject()){
			JsonObject obj = elm.getAsJsonObject();
			
			if(obj.has("required_textures") && obj.has("textures")){
				JsonArray requiredTexturesArr = obj.get("required_textures").getAsJsonArray();
				requiredTextures = new BufferedImage[requiredTexturesArr.size()];
				for(int i = 0; i < requiredTexturesArr.size(); i ++){
					try {
						InputStream is = ClientProxy.getResource(new ResourceLocation(requiredTexturesArr.get(i).getAsString())).getInputStream();
						requiredTextures[i] = ImageIO.read(is);
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				texturesArr = obj.get("textures").getAsJsonArray();
			}
		}
	}
	
	public void generateTextures(List<Wood> woods, Map<String, BufferedImage> bark, Map<String, BufferedImage> logTop, Map<String, BufferedImage> plank, Map<String, BufferedImage> cleanPlank){
		for(int i = 0; i < texturesArr.size(); i++){
			JsonObject obj = texturesArr.get(i).getAsJsonObject();
			Map<String, BufferedImage> base = new HashMap<String, BufferedImage>();
			switch(obj.get("base").getAsString()){
				case "bark":
					for(Wood key : woods){
						base.put(key.getName(), copyImage(bark.get(key.getName())));
					}
					break;
				case "log_top":
					for(Wood key : woods){
						base.put(key.getName(), copyImage(logTop.get(key.getName())));
					}
					break;
				case "plank":
					for(Wood key : woods){
						base.put(key.getName(), copyImage(plank.get(key.getName())));
					}
					break;
				case "clean_plank":
					for(Wood key : woods){
						base.put(key.getName(), copyImage(cleanPlank.get(key.getName())));
					}
					break;
			}
			
			JsonArray manipulations = obj.get("manipulations").getAsJsonArray();
			for(int j = 0; j < manipulations.size(); j++){

				JsonObject mani = manipulations.get(j).getAsJsonObject();
				boolean cached = mani.has("cached_texture");
				JsonElement texture = mani.has("texture") ? mani.get("texture") : mani.get("cached_texture");
				switch(mani.get("type").getAsString()){
					case "overlay":
						if(texture != null){
							for(Wood key : woods){
								base.put(key.getName(), overlay(base.get(key.getName()), !cached ? requiredTextures[texture.getAsInt()] : this.cache.get(texture.getAsString().replace("%wood%", key.getName()))));
							}
						}
						break;
					case "mask":
						if(texture != null){
							for(Wood key : woods){
								base.put(key.getName(), mask(base.get(key.getName()), !cached ? requiredTextures[texture.getAsInt()] : this.cache.get(texture.getAsString().replace("%wood%", key.getName()))));
							}
						}
						break;
					case "rotate":
						if(!mani.has("angle"))
							break;
						int angle = mani.get("angle").getAsInt();
						for(Wood key : woods){
							base.put(key.getName(), rotate(base.get(key.getName()), angle));
						}
						break;
					case "flip":
						break;
				}
			}
			for(String key : base.keySet()){
				if(obj.has("output")){
					File outputFile = new File(DRPCoreReferences.DARK_ROLEPLAY_ARGH + "/" + obj.get("output").getAsString().replaceAll("%wood%", key).replaceFirst(":", "/") + ".png");
	
					try {
						outputFile.getParentFile().mkdirs();
						ImageIO.write(base.get(key), "png", outputFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(obj.has("cache")){
					this.cache.put(obj.get("cache").getAsString().replace("%wood%", key), base.get(key));
				}
			}
		}
	}
	
	public BufferedImage mask(BufferedImage img, BufferedImage mask){
		WritableRaster raster = img.getRaster();
		Raster maskData = mask.getRaster();
		
        int[] pixel = new int[4];
        int[] pixel2 = new int[4];
		int width = img.getWidth(null);
        int height = img.getHeight(null);
        
		for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
            	pixel = maskData.getPixel(x, y, pixel);
            	pixel2 = raster.getPixel(x, y, pixel2);
            	pixel2[0] &= pixel[0];
            	pixel2[1] &= pixel[1];
            	pixel2[2] &= pixel[2];
            	pixel2[3] &= pixel[3];
            	
            	raster.setPixel(x, y, pixel2);
            }
		}
		
		img.setData(raster);
		
		return img;
	}
	
	public BufferedImage overlay(BufferedImage img, BufferedImage mask){
		WritableRaster raster = img.getRaster();
		Raster maskData = mask.getRaster();
		
        int[] pixel = new int[4];
        int[] pixel2 = new int[4];
		int width = img.getWidth(null);
        int height = img.getHeight(null);
        
		for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
            	pixel = maskData.getPixel(x, y, pixel);
            	pixel2 = raster.getPixel(x, y, pixel2);
            	pixel2[0] = (pixel[0] * pixel[3] / 255) + (pixel2[0] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[1] = (pixel[1] * pixel[3] / 255) + (pixel2[1] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[2] = (pixel[2] * pixel[3] / 255) + (pixel2[2] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[3] = pixel[3] + (pixel2[3] * (255 - pixel[3]) / 255);
            	
            	raster.setPixel(x, y, pixel2);
            }
		}
		img.setData(raster);
		
		return img;
	}
	
	public BufferedImage rotate(BufferedImage img, int angle){
		double sin = Math.abs(Math.sin(Math.toRadians(angle))),
				cos = Math.abs(Math.cos(Math.toRadians(angle)));

		int w = img.getWidth(null), h = img.getHeight(null);

		int neww = (int) Math.floor(w*cos + h*sin),
				newh = (int) Math.floor(h*cos + w*sin);

		BufferedImage bimg = new BufferedImage(neww, newh, img.getType());
		Graphics2D g = bimg.createGraphics();

		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(Math.toRadians(angle), w/2, h/2);
		g.drawRenderedImage(img, null);
		g.dispose();

		return bimg;
	}
	
	public BufferedImage flip(BufferedImage img, boolean horizontal){
		WritableRaster raster = img.getRaster();
		Raster maskData = raster;
		
        int[] pixel = new int[4];
        int[] pixel2 = new int[4];
		int width = img.getWidth(null);
        int height = img.getHeight(null);
        
		for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
            	pixel = maskData.getPixel(x, y, pixel);
            	pixel2 = raster.getPixel(x, y, pixel2);
            	pixel2[0] = (pixel[0] * pixel[3] / 255) + (pixel2[0] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[1] = (pixel[1] * pixel[3] / 255) + (pixel2[1] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[2] = (pixel[2] * pixel[3] / 255) + (pixel2[2] * pixel2[3] * (255 - pixel[3]) / 65025);
            	pixel2[3] = pixel[3] + (pixel2[3] * (255 - pixel[3]) / 255);
            	
            	raster.setPixel(x, y, pixel2);
            }
		}
		
		img.setData(raster);
		
		return img;
	}
	
	private static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics2D g = b.createGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
}
