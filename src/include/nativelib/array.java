package include.nativelib;

import java.lang.reflect.Array;

import de.longcity.interpreter.IncludeLib;

public class array extends IncludeLib {
	@NativeFunction(name = "new", paramtypes = "int")
	public Object create(int size) throws Exception{
		Object array = Array.newInstance(Object.class, size);
		for(int i = 0; i < size; i++) {
			Array.set(array, i, nullptr);
		}
		return array;
	}
	@NativeFunction(name = "set", paramtypes = {"Array", "int", "Object"})
	public void set(Object array, int index, Object obj) {
		try {
			Array.set(array, index, obj);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The given index is out of bounds!");
		} catch (IllegalArgumentException e) {
			System.err.println("The given object is not an array!");
		}
	}
	@NativeFunction(name = "get", paramtypes = {"Array", "int"})
	public Object get(Object array, int index) {
		try {
			return Array.get(array, index);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("The given index is out of bounds!");
		} catch (IllegalArgumentException e) {
			System.err.println("The given object is not an array!");
		}
		return nullptr;
	}
	@NativeFunction(name = "length", paramtypes = "Array")
	public int length(Object array) {
		try {
			return Array.getLength(array);
		} catch (IllegalArgumentException e) {
			System.err.println("The given object is not an array!");
			return 0;
		}
	}
	@NativeFunction(name = "tostr", paramtypes = "Array")
	public String tostr(Object array) {
		String ret = "";
		for(int i = 0; i < Array.getLength(array); i++) {
			ret += Array.get(array, i);
		}
		return ret;
	}
}
