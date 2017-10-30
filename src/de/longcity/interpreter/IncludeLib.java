package de.longcity.interpreter;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.longcity.interpreter.type.nullptr_t;

public abstract class IncludeLib {
	protected static final nullptr_t nullptr = nullptr_t.nullptr;
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	protected @interface NativeFunction {
		/**
		 * @return Name of the function
		 */
		String name();
		/**
		 * @return Parametertypes
		 */
		String[] paramtypes() default {};
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	protected @interface NativeVariable {
		/**
		 * @return name of variable
		 */
		String name();
		/**
		 * @return Attributes of the Variable
		 */
		VariableAttribute[] attributes() default VariableAttribute.CONST;
	}
	private Map<NativeFunction, Method> functions;
	private Map<NativeVariable, Field> variables;
	public final String name;
	public IncludeLib(String name) {
		this.name = name;
	}
	public IncludeLib() {
		this.name = getClass().getSimpleName();
	}
	protected void init() {
		functions = new HashMap<>();
		variables = new HashMap<>();
		for(Method m : getClass().getDeclaredMethods()) {
			for(Annotation a : m.getAnnotations()) {
				if(a instanceof NativeFunction) {
					functions.put((NativeFunction)a, m);
					break;
				}
			}
		}
		NativeVariable nv;
		Variable v;
		a: for(Field f : getClass().getDeclaredFields()) {
			f.setAccessible(true);
			for(Annotation a : f.getAnnotations()) {
				if(a instanceof NativeVariable) {
					if(!Modifier.isFinal(f.getModifiers())) {
						System.err.println("All native Field must be final!");
						continue a;
					}
					nv = (NativeVariable) a;
					v = new Variable(nv.name());
					v.setAttributes(nv.attributes());
					variables.put(nv, f);
					break;
				}
			}
		}
	}
	public Method getFunction(String name, String[] paramtypes) throws Exception {
		for(Entry<NativeFunction, Method> e : functions.entrySet()) {
			if(e.getKey().name().equals(name) && checkTypes(paramtypes, e.getKey().paramtypes())) {
				return e.getValue();
			}
		}
		return null;
	}
	public Variable getVariable(String name) throws Exception{
		for(Entry<NativeVariable, Field> e : variables.entrySet()) {
			if(e.getKey().name().equals(name)) {
				String ns;
				if(getNameSpace().isEmpty())ns = "";
				else ns = getNameSpace() + "::";
				return new Variable(ns+name, e.getValue().get(this));
			}
		}
		return null;
	}
	private boolean checkTypes(String[] t1, String[] t2) {
		if(t1.length != t2.length)return false;
		for(int i = 0; i < t1.length; i++) {
			if(!t1[i].equals(t2[i]))return false;
		}
		return true;
	}
	protected String getNameSpace() {
		return getClass().getSimpleName();
	}
	protected void doNothing() {}
}