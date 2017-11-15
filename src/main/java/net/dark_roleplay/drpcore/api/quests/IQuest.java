package net.dark_roleplay.drpcore.api.quests;

public class IQuest {

	public boolean isCompleted(){
		return false;
	}
	
	public boolean isTargeted(){
		return true;
	}
	
	public String getQuestName(){
		return "Slime Hunter";
	}
	
	public String getQuestGoal(){
		return "Kill 10 Slimes";
	}
	
}
