package net.dark_roleplay.core.modules.automatic_resource_generation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.api.old.modules.materials.Material;
import net.dark_roleplay.core.client.ClientProxy;
import net.dark_roleplay.core.common.References;
import net.minecraft.util.ResourceLocation;

public class JsonGenerator {

	private JsonArray modelArr;
	
	public JsonGenerator(JsonElement elm){
		if(elm.isJsonObject()){
			JsonObject obj = elm.getAsJsonObject();
			
			if(obj.has("jsons")){
				modelArr = obj.get("jsons").getAsJsonArray();
			}
		}
	}
	
	public void generateModels(List<Material> materials){
		
		for(int i = 0; i < modelArr.size(); i++){
			JsonObject obj = modelArr.get(i).getAsJsonObject();
			Charset charset = StandardCharsets.UTF_8;
			String content;
			String destPath = obj.get("output").getAsString().replaceFirst(":", "/");
			try {
				boolean needsToCreate = false;
				for(Material mat : materials){
					File dest = new File(References.FOLDER_ARG + "/" + mat.getFormatted(destPath));
					if(!dest.exists())
						needsToCreate = true;
					break;
				}
				if(!needsToCreate)
					break;
					
				InputStream is = ClientProxy.getResource(new ResourceLocation(obj.get("input").getAsString())).getInputStream();
				content = IOUtils.toString(is, charset);
				is.close();
				for(Material mat : materials){
					File dest = new File(References.FOLDER_ARG + "/" + mat.getFormatted(destPath));
					if(dest.exists())
						continue;
					dest.getParentFile().mkdirs();
					String base = mat.getFormatted(content);
					Files.write(dest.toPath(), base.getBytes(charset));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



