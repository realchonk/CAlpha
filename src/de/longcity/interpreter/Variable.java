package de.longcity.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.longcity.interpreter.type.nullptr_t;

public class Variable {
	public final String name;
	private List<VariableAttribute> attributes;
	public Object value;
	public Variable(String name, Object value, VariableAttribute attributes) {
		this.name = name;
		this.value = value;
		this.attributes = Arrays.asList(attributes);
	}
	public Variable(String name, Object value) {
		this.name = name;
		this.value = value;
		attributes = new ArrayList<>();
	}
	public Variable(String name) {
		this.name = name;
		value = null;
		attributes = new ArrayList<>();
	}
	public boolean isPrimitive() {
		if(value==null || value instanceof nullptr_t)return true;
		if(value.getClass().getSuperclass() == Number.class) return true;
		if(value.getClass() == Boolean.class) return true;
		if(value.getClass() == Character.class) return true;
		return false;
	}
	public void setConst() {
		attributes.add(VariableAttribute.CONST);
	}
	public boolean isConst() {
		return attributes.contains(VariableAttribute.CONST);
	}
	public void setAttributes(VariableAttribute[] attributes) {
		this.attributes = Arrays.asList(attributes);
	}
}
