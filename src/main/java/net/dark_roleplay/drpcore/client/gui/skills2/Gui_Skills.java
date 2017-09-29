package net.dark_roleplay.drpcore.client.gui.skills2;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Frame;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Icon;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Label;
import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Screen;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;
import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillTree;
import net.dark_roleplay.drpcore.api.skills.SkillTreeData;
import net.dark_roleplay.drpcore.api.util.DRPUtil;
import net.dark_roleplay.drpcore.client.gui.advanced.wrappers.Variable_Object;
import net.dark_roleplay.drpcore.common.util.toasts.ToastController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class Gui_Skills extends Gui_Screen{

	private Gui_Frame mainFrame;
	private Gui_Frame descriptionFrame;
	private Gui_Frame skillTree;
	private Gui_Label descNameLabel;
	private Gui_Label descDescriptionLabel;
	private Gui_Label skillTreeNameLabel;
	
	private Variable_Object<Skill> lastSkill;
	private Variable_Object<Skill> selectedSkill;
	private Gui_Icon selectedSkillIcon;
	private int selectedSkillIconID;
	
	private List<SkillTree> allSkillTrees;
	private Variable_Int skillTreePos;
	private Variable_Object<SkillTree> selectedSkillTree;
	
	private List<Button_Skill> skillButtons;
	
	private boolean initialized = false;;
	
	private int tick;
	
	public void initGui(){
		if(!initialized){
			this.allSkillTrees = DRPUtil.getRegistrySkillTrees().getValues();
			
			if(this.allSkillTrees.isEmpty()){
				ToastController.displayInfoToast("No skills available", null);
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				return;
			}
			
			//WRAPPERS
			this.lastSkill = new Variable_Object<Skill>();
			this.selectedSkill = new Variable_Object<Skill>();
			this.skillTreePos = new Variable_Int(0);
			this.selectedSkillTree = new Variable_Object<SkillTree>(this.allSkillTrees.get(this.skillTreePos.get()));
			
			//MAIN FRAME
			mainFrame = new Gui_Frame(this, 0, 0, 300, 125, true);
			
			skillButtons = new ArrayList<Button_Skill>();
			for(Skill skill : this.selectedSkillTree.get().getSkills()){
				SkillTreeData data = skill.getDataForTree(this.selectedSkillTree.get());
				skillButtons.add(new Button_Skill(this.selectedSkill, skill, 2 + (data.getPosX() * 30) , 2 + (data.getPosY() * 30)));
				mainFrame.addChild(skillButtons.get(skillButtons.size()-1));
			}

			this.addElement(mainFrame);
			
			//SKILL TREE NAME FRAME
			skillTree = new Gui_Frame(this, 0, 0, 300, 20, false);
			
			skillTreeNameLabel = new Gui_Label(null, 0xFF333333);
			skillTreeNameLabel.disableShadow();
			skillTreeNameLabel.setSize(280, 20);
			skillTreeNameLabel.setText(I18n.format(this.selectedSkillTree.get().getUnlocalizedName()));
			skillTree.addChild(this.skillTreeNameLabel);
			
			this.addElement(skillTree);
			
			//DESCRIPTION FRAME
			descriptionFrame = new Gui_Frame(this, 0, 0, 300, 75, true);
			
			descNameLabel = new Gui_Label(null, 0xFFFFFFFF);
			descNameLabel.setPos(23, 7);
			descNameLabel.setSize(260, 8);
			descriptionFrame.addChild(descNameLabel);

			descDescriptionLabel = new Gui_Label(null, 0xFFEEEEEE);
			descDescriptionLabel.setPos(2, 23);
			descDescriptionLabel.setSize(260, 40);
			descriptionFrame.addChild(descDescriptionLabel);
			
			this.selectedSkillIcon = new Gui_Icon(null);
			selectedSkillIconID = descriptionFrame.addChild(selectedSkillIcon);

			this.addElement(descriptionFrame);
			
			//Initial Initialization is done, don't repeat
			this.initialized = true;
		}
		
		mainFrame.setPos((this.width - 300) / 2, (this.height - 150) / 2 - 15);
		descriptionFrame.setPos((this.width - 300) / 2, (this.height - 150) / 2 + 111);
		skillTree.setPos((this.width - 300) / 2, (this.height - 150) / 2 - 36);
	}
	
	@Override
	public void updateScreen(){
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		
		if(this.lastSkill.get() != this.selectedSkill.get()){
			this.descNameLabel.setText(I18n.format(this.selectedSkill.get().getUnlocalizedName()));
			this.descDescriptionLabel.setText(I18n.format(this.selectedSkill.get().getUnlocalizedDesc()));
			this.lastSkill.set(this.selectedSkill.get());
			this.selectedSkillIcon = this.selectedSkill.get().getIcon().getClone();
			this.selectedSkillIcon.setSize(20, 20);
			this.selectedSkillIcon.setPos(1, 1);
			this.descriptionFrame.setChild(selectedSkillIconID, this.selectedSkillIcon);
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
