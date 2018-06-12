package net.dark_roleplay.core.api.old.modules.time;

public enum Season {
	EARLY_SPRING("early_spring"),
	MIDDLE_SPRING("middle_spring"),
	LATE_SPRING("late_spring"),
	EARLY_SUMMER("early_summer"),
	MIDDLE_SUMMER("middle_summer"),
	LATE_SUMMER("late_summer"),
	EARLY_AUTUMN("early_autumn"),
	MIDDLE_AUTUMN("middle_autumn"),
	LATE_AUTUMN("late_autumn"),
	EARLY_WINTER("early_winter"),
	MIDDLE_WINTER("middle_winter"),
	LATE_WINTER("late_winter");
	
	private String name;
	
	private Season(String name){
		this.name = name;
	}
	
	public static Season getSeasonByID(String name){
		for(Season s : values()){
			if(s.name.equals(name)){
				return s;
			}
		}
		return EARLY_SPRING;
	}
	
	public String getName(){
		return this.name();
	}
}
