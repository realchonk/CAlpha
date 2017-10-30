package include.nativelib;

import java.lang.reflect.Array;

import de.longcity.interpreter.Function;
import de.longcity.interpreter.IncludeLib;
import de.longcity.interpreter.Interpreter;
import de.longcity.interpreter.Variable;

public class stdlib extends IncludeLib {
	@Override
	protected String getNameSpace() {
		return "";
	}
	@NativeFunction(name = "exit", paramtypes = "int")
	public void exit(int i) {
		System.exit(0);
	}
	@NativeFunction(name = "gc")
	public void gc() {
		System.gc();
	}
	@NativeFunction(name = "time")
	public long time() {
		return System.currentTimeMillis();
	}
	@NativeFunction(name = "getenv", paramtypes = "String")
	public String getenv(String name) {
		return System.getenv(name);
	}
	@NativeFunction(name = "memset", paramtypes = {"Array", "Object"})
	public void memset(Object array, Object obj) {
		for(int i = 0; i < Array.getLength(array); i++) {
			Array.set(array, i, obj);
		}
	}
	@NativeFunction(name = "memcopy", paramtypes = {"Array", "Array", "int"})
	public void memcopy(Object src, Object dest, int len) {
		System.arraycopy(src, 0, dest, 0, len);
	}
	@NativeFunction(name = "getfunctions")
	public String getfunctions() {
		String ret = "";
		
		for(Function f : Interpreter.funcs) {
			ret += f.name + ", ";
		}
		
		return ret.substring(0, ret.length()-2);
	}
	@NativeFunction(name = "getvariables")
	public String getvariables() {
		String ret = "";
		
		for(Variable v : Interpreter.vars) {
			ret += v.name + "=" + v.value.toString() + ", ";
		}
		
		return ret.substring(0, ret.length()-2);
	}
	@NativeFunction(name = "toascii", paramtypes = "Object")
	public String toascii(Object obj) {
		return obj.toString();
	}
	@NativeFunction(name = "equals", paramtypes = {"Object", "Object"})
	public boolean equals(Object x, Object y) {
		return x.equals(y);
	}
}
