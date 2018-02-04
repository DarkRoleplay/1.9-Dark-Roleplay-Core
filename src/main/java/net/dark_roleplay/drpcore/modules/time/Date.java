package net.dark_roleplay.drpcore.modules.time;

import net.minecraft.nbt.NBTTagCompound;

public class Date {

	private int day;
	private Season season;
	private int year;
	
	public Date(int day, Season season, int year){
		this.day = day;
		this.season = season;
		this.year = year;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public Season getSeason(){
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
	
	public Date(NBTTagCompound tag){
		this.day = tag.getInteger("day");
		this.season = Season.getSeasonByID(tag.getString("season"));
		this.year = tag.getInteger("year");
	}
	
	public NBTTagCompound toNBT(){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("year", this.getYear());
		tag.setInteger("day", this.getDayOfSeason());
		tag.setString("season", this.getSeason().getName());
		return tag;
	}
	
	public void addSeason(int amount){
		for(int i = 0; i < amount; i++){
			switch(this.season){
			case EARLY_AUTUMN:
				this.season = Season.MIDDLE_AUTUMN;
				break;
			case EARLY_SPRING:
				this.season = Season.MIDDLE_SPRING;
				break;
			case EARLY_SUMMER:
				this.season = Season.MIDDLE_SUMMER;
				break;
			case EARLY_WINTER:
				this.season = Season.MIDDLE_WINTER;
				break;
			case LATE_AUTUMN:
				this.season = Season.EARLY_WINTER;
				break;
			case LATE_SPRING:
				this.season = Season.EARLY_SUMMER;
				break;
			case LATE_SUMMER:
				this.season = Season.EARLY_AUTUMN;
				break;
			case LATE_WINTER:
				this.season = Season.EARLY_SPRING;
				break;
			case MIDDLE_AUTUMN:
				this.season = Season.LATE_AUTUMN;
				break;
			case MIDDLE_SPRING:
				this.season = Season.LATE_SPRING;
				break;
			case MIDDLE_SUMMER:
				this.season = Season.LATE_SUMMER;
				break;
			case MIDDLE_WINTER:
				this.season = Season.LATE_WINTER;
				break;
			}
		}
	}
}
