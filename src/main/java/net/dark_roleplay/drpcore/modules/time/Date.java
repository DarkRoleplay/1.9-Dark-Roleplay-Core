package net.dark_roleplay.drpcore.modules.time;

public class Date {

	private int day;
	private SEASON season;
	private int year;
	
	public Date(int day, SEASON season, int year){
		this.day = day;
		this.season = season;
		this.year = year;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public SEASON getSeason(){
		return this.season;
	}
	
	public int getDayOfSeason(){
		return this.day;
	}
	
	public void addDays(int amount){
		this.day += amount;
	}
	
	public Date copy(){
		return new Date(day, season, year);
	}
	
	public void addSeason(int amount){
		for(int i = 0; i < amount; i++){
			switch(this.season){
			case EARLY_AUTUMN:
				this.season = SEASON.MIDDLE_AUTUMN;
				break;
			case EARLY_SPRING:
				this.season = SEASON.MIDDLE_SPRING;
				break;
			case EARLY_SUMMER:
				this.season = SEASON.MIDDLE_SUMMER;
				break;
			case EARLY_WINTER:
				this.season = SEASON.MIDDLE_WINTER;
				break;
			case LATE_AUTUMN:
				this.season = SEASON.EARLY_WINTER;
				break;
			case LATE_SPRING:
				this.season = SEASON.EARLY_SUMMER;
				break;
			case LATE_SUMMER:
				this.season = SEASON.EARLY_AUTUMN;
				break;
			case LATE_WINTER:
				this.season = SEASON.EARLY_SPRING;
				break;
			case MIDDLE_AUTUMN:
				this.season = SEASON.LATE_AUTUMN;
				break;
			case MIDDLE_SPRING:
				this.season = SEASON.LATE_SPRING;
				break;
			case MIDDLE_SUMMER:
				this.season = SEASON.LATE_SUMMER;
				break;
			case MIDDLE_WINTER:
				this.season = SEASON.LATE_WINTER;
				break;
			}
		}
	}
	
	public static enum SEASON{
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
		
		private SEASON(String name){
			this.name = name;
		}
		
		public static SEASON getSeasonByID(String name){
			for(SEASON s : values()){
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
}
