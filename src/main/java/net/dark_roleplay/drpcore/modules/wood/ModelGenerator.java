package net.dark_roleplay.drpcore.modules.wood;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.minecraft.util.ResourceLocation;

public class ModelGenerator {

	private JsonArray modelArr;
	
	public ModelGenerator(JsonElement elm){
		if(elm.isJsonObject()){
			JsonObject obj = elm.getAsJsonObject();
			
			if(obj.has("models")){
				modelArr = obj.get("models").getAsJsonArray();
			}
		}
	}
	
	public void generateModels(List<Wood> woods){
		
	}

}
