package net.dark_roleplay.drpcore.modules.time;

public class Date {

	private int day;
	private int month;
	private int year;
	
	public Date(int day, int month, int year){
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public long getDifferenze(Date date2){
		long diff = (this.year - date2.year * 365);
//		diff + ;
		
		return diff;
	}
}
