package net.dark_roleplay.drpcore.modules.time;

public class Date {

	private int day;
	private SEASONS season;
	private int year;
	
	public Date(int day, SEASONS season, int year){
		this.day = day;
		this.season = season;
		this.year = year;
	}
	
	public long getDifferenze(Date date2){
		long diff = (this.year - date2.year * 365);
//		diff + ;
		
		return diff;
	}
	
	public static enum SEASONS{
		EARLY_SPRING,
		MIDDLE_SPRING,
		LATE_SPRING,
		EARLY_SUMMER,
		MIDDLE_SUMMER,
		LATE_SUMMER,
		EARLY_AUTUMN,
		MIDDLE_AUTUMN,
		LATE_AUTUMN,
		EARLY_WINTER,
		MIDDLE_WINTER,
		LATE_WINTER
	}
}
