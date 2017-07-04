package net.dark_roleplay.drpcore.client.gui.structure;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.tileentities.TileEntity_StructureController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class Gui_StructureControll extends DRPGuiScreen {

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID,
			"textures/guis/recipe_crafting_simple.png");

	private final TileEntity_StructureController tileStructure;

	private static final int[] LEGAL_KEY_CODES = new int[] { 203, 205, 14, 211, 199, 207 };

	private GuiTextField nameEdit;
	private GuiTextField posXEdit;
	private GuiTextField posYEdit;
	private GuiTextField posZEdit;
	private GuiTextField sizeXEdit;
	private GuiTextField sizeYEdit;
	private GuiTextField sizeZEdit;

	private GuiButton showAirButton;
	private GuiButton showBoundingBoxButton;

	private GuiButton applyButton;
	private Mirror mirror = Mirror.NONE;
	private Rotation rotation = Rotation.NONE;
	private TileEntityStructure.Mode mode = TileEntityStructure.Mode.DATA;
	private boolean showAir;
	private boolean showBoundingBox;
	private GuiButton cancelButton;
	private GuiButton saveButton;
	private GuiButton loadButton;
	private GuiButton modeButton;
	private final List<GuiTextField> tabOrder = Lists.<GuiTextField>newArrayList();

	public Gui_StructureControll(TileEntity_StructureController te) {
		super(bg, 178, 161);
		this.tileStructure = te;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		byte buttonID = 0;

		this.showAirButton = this.addButton(new GuiButton(buttonID++, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWAIR"));
		this.showBoundingBoxButton = this
				.addButton(new GuiButton(buttonID++, this.width / 2 + 4 + 100, 80, 50, 20, "SHOWBB"));
		this.applyButton = this
				.addButton(new GuiButton(buttonID++, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.apply")));
		this.modeButton = this.addButton(new GuiButton(buttonID++, this.width / 2 - 4 - 150, 185, 50, 20, "MODE"));

		this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel")));
		this.saveButton = this.addButton(new GuiButton(9, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.save")));
		this.loadButton = this.addButton(new GuiButton(10, this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.load")));
		
		this.nameEdit = new GuiTextField(2, this.fontRenderer, this.width / 2 - 152, 40, 300, 20);
		this.nameEdit.setMaxStringLength(64);
		this.nameEdit.setText(this.tileStructure.getName());
		// this.tabOrder.add(this.nameEdit);
		BlockPos blockpos = this.tileStructure.getPosition();
		this.posXEdit = new GuiTextField(3, this.fontRenderer, this.width / 2 - 152, 80, 80, 20);
		this.posXEdit.setMaxStringLength(15);
		this.posXEdit.setText(Integer.toString(blockpos.getX()));
		// this.tabOrder.add(this.posXEdit);
		this.posYEdit = new GuiTextField(4, this.fontRenderer, this.width / 2 - 72, 80, 80, 20);
		this.posYEdit.setMaxStringLength(15);
		this.posYEdit.setText(Integer.toString(blockpos.getY()));
		// this.tabOrder.add(this.posYEdit);
		this.posZEdit = new GuiTextField(5, this.fontRenderer, this.width / 2 + 8, 80, 80, 20);
		this.posZEdit.setMaxStringLength(15);
		this.posZEdit.setText(Integer.toString(blockpos.getZ()));
		// this.tabOrder.add(this.posZEdit);
		BlockPos blockpos1 = this.tileStructure.getStructureSize();
		this.sizeXEdit = new GuiTextField(6, this.fontRenderer, this.width / 2 - 152, 120, 80, 20);
		this.sizeXEdit.setMaxStringLength(15);
		this.sizeXEdit.setText(Integer.toString(blockpos1.getX()));
		// this.tabOrder.add(this.sizeXEdit);
		this.sizeYEdit = new GuiTextField(7, this.fontRenderer, this.width / 2 - 72, 120, 80, 20);
		this.sizeYEdit.setMaxStringLength(15);
		this.sizeYEdit.setText(Integer.toString(blockpos1.getY()));
		// this.tabOrder.add(this.sizeYEdit);
		this.sizeZEdit = new GuiTextField(8, this.fontRenderer, this.width / 2 + 8, 120, 80, 20);
		this.sizeZEdit.setMaxStringLength(15);
		this.sizeZEdit.setText(Integer.toString(blockpos1.getZ()));
		// this.tabOrder.add(this.sizeZEdit);
		
		this.showAir = this.tileStructure.showsAir();
		this.updateToggleAirButton();
		this.showBoundingBox = this.tileStructure.showsBoundingBox();
		this.updateToggleBoundingBox();
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {

		if (this.tileStructure.getMode() == TileEntity_StructureController.Mode.SAVE) {
			this.nameEdit.drawTextBox();
			this.posXEdit.drawTextBox();
			this.posYEdit.drawTextBox();
			this.posZEdit.drawTextBox();
			this.sizeXEdit.drawTextBox();
			this.sizeYEdit.drawTextBox();
			this.sizeZEdit.drawTextBox();
		}

	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == this.showAirButton.id) {
				this.tileStructure.setShowAir(!this.tileStructure.showsAir());
			} else if (button.id == this.showAirButton.id) {
				this.tileStructure.setShowBoundingBox(!this.tileStructure.showsBoundingBox());
			} else if (button.id == this.modeButton.id) {
				this.tileStructure.nextMode();
				this.updateMode();
			}
		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (this.tileStructure.getMode() == TileEntity_StructureController.Mode.SAVE) {

			if (this.nameEdit.getVisible()) {
				this.nameEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.posXEdit.getVisible()) {
				this.posXEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.posYEdit.getVisible()) {
				this.posYEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.posZEdit.getVisible()) {
				this.posZEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.sizeXEdit.getVisible()) {
				this.sizeXEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.sizeYEdit.getVisible()) {
				this.sizeYEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

			if (this.sizeZEdit.getVisible()) {
				this.sizeZEdit.mouseClicked(mouseX, mouseY, mouseButton);
			}

		}

	}

	private void updateMode() {
		this.nameEdit.setFocused(false);
		this.posXEdit.setFocused(false);
		this.posYEdit.setFocused(false);
		this.posZEdit.setFocused(false);
		this.sizeXEdit.setFocused(false);
		this.sizeYEdit.setFocused(false);
		this.sizeZEdit.setFocused(false);
		this.nameEdit.setVisible(false);
		this.nameEdit.setFocused(false);
		this.posXEdit.setVisible(false);
		this.posYEdit.setVisible(false);
		this.posZEdit.setVisible(false);
		this.sizeXEdit.setVisible(false);
		this.sizeYEdit.setVisible(false);
		this.sizeZEdit.setVisible(false);
		this.saveButton.visible = false;
		this.loadButton.visible = false;
		this.showAirButton.visible = false;
		this.showBoundingBoxButton.visible = false;

		switch (this.tileStructure.getMode()) {
			case SAVE:
				this.nameEdit.setVisible(true);
				this.nameEdit.setFocused(true);
				this.posXEdit.setVisible(true);
				this.posYEdit.setVisible(true);
				this.posZEdit.setVisible(true);
				this.sizeXEdit.setVisible(true);
				this.sizeYEdit.setVisible(true);
				this.sizeZEdit.setVisible(true);
				this.saveButton.visible = true;
				this.showAirButton.visible = true;
				break;
			case LOAD:
				this.nameEdit.setVisible(true);
				this.nameEdit.setFocused(true);
				this.posXEdit.setVisible(true);
				this.posYEdit.setVisible(true);
				this.posZEdit.setVisible(true);
				this.loadButton.visible = true;
				this.showBoundingBoxButton.visible = true;
				break;
			case CORNER:
				this.nameEdit.setVisible(true);
				this.nameEdit.setFocused(true);
				break;
		}

		this.modeButton.displayString = I18n.format("structure_block.mode." + this.tileStructure.getMode().getName());
	}

	private void updateToggleAirButton() {
		boolean flag = this.tileStructure.showsAir();

		if (flag) {
			this.showAirButton.displayString = I18n.format("options.on");
		} else {
			this.showAirButton.displayString = I18n.format("options.off");
		}
	}

	private void updateToggleBoundingBox() {
		boolean flag = this.tileStructure.showsBoundingBox();

		if (flag) {
			this.showBoundingBoxButton.displayString = I18n.format("options.on");
		} else {
			this.showBoundingBoxButton.displayString = I18n.format("options.off");
		}
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {

		if (keyCode == 1) {
			this.mc.displayGuiScreen((GuiScreen) null);
		}

		if (this.tileStructure.getMode() == TileEntity_StructureController.Mode.SAVE) {
			if (this.nameEdit.getVisible() && isValidCharacterForName(typedChar, keyCode)) {
				this.nameEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.posXEdit.getVisible()) {
				this.posXEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.posYEdit.getVisible()) {
				this.posYEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.posZEdit.getVisible()) {
				this.posZEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.sizeXEdit.getVisible()) {
				this.sizeXEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.sizeYEdit.getVisible()) {
				this.sizeYEdit.textboxKeyTyped(typedChar, keyCode);
			}

			if (this.sizeZEdit.getVisible()) {
				this.sizeZEdit.textboxKeyTyped(typedChar, keyCode);
			}
		}
	}

	private static boolean isValidCharacterForName(char c, int keyCode) {
		boolean flag = true;

		for (int i : LEGAL_KEY_CODES) {
			if (i == keyCode) {
				return true;
			}
		}

		for (char c0 : ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS) {
			if (c0 == c) {
				flag = false;
				break;
			}
		}

		return flag;
	}
}
