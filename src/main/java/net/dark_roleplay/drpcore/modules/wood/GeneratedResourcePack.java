package net.dark_roleplay.drpcore.modules.wood;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;

import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLContainerHolder;
import net.minecraftforge.fml.common.ModContainer;

public class GeneratedResourcePack implements IResourcePack, IResourceManagerReloadListener {
	
	protected boolean validPath(ResourceLocation location) {
		return location.getResourceDomain().equals("minecraft") && location.getResourcePath().startsWith("shaders/");
	}
	
	private final Map<ResourceLocation, String> loadedData = new HashMap<>();

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
        if (validPath(location)) {
            String s = loadedData.computeIfAbsent(location, loc -> {
                InputStream in = Blur.class.getResourceAsStream("/" + location.getResourcePath());
                StringBuilder data = new StringBuilder();
                Scanner scan = new Scanner(in);
                try {
                    while (scan.hasNextLine()) {
                        data.append(scan.nextLine().replaceAll("@radius@", Integer.toString(Blur.instance.radius))).append('\n');
                    }
                } finally {
                    scan.close();
                }
                return data.toString();
            });

            return new ByteArrayInputStream(s.getBytes());
        }
        throw new FileNotFoundException(location.toString());
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		return validPath(location) && Blur.class.getResource("/" + location.getResourcePath()) != null;
	}

	@Override
	public Set<String> getResourceDomains() {
		return ImmutableSet.of("minecraft");
	}

	@SuppressWarnings({ "unchecked", "null" })
    @Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
	    if ("pack".equals(metadataSectionName)) {
	        return (T) new PackMetadataSection(new TextComponentString("Blur's default shaders"), 3);
	    }
	    return null;
    }

	@Override
	public BufferedImage getPackImage() throws IOException {
		throw new FileNotFoundException("pack.png");
	}

	@Override
	public String getPackName() {
		return "Blur dummy resource pack";
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	    loadedData.clear();
	}

}