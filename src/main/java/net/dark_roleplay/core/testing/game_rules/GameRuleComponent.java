package net.dark_roleplay.core.testing.game_rules;

import java.io.IOException;

import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core_modules.guis.api.components.base.input.booleans.Switch;
import net.dark_roleplay.library.experimental.variables.wrappers.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class GameRuleComponent extends Switch{

	private String gamerule = "none";

	public GameRuleComponent(int posX, int posY, BooleanWrapper bool, String gamerule) {
		super(posX, posY, bool);
		this.maxWidth = 300;
		this.setWidth(300);
		this.gamerule = gamerule;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.gamerule, this.posX, this.posY + 1, 0xFFFFFFFF);

		boolean hover = this.isHovered(mouseX, mouseY);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());
		this.drawTexturedModalRect(this.posX + this.width - 24, this.posY, this.u + (hover ? 24 : 0), this.v + (this.bool.get() ? this.height : 0), 24, this.height);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(this.isHovered(mouseX, mouseY)) {
			this.bool.set(!this.bool.get());
			DRPCorePackets.sendToServer(new ListenerPackets.SyncBooleanRuleToListener(this.gamerule, this.bool.get()));
		}

		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));

		return this.isHovered(mouseX, mouseY);
	}
}
