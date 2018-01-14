package net.dark_roleplay.drpcore.modules.time;

import net.dark_roleplay.drpcore.modules.crops.ICropHandler;

public interface IDateHandler {

	public boolean attemptIncrease(long dayTicks);
	
	public Date getDate();
	
	public long getLastTick();
	
	public void setLastTick(long tick);
	
	public void setDate(Date date);
	
	public static class Impl implements IDateHandler{

		private Date date = new Date(0, Date.SEASON.EARLY_SPRING, 0);
		
		private long lastTick = 0;
		
		@Override
		public boolean attemptIncrease(long dayTicks) {
			if(lastTick > dayTicks){
				this.date.addDays(1);
				this.lastTick = dayTicks;
				return true;
			}
			this.lastTick = dayTicks;
			return false;
		}

		@Override
		public Date getDate() {
			return this.date;
		}

		@Override
		public void setDate(Date date) {
			this.date = date;
		}

		@Override
		public long getLastTick() {
			return lastTick;
		}

		@Override
		public void setLastTick(long tick) {
			this.lastTick = tick;
		}
	}
}
