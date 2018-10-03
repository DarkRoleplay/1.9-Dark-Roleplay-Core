package net.dark_roleplay.core.testing.modelling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.input.KeyCode;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;

public class ModelTestingGui extends GuiScreen {

	List<Vertex> vertices = new ArrayList<Vertex>();
	
	int rotationPosX;
	int rotationPosY;
	
	float yaw = 0f;
	float pitch = 0f;
	float roll = 0f;
	
	@Override
	public void initGui() {
		Random rnd = new Random();
		rotationPosX = this.width / 2;
		rotationPosY = this.height / 2;
		this.vertices.add(new Vertex(rnd.nextInt(10), rnd.nextInt(10), 0));
//		this.vertices.add(new Vertex(rnd.nextInt(200) + 50, rnd.nextInt(200) + 50, 50));
//		this.vertices.add(new Vertex(rnd.nextInt(200) + 50, rnd.nextInt(200) + 50, 50));
//		this.vertices.add(new Vertex(rnd.nextInt(200) + 50, rnd.nextInt(200) + 50, 50));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// this.drawDefaultBackground();
		//Raytracing
		
		
		Vec3d origin = new Vec3d(mouseX, mouseY, 100);
		Vec3d dir = new Vec3d(1F, 1F, 0F).normalize();
		
		for (Vertex vert : vertices) {
			vert.hoevered = trace(origin, dir, vert);
//			System.out.println(mouseX + " : " + mouseY + "-->" + vert.posX + " : " + vert.posY + " : " + vert.posZ);

//			System.out.println(vert.hoevered);
		}
		
		//BG

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(0.3F, 0.3F, 0.3F, 1F);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos((double) 0, (double) this.height, -500.0D).endVertex();
		bufferbuilder.pos((double) this.width, (double) this.height, -500.0D).endVertex();
		bufferbuilder.pos((double) this.width, (double) 0, -500.0D).endVertex();
		bufferbuilder.pos((double) 0, (double) 0, -500.0D).endVertex();
		tessellator.draw();

		GlStateManager.disableAlpha();
		GlStateManager.shadeModel(7425);
		
		
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);

		GlStateManager.rotate(this.yaw, 0F, 1F, 0F);
		GlStateManager.rotate(this.pitch, 1F, 0F, 0F);
		GlStateManager.translate(rotationPosX, rotationPosY, 0);

//		GlSta
		
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		
		for (Vertex vert : vertices) {
			buffer.pos(vert.posX, vert.posY, vert.posZ).color(0F, 1F, 0F, 1F).endVertex();
		}
		
		tessellator.draw();
		
		buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		for (Vertex vert : vertices) {
			vert.render(buffer, vert.hoevered ? 0f : 1f, 1F, !vert.hoevered ? 0f : 1f, 1F);
		}
		
		tessellator.draw();
		GlStateManager.popMatrix();
		
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

	}

	private static class Vertex {
		public int posX;
		public int posY;
		public int posZ;
		
		public boolean hoevered = false;

		public Vertex(int posX, int posY, int posZ) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
		}

		public void render(BufferBuilder builder, float red, float green, float blue, float alpha) {
			int i = 10;
			builder.pos(posX - i, posY - i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY - i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY - i, posZ + i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX - i, posY - i, posZ + i).color(red, green, blue, alpha).endVertex();

			builder.pos(posX - i, posY + i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY + i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY + i, posZ + i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX - i, posY + i, posZ + i).color(red, green, blue, alpha).endVertex();
			
			builder.pos(posX - i, posY + i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY + i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX + i, posY - i, posZ - i).color(red, green, blue, alpha).endVertex();
			builder.pos(posX - i, posY - i, posZ - i).color(red, green, blue, alpha).endVertex();


//			builder.pos(posX + i, posY + i, posZ - i).color(red, green, blue, 0.0F).endVertex();
//			builder.pos(posX + i, posY - i, posZ - i).color(red, green, blue, alpha).endVertex();
//
//			builder.pos(posX + i, posY + i, posZ + i).color(red, green, blue, 0.0F).endVertex();
//			builder.pos(posX + i, posY - i, posZ + i).color(red, green, blue, alpha).endVertex();
//
//			builder.pos(posX - i, posY + i, posZ + i).color(red, green, blue, 0.0F).endVertex();
//			builder.pos(posX - i, posY - i, posZ + i).color(red, green, blue, alpha).endVertex();
//			builder.pos(posX - i, posY - i, posZ + i).color(red, green, blue, 0.0F).endVertex();
		}
	}
	
	public static boolean trace(Vec3d rO, Vec3d dir, Vertex v) {
		// r.dir is unit direction vector of ray
		// lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
		// r.org is origin of ray
		int i = 10;

		Vec3d min = new Vec3d(v.posX - i, v.posY - i, v.posZ - i);
		Vec3d max = new Vec3d(v.posX + i, v.posY + i, v.posZ + i);

		
		float t[] = new float[9];

		t[0] = (float) (( min.x - rO.x) * dir.x);
		t[1] = (float) (( max.x - rO.x) * dir.x);
		t[2] = (float) (( min.y - rO.y) * dir.y);
		t[3] = (float) (( max.y - rO.y) * dir.y);
		t[4] = (float) (( min.z - rO.z) * dir.z);
		t[5] = (float) (( max.z - rO.z) * dir.z);

		t[6] = Math.max(Math.max(Math.min(t[0], t[1]), Math.min(t[2], t[3])), Math.min(t[4], t[5]));
		t[7] = Math.min(Math.min(Math.max(t[0], t[1]), Math.max(t[2], t[3])), Math.max(t[4], t[5]));
		
		if (t[7] < 0 || t[6] > t[7]){
			return false;
		}

		return true;
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
        super.keyTyped(typedChar, keyCode);
        if(keyCode == KeyCode.D.impl_getCode())
        	this.yaw += 0.1;
        if(keyCode == KeyCode.D.impl_getCode())
        	this.yaw -= 0.1;
    }
}
