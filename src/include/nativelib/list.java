package include.nativelib;

import java.util.ArrayList;
import java.util.List;

import de.longcity.interpreter.IncludeLib;

public class list extends IncludeLib {
	@NativeFunction(name = "new")
	public List<Object> create() {
		return new ArrayList<>();
	}
	@NativeFunction(name = "get", paramtypes = {"List", "int"})
	public Object get(List<Object> list, int index) {
		return list.get(index);
	}
	@NativeFunction(name = "add", paramtypes = {"List", "Object"})
	public boolean add(List<Object> list, Object obj) {
		return list.add(obj);
	}
	@NativeFunction(name = "set", paramtypes = {"List", "int", "Object"})
	public Object set(List<Object> list, int index, Object obj) {
		return list.set(index, obj);
	}
	@NativeFunction(name = "remove", paramtypes = {"List", "int"})
	public Object remove(List<Object> list, int index) {
		return list.remove(index);
	}
	@NativeFunction(name = "removeObj", paramtypes = {"List", "Object"})
	public boolean remove(List<Object> list, Object obj) {
		return list.remove(obj);
	}
	@NativeFunction(name = "size", paramtypes = "List")
	public int size(List<Object> list) {
		return list.size();
	}
	@NativeFunction(name = "clear", paramtypes = "List")
	public void clear(List<Object> list) {
		list.clear();
	}
	@NativeFunction(name = "tostr", paramtypes = "List")
	public String toStr(List<Object> list) {
		return list.toString();
	}
	@NativeFunction(name = "toarray", paramtypes = "List")
	public Object[] toArray(List<Object> list) {
		return list.toArray();
	}
}
