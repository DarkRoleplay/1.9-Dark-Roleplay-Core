package net.dark_roleplay.drpcore.client.renderer.players;

import net.minecraft.util.ResourceLocation;

public class PremiumAddon {
	
	private int uuid;
	private int position;
	private String name;
	private int price;
	private String description;
	private Attachment_Premium attachment;
	private String[] style_urls;
	private ResourceLocation[] styles;

	public PremiumAddon(int uuid, int position, String name, int price, String description, Attachment_Premium attachment, String... style_urls){
		this.uuid = uuid;
		this.position = position;
		this.name = name;
		this.price = price;
		this.description = description;
		this.attachment = attachment;
		this.style_urls = style_urls;
	}

	public int getUuid() {
		return uuid;
	}

	public int getPosition() {
		return position;
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

	public Attachment_Premium getAttachment() {
		return attachment;
	}

	public ResourceLocation[] getStyles() {
		return styles;
	}
}
