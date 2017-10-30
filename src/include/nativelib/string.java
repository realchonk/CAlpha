package include.nativelib;

import de.longcity.interpreter.IncludeLib;

public class string extends IncludeLib {
	@NativeFunction(name = "length", paramtypes = "String")
	public int length(String str) {
		return str.length();
	}
	@NativeFunction(name = "concat", paramtypes = {"String", "String"})
	public String concat(String x, String y) {
		return x.concat(y);
	}
	@NativeFunction(name = "concatc", paramtypes = {"String", "char"})
	public String concatc(String x, char y) {
		return x.concat(y+"");
	}
	@NativeFunction(name = "indexOf", paramtypes = {"String", "String"})
	public int indexOf(String str, String x) {
		return str.indexOf(x);
	}
	@NativeFunction(name = "lastIndexOf", paramtypes = {"String", "String"})
	public int lastIndexOf(String str, String x) {
		return str.lastIndexOf(x);
	}
	@NativeFunction(name = "charAt", paramtypes = {"String", "int"})
	public char charAt(String str, int index) {
		return str.charAt(index);
	}
	@NativeFunction(name = "startsWith", paramtypes = {"String", "String"})
	public boolean startsWith(String str, String x) {
		return str.startsWith(x);
	}
	@NativeFunction(name = "endsWith", paramtypes = {"String", "String"})
	public boolean endsWith(String str, String x) {
		return str.endsWith(x);
	}
	@NativeFunction(name = "substr", paramtypes = {"String", "int"})
	public String substr(String str, int start) {
		return str.substring(start);
	}
	@NativeFunction(name = "substr2", paramtypes = {"String", "int", "int"})
	public String substr(String str, int start, int end) {
		return str.substring(start, end);
	}
	@NativeFunction(name = "trim", paramtypes = "String")
	public String trim(String str) {
		return str.trim();
	}
	@NativeFunction(name = "contains", paramtypes = {"String", "String"})
	public boolean contains(String str, String x) {
		return str.contains(x);
	}
	@NativeFunction(name = "isEmpty", paramtypes = "String")
	public boolean isEmpty(String str) {
		return str.isEmpty();
	}
	@NativeFunction(name = "toUpperCase", paramtypes = "String")
	public String toUpperCase(String str) {
		return str.toUpperCase();
	}
	@NativeFunction(name = "toLowerCase", paramtypes = "String")
	public String toLowerCase(String str) {
		return str.toLowerCase();
	}
	@NativeFunction(name = "equalsIgnoreCase", paramtypes = {"String", "String"})
	public boolean equalsIgnoreCase(String x, String y) {
		return x.equalsIgnoreCase(y);
	}
	@NativeFunction(name = "equals", paramtypes = {"String", "String"})
	public boolean equals(String x, String y) {
		return x.equals(y);
	}
	@NativeFunction(name = "replace", paramtypes = {"String", "String", "String"})
	public String replace(String str, String x, String y) {
		return str.replace(x, y);
	}
	@NativeFunction(name = "replaceAll", paramtypes = {"String", "String", "String"})
	public String replaceAll(String str, String x, String y) {
		return str.replaceAll(x, y);
	}
	@NativeFunction(name = "replaceFirst", paramtypes = {"String", "String", "String"})
	public String replaceFirst(String str, String x, String y) {
		return str.replaceFirst(x, y);
	}
	@NativeFunction(name = "format", paramtypes = {"String", "Object[]"})
	public String format(String str, Object... args) {
		return String.format(str, args);
	}
}
