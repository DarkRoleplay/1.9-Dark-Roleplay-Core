package net.drpcore.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInformation extends Gui{
	/** Time to display in Seconds **/
	private int[] displayTime = new int[3];
	/** Remaining Ticks for Second **/
	private int remainingTicks = 20;
	
	private int[] currentOffset = new int[3];
	
    private String text = "Hello world!";
    private String[] messages = new String[3];
 
    public GuiInformation(){}
    
    public void drawGui(Minecraft mc){
    	 ScaledResolution scaled = new ScaledResolution(mc);
         int width = scaled.getScaledWidth();
         int height = scaled.getScaledHeight();
  
         if(messages[2] != null){
        	 if(messages[1] != null){
        		 if(messages[0] != null){
        			 drawCenteredString(mc.fontRendererObj, messages[2], width / 2, 1 + (currentOffset[2] + 10 - 10), Integer.parseInt("FFAA00", 16));
                	 drawCenteredString(mc.fontRendererObj, messages[1], width / 2, 1 + (currentOffset[1] + 10), Integer.parseInt("FFAA00", 16));
                	 drawCenteredString(mc.fontRendererObj, messages[0], width / 2, 1 + (20), Integer.parseInt("FFAA00", 16));
        		 }
        	 }
         }else if(messages[1] != null){
    		 if(messages[0] != null){
            	 drawCenteredString(mc.fontRendererObj, messages[1], width / 2, 1 + (currentOffset[1] + currentOffset[0] - 10), Integer.parseInt("FFAA00", 16));
            	 drawCenteredString(mc.fontRendererObj, messages[0], width / 2, 1 + (currentOffset[0]), Integer.parseInt("FFAA00", 16));
    		 }
    	 }else if(messages[0] != null){
        	 drawCenteredString(mc.fontRendererObj, messages[0], width / 2, 1 + (currentOffset[0] - 10), Integer.parseInt("FFAA00", 16));
		 }
    }
    
    public void addMessage(String message){
    	this.messages[2] = this.messages[1];
    	this.displayTime[2] = this.displayTime[1];
    	
    	this.messages[1] = this.messages[0];
    	this.displayTime[1] = this.displayTime[0];
    	
    	this.messages[0] = message;
    	this.displayTime[0] = 10;
    	this.currentOffset[0] = 10;
    }
    
    public void removeMessage(int index){
    	if(index < this.messages.length)
    	this.messages[index] = null;
    }
    
    public void increaseTimer(int time){
    	if(remainingTicks > 0){
    		this.remainingTicks --;
    		for(int i = 0; i < 3; i++){
    			if(displayTime[i] == 0){
        			if(currentOffset[i] > 0){
        				if(i < 2){
        					if(this.messages[i + 1] == null)
        						currentOffset[i] --;
        				}else{
        					currentOffset[i] --;
        				}
        				
        			}else{
            			this.messages[i] = null;
            			this.displayTime[i] = -1;
            		}
        		}
    		}
    	}else{
    		remainingTicks = 20;
    		for(int i = 0; i < 3; i++){
    			displayTime[i] --;
    		}
    	}
    }
}