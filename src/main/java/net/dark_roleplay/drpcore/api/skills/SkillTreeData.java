package net.dark_roleplay.drpcore.api.skills;

public class SkillTreeData {

	private SkillTree tree;
	private int posX;
	private int posY;
	
	public SkillTreeData(SkillTree tree, int posX, int posY){
		this.tree = tree;
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX(){
		return this.posX;
	}

	public int getPosY(){
		return this.posY;
	}
	
	public SkillTree getSkillTree(){
		return this.tree;
	}
}