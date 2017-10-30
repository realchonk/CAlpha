package de.longcity.interpreter;

public abstract class Function {
	public final String name;
	public Function(String name) {
		this.name = name;
	}
	public abstract Object invoke(Object... params) throws Exception;
}
