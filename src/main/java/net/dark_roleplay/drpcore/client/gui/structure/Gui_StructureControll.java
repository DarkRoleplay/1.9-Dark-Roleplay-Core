package net.dark_roleplay.drpcore.client.gui.structure;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.dark_roleplay.drpcore.api.blueprints.Blueprint;
import net.dark_roleplay.drpcore.api.blueprints.BlueprintUtil;
import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Frame;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Label;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Screen;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Textfield;
import net.dark_roleplay.drpcore.api.gui.advanced.IGuiElement;
import net.dark_roleplay.drpcore.api.gui.advanced.buttons.Button_ChangeBool;
import net.dark_roleplay.drpcore.api.gui.advanced.buttons.Button_ChangeInt;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Boolean;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;
import net.dark_roleplay.drpcore.client.gui.advanced.buttons.blueprint_controll.Button_ChangeMode;
import net.dark_roleplay.drpcore.client.gui.advanced.buttons.blueprint_controll.Button_ChangeRenderMode;
import net.dark_roleplay.drpcore.client.gui.advanced.buttons.blueprint_controll.Button_SaveLoad;
import net.dark_roleplay.drpcore.client.gui.advanced.wrappers.Variable_Mode;
import net.dark_roleplay.drpcore.client.gui.advanced.wrappers.Variable_RenderMode;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.blocks.Packet_LoadBlueprint;
import net.dark_roleplay.drpcore.common.network.packets.blocks.Packet_SaveBlueprint;
import net.dark_roleplay.drpcore.common.network.packets.blocks.SyncPacket_BlueprintBlock;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public class Gui_StructureControll extends Gui_Screen {

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID,"textures/guis/recipe_crafting_simple.png");

	private TE_BlueprintController te;

	private Variable_Int offsetX;
	private Variable_Int offsetY;
	private Variable_Int offsetZ;
	
	private Variable_Int sizeX;
	private Variable_Int sizeY;
	private Variable_Int sizeZ;
	
	private Variable_RenderMode renderMode;
	
	private Variable_Mode mode;
	
	private String name = "";
	private String architects = "";
	
	
	private int posX;
	private int posY;
	
	private Gui_Frame mainFrame;
	

	private Gui_Label lblOffset;
	private Button_ChangeInt decOffsetX;
	private Gui_Label lblOffsetX;
	private Button_ChangeInt incOffsetX;
	private Button_ChangeInt decOffsetY;
	private Gui_Label lblOffsetY;
	private Button_ChangeInt incOffsetY;
	private Button_ChangeInt decOffsetZ;
	private Gui_Label lblOffsetZ;
	private Button_ChangeInt incOffsetZ;
	
	private Gui_Label lblSize;
	private Button_ChangeInt decSizeX;
	private Gui_Label lblSizeX;
	private Button_ChangeInt incSizeX;
	private Button_ChangeInt decSizeY;
	private Gui_Label lblSizeY;
	private Button_ChangeInt incSizeY;
	private Button_ChangeInt decSizeZ;
	private Gui_Label lblSizeZ;
	private Button_ChangeInt incSizeZ;
	
	private Gui_Label lblRenderMode;
	private Button_ChangeRenderMode btnRenderMode;
	
	private Button_ChangeMode changeMode;
	
	private Button_SaveLoad saveLoad;
	
//	private Gui_Textfield nameField;
	
	private int nameEditPosX, nameEditPosY, nameEditSizeX, nameEditSizeY;
    private GuiTextField nameEdit;
	private Gui_Label lblNameEdit;
    
    private int architectsPosX, architectsPosY, architectsSizeX, architectsSizeY;
	private Gui_Label lblArchitectsEdit;
    private GuiTextField architectsEdit;
    
    private GuiTextField focused;
    
	private boolean initialized = false;
	
	public Gui_StructureControll(TE_BlueprintController te){
		this.te = te;
		BlockPos offset = te.getOffset();
		this.offsetX = new Variable_Int(offset.getX());
		this.offsetY = new Variable_Int(offset.getY());
		this.offsetZ = new Variable_Int(offset.getZ());
		
		BlockPos size = te.getSize();
		this.sizeX = new Variable_Int(size.getX());
		this.sizeY = new Variable_Int(size.getY());
		this.sizeZ = new Variable_Int(size.getZ());
		
		this.name = te.getName();
		this.architects = te.getArchitects();
		
		this.renderMode = new Variable_RenderMode(te.getRenderMode());
		
		this.mode = new Variable_Mode(te.getMode());
	}
	
	@Override
	public void updateScreen(){
		this.nameEdit.updateCursorCounter();
		this.architectsEdit.updateCursorCounter();
	}
	
	public void update(){
		switch(this.mode.get()){
			case CORNER:
				this.setNameGui(false);
				this.setArchitectsGui(false);
				this.setSizeGui(false);
				this.setBBGui(false);
				this.setOffsetGui(false);
				this.saveLoad.setVisible(false);
				break;
			case LOAD:
				this.setSizeGui(false);
				this.setArchitectsGui(false);
				this.setBBGui(true);
				this.setNameGui(true);
				this.setOffsetGui(true);
				this.saveLoad.setVisible(true);
				break;
			case SAVE:
				this.setSizeGui(true);
				this.setArchitectsGui(true);
				this.setBBGui(true);
				this.setNameGui(true);
				this.setOffsetGui(true);
				this.saveLoad.setVisible(true);
				break;
		}
		this.te.setOffset(new BlockPos(this.offsetX.get(), this.offsetY.get(), this.offsetZ.get()));
		this.te.setSize(new BlockPos(this.sizeX.get(), this.sizeY.get(), this.sizeZ.get()));
		this.te.setRenderMode(this.renderMode.get());
		this.te.setMode(this.mode.get());
		this.te.setName(this.name);
		this.te.setArchitects(this.architects);
		
	}
	
	@Override
	public void initGui(){
        Keyboard.enableRepeatEvents(true);

		this.posX = (this.width / 2) - 150;
		this.posY = (this.height / 2) - 100;
		
		int btnOffsetY = 73;
		
		if(!this.initialized){
			this.mainFrame = new Gui_Frame(this, this.posX, this.posY, 300, 200);
			
			this.mainFrame.addChild(this.lblOffset = new Gui_Label("gui.structure.offset", 0xFFFFFFFF, 0, btnOffsetY));
			this.lblOffset.setSize(this.mainFrame.getWidth() - 10, 10);
			this.mainFrame.addChild(this.decOffsetX = (Button_ChangeInt) new Button_ChangeInt(this.offsetX, 	-1, 	0, 		11 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incOffsetX = (Button_ChangeInt) new Button_ChangeInt(this.offsetX, 	1, 		35, 	11 + btnOffsetY, 12, 17).setText("+"));
			this.mainFrame.addChild(this.decOffsetY = (Button_ChangeInt) new Button_ChangeInt(this.offsetY, 	-1,		55,		11 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incOffsetY = (Button_ChangeInt) new Button_ChangeInt(this.offsetY, 	1, 		90, 	11 + btnOffsetY, 12, 17).setText("+"));
			this.mainFrame.addChild(this.decOffsetZ = (Button_ChangeInt) new Button_ChangeInt(this.offsetZ, 	-1, 	110,	11 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incOffsetZ =(Button_ChangeInt)  new Button_ChangeInt(this.offsetZ, 	1, 		145, 	11 + btnOffsetY, 12, 17).setText("+"));

			this.mainFrame.addChild(this.lblSize = new Gui_Label("gui.structure.size", 0xFFFFFFFF, 0, 31 + btnOffsetY));
			this.lblSize.setSize(this.mainFrame.getWidth() - 10, 10);
			this.mainFrame.addChild(this.decSizeX = (Button_ChangeInt) new Button_ChangeInt(this.sizeX, 	-1, 	0, 		42 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incSizeX = (Button_ChangeInt) new Button_ChangeInt(this.sizeX, 	1, 		35, 	42 + btnOffsetY, 12, 17).setText("+"));
			this.mainFrame.addChild(this.decSizeY = (Button_ChangeInt) new Button_ChangeInt(this.sizeY, 	-1,		55,		42 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incSizeY = (Button_ChangeInt) new Button_ChangeInt(this.sizeY, 	1, 		90, 	42 + btnOffsetY, 12, 17).setText("+"));
			this.mainFrame.addChild(this.decSizeZ = (Button_ChangeInt) new Button_ChangeInt(this.sizeZ, 	-1, 	110,	42 + btnOffsetY, 12, 17).setText("-"));
			this.mainFrame.addChild(this.incSizeZ = (Button_ChangeInt) new Button_ChangeInt(this.sizeZ, 	1, 		145, 	42 + btnOffsetY, 12, 17).setText("+"));
						
			this.mainFrame.addChild(this.lblRenderMode = new Gui_Label(I18n.format("gui.structure.render_mode"), 0xFFFFFFFF, this.mainFrame.getWidth() - 122, 1 + btnOffsetY));
			this.mainFrame.addChild(this.btnRenderMode = new Button_ChangeRenderMode(this.renderMode, this.mainFrame.getWidth() - 122, 11 + btnOffsetY, 110, 20));
			this.lblRenderMode.setSize(110, 10);
			
			this.mainFrame.addChild(this.changeMode = new Button_ChangeMode(this.mode, 95, 85 + btnOffsetY, 50, 20));
			
			this.mainFrame.addChild(this.saveLoad = new Button_SaveLoad(this, this.mode.get() == TE_BlueprintController.Mode.SAVE ? true : false, 150, 105 + btnOffsetY, 40, 20));
			
			this.mainFrame.addChild(this.lblNameEdit = new Gui_Label(I18n.format("gui.structure.name"), 0xFFFFFFFF));
			this.lblNameEdit.setPos(0, 0);
			this.lblNameEdit.setSize(this.mainFrame.getWidth() - 8, 10);
			
			this.mainFrame.addChild(this.lblArchitectsEdit = new Gui_Label(I18n.format("gui.structure.architects"), 0xFFFFFFFF));
			this.lblArchitectsEdit.setPos(0, 37);
			this.lblArchitectsEdit.setSize(this.mainFrame.getWidth() - 8, 10);
			
			
			this.addElement(this.mainFrame);
			this.initialized = true;
		}
		
		this.mainFrame.setPos(this.posX, this.posY);
		this.nameEdit = new GuiTextField(0, this.fontRenderer,(this.nameEditPosX = this.posX + 5), (this.nameEditPosY = this.posY + 18), (this.nameEditSizeX = 290), (this.nameEditSizeY = 20));
        this.nameEdit.setMaxStringLength(64);
        this.nameEdit.setText(this.name);
        
        this.architectsEdit = new GuiTextField(1, this.fontRenderer,(this.architectsPosX = this.posX + 5), (this.architectsPosY = this.posY + 55), (this.architectsSizeX = 290), (this.architectsSizeY = 20));
        this.architectsEdit.setMaxStringLength(256);
        this.architectsEdit.setText(this.architects);

		update();
    }
	
	@Override
	public void onGuiClosed(){
		this.update();
		
		DRPCorePackets.sendToServer(new SyncPacket_BlueprintBlock(this.te));
        Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		//Offset X
		this.drawString(this.fontRenderer,String.valueOf(this.offsetX.get()), 5, 5, new Color(255,255,255).getRGB());
		//Offset Y
		this.drawString(this.fontRenderer,String.valueOf(this.offsetY.get()), 5, 25, new Color(255,255,255).getRGB());
		//Offset Z
		this.drawString(this.fontRenderer,String.valueOf(this.offsetZ.get()), 5, 45, new Color(255,255,255).getRGB());
		

		//Size X
		this.drawString(this.fontRenderer,String.valueOf(this.sizeX.get()), 5, 65, new Color(255,255,255).getRGB());
		//Size Y
		this.drawString(this.fontRenderer,String.valueOf(this.sizeY.get()), 5, 85, new Color(255,255,255).getRGB());
		//Size Z
		this.drawString(this.fontRenderer,String.valueOf(this.sizeZ.get()), 5, 105, new Color(255,255,255).getRGB());
		
		//Bounding Box
		this.drawString(this.fontRenderer,String.valueOf(this.mode.get() == TE_BlueprintController.Mode.SAVE || this.mode.get() == TE_BlueprintController.Mode.LOAD), 5, 125, new Color(255,255,255).getRGB());
		//Mode
		this.drawString(this.fontRenderer,this.mode.get().getName(), 5, 165, new Color(255,255,255).getRGB());
		
        this.nameEdit.drawTextBox();
        this.architectsEdit.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
        if (this.nameEdit.getVisible() && this.nameEdit.isFocused() && isValidCharacterForName(typedChar, keyCode)){
            this.nameEdit.textboxKeyTyped(typedChar, keyCode);
            this.name = this.nameEdit.getText();
        }else if (this.architectsEdit.getVisible() && this.architectsEdit.isFocused() && isValidCharacterForName(typedChar, keyCode)){
            this.architectsEdit.textboxKeyTyped(typedChar, keyCode);
            this.architects = this.architectsEdit.getText();
        }
        
        if(keyCode == 1){
        	if(this.focused == null){
        		this.mc.displayGuiScreen(null);
        	}else{
        		this.focused.setFocused(false);
        		this.focused = null;
        	}
        }
        this.update();
    }
	
	private void setFocused(GuiTextField field){
		if(this.focused != null)
			this.focused.setFocused(false);
		this.focused = field;
		if(field != null)
			field.setFocused(true);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
			if((mouseX > this.nameEditPosX && mouseX < nameEditPosX + nameEditSizeX) && (mouseY > this.nameEditPosY && mouseY < nameEditPosY + nameEditSizeY)){
				this.setFocused(this.nameEdit);
				return;
			}else if((mouseX > this.architectsPosX && mouseX < architectsPosX + architectsSizeX) && (mouseY > this.architectsPosY && mouseY < architectsPosY + architectsSizeY)){
				this.setFocused(this.architectsEdit);
				return;
			}else{
				this.setFocused(null);
			}
        	
        	for(int i = 0; i < this.elements.size(); ++i){
    			IGuiElement element = this.elements.get(i);
    			if((mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
					if(element.mouseClicked(mouseX - element.getPosX(), mouseY - element.getPosY(), mouseButton)){
						this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
					}
				}
    		}
        }
		this.update();
    }
	
	public void save(){
		this.update();
		DRPCorePackets.sendToServer(new Packet_SaveBlueprint(this.te));
	}

	public void load(){
		this.update();
		DRPCorePackets.sendToServer(new Packet_LoadBlueprint(this.te));
	}
	
	private void setSizeGui(boolean visible){
		this.lblSize.setVisible(visible);
//		this.lblSizeX.setVisible(visible);
//		this.lblSizeY.setVisible(visible);
//		this.lblSizeZ.setVisible(visible);
		this.decSizeX.setVisible(visible);
		this.decSizeY.setVisible(visible);
		this.decSizeZ.setVisible(visible);
		this.incSizeX.setVisible(visible);
		this.incSizeY.setVisible(visible);
		this.incSizeZ.setVisible(visible);
	}
	
	private void setOffsetGui(boolean visible){
		this.lblOffset.setVisible(visible);
//		this.lblOffsetX.setVisible(visible);
//		this.lblOffsetY.setVisible(visible);
//		this.lblOffsetZ.setVisible(visible);
		this.decOffsetX.setVisible(visible);
		this.decOffsetY.setVisible(visible);
		this.decOffsetZ.setVisible(visible);
		this.incOffsetX.setVisible(visible);
		this.incOffsetY.setVisible(visible);
		this.incOffsetZ.setVisible(visible);
	}
	
	private void setNameGui(boolean visible){
		this.lblNameEdit.setVisible(visible);
		this.nameEdit.setVisible(visible);
	}
	
	private void setArchitectsGui(boolean visible){
		this.lblArchitectsEdit.setVisible(visible);
		this.architectsEdit.setVisible(visible);
	}
	
	private void setBBGui(boolean visible){
		this.lblRenderMode.setVisible(visible);
		this.btnRenderMode.setVisible(visible);
	}
	
    public static final char[] ILLEGAL_CHARACTERS = new char[] {'/', '.', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':', ','};															
    public static final int[] LEGAL_KEY_CODES = new int[] {203, 205, 14, 211, 199, 207};
	private static boolean isValidCharacterForName(char c, int keyCode){
        boolean flag = true;
        for (int i : LEGAL_KEY_CODES){
            if (i == keyCode) {
                return true;
            }
        }
        for (char c0 : Gui_StructureControll.ILLEGAL_CHARACTERS){
            if (c0 == c){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
