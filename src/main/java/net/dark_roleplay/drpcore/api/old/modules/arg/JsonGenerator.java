package net.dark_roleplay.drpcore.api.old.modules.arg;

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

import net.dark_roleplay.drpcore.api.old.modules.materials.Material;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.References;
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
				InputStream is = ClientProxy.getResource(new ResourceLocation(obj.get("input").getAsString())).getInputStream();
				content = IOUtils.toString(is, charset);
				is.close();
				for(Material mat : materials){
					File dest = new File(References.FOLDER_ARG + "/" + mat.getFormatted(destPath));
					dest.getParentFile().mkdirs();
					String base = mat.getFormatted(content);
					File woodDest = new File(mat.getFormatted(dest.getPath()));
					Files.write(woodDest.toPath(), base.getBytes(charset));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



