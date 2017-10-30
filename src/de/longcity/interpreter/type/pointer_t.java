package de.longcity.interpreter.type;

import de.longcity.interpreter.Interpreter;

public class pointer_t extends Type {
	protected int value;
	public pointer_t() {
		value = -1;
	}
	public pointer_t(int value) {
		setValue(value);
	}
	@Override
	public String toString() {
		return value+"";
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Object getReference() {
		try {
			return Interpreter.vars.get(value).value;
		} catch (IndexOutOfBoundsException e) {
			return nullptr_t.nullptr.getReference();
		}
	}
	public void setReference(Object obj) {
		Interpreter.vars.get(value).value = obj;
	}
}
