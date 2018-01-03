package net.dark_roleplay.drpcore.modules.premium;

import net.minecraft.util.ResourceLocation;

public class PremiumAddon {
	
	private int uuid;
	private String name;
	private int price;
	private String description;
	private Attachment_Premium attachment;
	private String[] style_urls;
	private ResourceLocation[] styles;

	public PremiumAddon(int uuid, String name, int price, String description){
		this.uuid = uuid;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public int getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}
	
	public void loadDetailed(){
		Attachment_Premium attachment;
		String[] style_urls;
	}

	public Attachment_Premium getAttachment() {
		return attachment;
	}

	public ResourceLocation[] getStyles() {
		return styles;
	}
}
