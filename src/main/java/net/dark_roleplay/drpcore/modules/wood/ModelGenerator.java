package net.dark_roleplay.drpcore.modules.wood;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
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
		for(int i = 0; i < modelArr.size(); i++){
			JsonObject obj = modelArr.get(i).getAsJsonObject();
			Charset charset = StandardCharsets.UTF_8;
			String content;
			String destPath = obj.get("output").getAsString().replaceFirst(":", "/");
			try {
				InputStream is = ClientProxy.getResource(new ResourceLocation(obj.get("model").getAsString())).getInputStream();
				content = IOUtils.toString(is, charset);
				is.close();
				for(Wood wood : woods){
					File dest = new File(DRPCoreReferences.DARK_ROLEPLAY_ARGH + "/" + destPath.replaceAll("%wood%", wood.getName()));
					dest.getParentFile().mkdirs();
					String base = content.replaceAll("%wood%", wood.getName());
					File woodDest = new File(dest.getPath().replaceAll("%wood%", wood.getName()));
					Files.write(woodDest.toPath(), base.getBytes(charset));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



