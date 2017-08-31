package net.dark_roleplay.drpcore.api.gui;

import java.io.IOException;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.advanced.IGuiElement;

public abstract class DRPAbstractGuiElement implements IGuiElement{

	protected int posX, posY;
	protected int width, height;
	
	@Override
	public int addChild(IGuiElement child) {
		return -1;
	}

	@Override
	public IGuiElement getChild(int id) {
		return null;
	}

	@Override
	public List<IGuiElement> getChildren() {
		return null;
	}

	@Override
	public int getPosX() {
		return this.posX;
	}

	@Override
	public int getPosY() {
		return this.posY;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

}
