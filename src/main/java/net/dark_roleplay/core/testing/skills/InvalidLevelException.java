package net.dark_roleplay.core.testing.skills;

public class InvalidLevelException extends RuntimeException{
	
	Type type;
	
	public InvalidLevelException(String message, Type type) {
		super(message);
	}
	
	public Type getType() {
		return this.type;
	}
	
	public static enum Type{
		CANT_LEVEL,
		BEYOND_MAX_LEVEL,
		TO_SMALL_LEVEL
	}
}
