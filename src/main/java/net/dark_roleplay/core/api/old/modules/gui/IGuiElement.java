package net.dark_roleplay.core.api.old.modules.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public interface IGuiElement {
	
	/**
	 * @param child
	 * @return child ID (-1 for child was not added)
	 */
	public int addChild(IGuiElement child);
	
	public IGuiElement getChild(int id);
	public void setChild(int id, IGuiElement newChild);
	
	public List<IGuiElement> getChildren();
	
	public void draw(int mouseX, int mouseY, float partialTicks);
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton);
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY);
	public void mouseReleased(int mouseX, int mouseY);
	
	public int getPosX();
	public int getPosY();
	public IGuiElement setSize(int width, int height);

	public int getWidth();
	public int getHeight();
	public void setPos(int posX, int posY);
	
	public boolean isVisible();
	public void setVisible(boolean visible);
	
	public boolean isEnabled();
	public void setEnabled(boolean enabled);
	
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY);
	public boolean isHovered(int mouseX, int mouseY);
	
	public void handleMouseInput() throws IOException;
	
	public abstract class IMPL extends Gui implements IGuiElement{

		protected int posX = 0, posY = 0;
		protected int width = 10, height = 10;
		protected boolean visible = true;
		protected boolean enabled = true;
		
		
		protected List<IGuiElement> children = new ArrayList<IGuiElement>();
		
		@Override
		public int addChild(IGuiElement child) {
			return -1;
		}

		@Override
		public void setChild(int id, IGuiElement newChild){
			
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
		public IGuiElement setSize(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
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
		public void setPos(int posX, int posY) {
			this.posX = posX;
			this.posY = posY;
		}

		@Override
		public IGuiElement getChild(int id) {
			if(children.size() >= id && id > 0){
				return children.get(id);
			}else{
				return null;
			}
		}
		
		@Override
		public List<IGuiElement> getChildren() {
			return this.children;
		}
		
		@Override
		public void mouseDragged(Minecraft mc, int mouseX, int mouseY){
			for(IGuiElement element : this.children){
				element.mouseDragged(mc, mouseX, mouseY);
			}
		}
		
		@Override
		public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
			if(this.children != null){
				for(IGuiElement element : this.children){
					if(element.isVisible() && (mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
						if(element.mouseClicked(mouseX - element.getPosX(), mouseY - element.getPosY(), mouseButton)){
							return true;
						}
					}
				}
			}
			return false;
		}

		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
			if(this.enabled && this.visible && mouseX >= 0 && mouseY >= 0 && mouseX <= this.width && mouseY <= this.height){
				if(this.children != null){
					for(IGuiElement element : this.children){
						if(element.isVisible() && (mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
							if(element.mousePressed(mc, mouseX - element.getPosX(), mouseY - element.getPosY())){
								return true;
							}
						}
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public void mouseReleased(int mouseX, int mouseY){
			for(IGuiElement element : this.children){
				element.mouseReleased(mouseX, mouseY);
			}
		}
		
		@Override
		public void handleMouseInput() throws IOException{
			for(IGuiElement element : this.children){
				element.handleMouseInput();
			}
		}
		
		
		@Override
		public boolean isVisible(){
			return this.visible;
		}
		
		@Override
		public void setVisible(boolean visible){
			this.visible = visible;
		}
		
		@Override
		public boolean isEnabled(){
			return this.enabled;
		}
		
		@Override
		public void setEnabled(boolean enabled){
			this.enabled = enabled;
		}
		
		@Override
		public boolean isHovered(int mouseX, int mouseY){
			return mouseX >= 0 && mouseY >= 0 && mouseX <= this.width && mouseY <= this.height;
		}
		
		protected static void drawLine(int x1, int y1, int x2, int y2, int color) {

			float f3 = (float) (color >> 24 & 255) / 255.0F;
			float f = (float) (color >> 16 & 255) / 255.0F;
			float f1 = (float) (color >> 8 & 255) / 255.0F;
			float f2 = (float) (color & 255) / 255.0F;
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.color(f, f1, f2, f3);

			float angle = getAngleRad(new Point(x1,y1), new Point(x2,y2));
			
			double halfPi = Math.PI / 2;
			
			float a1 = (float) (x1 + 0.5F * Math.cos(angle + halfPi)), a2 = (float) (y1 + 0.5F * Math.sin(angle + halfPi));
			float b1 = (float) (x2 + 0.5F * Math.cos(angle + halfPi)), b2 = (float) (y2 + 0.5F * Math.sin(angle + halfPi));
			float c1 = (float) (x2 + 0.5F * Math.cos(angle - halfPi)), c2 = (float) (y2 + 0.5F * Math.sin(angle - halfPi));
			float d1 = (float) (x1 + 0.5F * Math.cos(angle - halfPi)), d2 = (float) (y1 + 0.5F * Math.sin(angle - halfPi));

			
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
			//new
			vertexbuffer.pos(a1, a2, 0.0D).endVertex();
			vertexbuffer.pos(b1, b2, 0.0D).endVertex();
			vertexbuffer.pos(c1, c2, 0.0D).endVertex();
			vertexbuffer.pos(d1, d2, 0.0D).endVertex();
			tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}
		
		private static float getAngleRad(Point a, Point b){
			Point vec = new Point(b.getX() - a.getX(), b.getY() - a.getY());
			return (float) (Math.acos(vec.getX() / (Math.sqrt(Math.pow(vec.getX(), 2) + Math.pow(vec.getY(), 2)))));
		}
		
		public static void drawCenteredRect(int centerX, int centerY, int radius, double angle, int color){
			double rads = Math.toRadians(angle);
			double diagRadius = radius * Math.sqrt(2);
	        float f3 = (float)(color >> 24 & 255) / 255.0F;
	        float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.color(f, f1, f2, f3);
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.25), centerY + radius * Math.sin(rads + Math.PI * 1.25), 0.0D).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.75), centerY + radius * Math.sin(rads + Math.PI * 0.75), 0.0D).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.25), centerY + radius * Math.sin(rads + Math.PI * 0.25), 0.0D).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.75), centerY + radius * Math.sin(rads + Math.PI * 1.75), 0.0D).endVertex();
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
		
		/**
		 * @param posX "X" position of the top left corner
		 * @param posY "Y" position of the top left corner
		 * @param widht Size of the rect on the "X" axis
		 * @param height Size of the rect on the "Y" axis
		 * @param u "X" position for the texture, top left corner
		 * @param v "Y" position for the texture, top left corner
		 * @param uMax "X" position for the texture, bottom right corner
		 * @param vMax "Y" position for the texture, bottom right corner
		 */
		public void drawPerctentedRect(int posX, int posY, int width, int height, float u, float v, float uMax, float vMax){
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	        bufferbuilder.pos(posX, posY + height, this.zLevel).tex(u, vMax).endVertex();
	        bufferbuilder.pos(posX + width, posY + height, this.zLevel).tex(uMax, vMax).endVertex();
	        bufferbuilder.pos(posX + width, posY, this.zLevel).tex(uMax, v).endVertex();
	        bufferbuilder.pos(posX, posY, this.zLevel).tex(u, v).endVertex();
	        tessellator.draw();
	    }
		
		public void drawTexturedCenteredRect(int centerX, int centerY, float u, float v, float uMax, float vMax, int radius, double angle){
			double rads = Math.toRadians(angle);
			double diagRadius = radius * Math.sqrt(2);
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.25), centerY + radius * Math.sin(rads + Math.PI * 1.25), 0.0D).tex(u, vMax).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.75), centerY + radius * Math.sin(rads + Math.PI * 0.75), 0.0D).tex(uMax, vMax).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.25), centerY + radius * Math.sin(rads + Math.PI * 0.25), 0.0D).tex(uMax, v).endVertex();
	        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.75), centerY + radius * Math.sin(rads + Math.PI * 1.75), 0.0D).tex(u, v).endVertex();
	        tessellator.draw();
	    }
		
		public static void drawCenteredCircle(int centerX, int centerY, int radius, int resoulution, int color){
			double diagRadius = radius * Math.sqrt(2);
	        float f3 = (float)(color >> 24 & 255) / 255.0F;
	        float f = (float)(color >> 16 & 255) / 255.0F;
	        float f1 = (float)(color >> 8 & 255) / 255.0F;
	        float f2 = (float)(color & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	        GlStateManager.color(f, f1, f2, f3);
	        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
	        double steps = (2D/resoulution);
	        double startX = centerX + radius * Math.cos(0);
	        double startY = centerY + radius * Math.sin(0);

        	bufferbuilder.pos(startX, startY, 0.0D).endVertex();

        	int i = 1;
        	for(;i < 4; i++){
	        	bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution-i))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution-i))), 0.0D).endVertex();
        	}
        	
        	for(; i < resoulution; i += 2){
            	bufferbuilder.pos(startX, startY, 0.0D).endVertex();
        		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i + 1))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i + 1))), 0.0D).endVertex();
        		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i))), 0.0D).endVertex();
        		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i - 1))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i - 1))), 0.0D).endVertex();
        	}
    		
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
		
		protected void drawTiled(int posX, int posY, int width, int height, int u, int v, int tileWidth, int tileHeight, int textureWidth, int textureHeight){
			if(height > tileHeight && width <= tileWidth){
				for(int i = 0; i < height; i += tileHeight){
					this.drawScaledCustomSizeModalRect(posX, posY + i, u, v, tileWidth, height - i >= tileHeight ? tileHeight : height - i, tileWidth, height - i >= tileHeight ? tileHeight : height - i, textureWidth, textureHeight);
				}
			}else if(width > tileWidth && height <= tileHeight){
				for(int i = 0; i < width; i += tileWidth){
					this.drawScaledCustomSizeModalRect(posX + i, posY, u, v,  width - i >= tileWidth ? tileWidth : width - i, tileHeight, width - i >= tileWidth ? tileWidth : width - i, tileHeight, textureWidth, textureHeight);
				}
			}else if(width > tileWidth && height > tileHeight){
				for(int i = 0; i < height; i += tileHeight){
					for(int j = 0; j < width; j += tileWidth){
						this.drawScaledCustomSizeModalRect(posX + j, posY + i, u, v, width - j >= tileWidth ? tileWidth : width - j, height - i >= tileHeight ? tileHeight : height - i, width - j >= tileWidth ? tileWidth : width - j, height - i >= tileHeight ? tileHeight : height - i, textureWidth, textureHeight);
					}
				}
			}
		}
	}
}
