package de.longcity.interpreter.type;

public abstract class Type implements Cloneable{
	@Override
	public abstract String toString();
	
	
	@Override
	protected Type clone() {
		try {
			return (Type) super.clone();
		} catch (CloneNotSupportedException e) { return null; }
	}
}
