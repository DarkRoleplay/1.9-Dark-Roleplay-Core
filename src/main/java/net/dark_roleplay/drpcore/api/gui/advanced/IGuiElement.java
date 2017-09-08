package net.dark_roleplay.drpcore.api.gui.advanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import net.dark_roleplay.drpcore.api.skills.Skill;
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
	
	public List<IGuiElement> getChildren();
	
	public void draw(int mouseX, int mouseY, float partialTicks);
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException;
	
	public int getPosX();
	public int getPosY();
	public void setSize(int width, int height);

	public int getWidth();
	public int getHeight();
	public void setPos(int posX, int posY);
	
	public boolean isVisible();
	public void setVsisible(boolean visible);
	
	public abstract class IMPL extends Gui implements IGuiElement{

		protected int posX, posY;
		protected int width, height;
		private boolean visible = true;
		
		protected List<IGuiElement> children = new ArrayList<IGuiElement>();
		
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
		public void setSize(int width, int height) {
			this.width = width;
			this.height = height;
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
		public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
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
		public boolean isVisible(){
			return this.visible;
		}
		
		@Override
		public void setVsisible(boolean visible){
			this.visible = visible;
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
//        	bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution-1))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution-1))), 0.0D).endVertex();
//        	bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution-2))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution-2))), 0.0D).endVertex();
//        	bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution-3))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution-3))), 0.0D).endVertex();

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
	}
}
