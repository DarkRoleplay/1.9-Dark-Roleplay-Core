package net.dark_roleplay.drpcore.api.toasts;

import net.dark_roleplay.drpcore.api.quests.IQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class QuestToast implements IToast{

	//Replace IQuest with your quest class thingy...
	//this to add it:
	//Minecraft.getMinecraft().getToastGui().add(new QuestToast(new IQuest()));
	
	private IQuest quest;
	
    private float displayedProgress = 1F;
    private float currentProgress = 0.4F;
    private long lastDelta;
    
	public QuestToast(IQuest quest){
		this.quest = quest;
	}
	
	@Override
	public Visibility draw(GuiToast toastGui, long delta) {
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		toastGui.drawTexturedModalRect(0, 0, 0, 64, 160, 32);
	        
		toastGui.getMinecraft().fontRenderer.drawString(quest.getQuestName(), 18, 7, -256);
        toastGui.getMinecraft().fontRenderer.drawString(quest.getQuestGoal(), 18, 18, -1);
	        
        Gui.drawRect(3, 28, 157, 29, -1);
        //For Animated progress thingy
        float f = (float)MathHelper.clampedLerp((double)this.displayedProgress, (double)this.currentProgress, (double)((float)(delta - this.lastDelta) / 100.0F));

        Gui.drawRect(3, 28, (int)(3.0F + 154.0F * f), 29, 0xFF44FF44);

        this.displayedProgress = f;
        this.lastDelta = delta;
        
		return (quest.isCompleted() || !quest.isTargeted()) ? Visibility.HIDE : Visibility.SHOW;
	}
}