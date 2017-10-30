package include.nativelib;

import de.longcity.interpreter.IncludeLib;

public class stdio extends IncludeLib{
	@NativeVariable(name = "stdin")
	private final Object stdin = System.in;
	@NativeVariable(name = "stdout")
	private final Object stdout = System.out;
	@NativeVariable(name = "stderr")
	private final Object stderr = System.err;
	@Override
	protected String getNameSpace() {
		return "";
	}
	@NativeFunction(name = "print", paramtypes = "Object")
	public void func0(Object obj) {
		System.out.print(obj);
	}
	@NativeFunction(name = "println", paramtypes = "Object")
	public void func1(Object obj) {
		System.out.println(obj);
	}
	@NativeFunction(name = "printf", paramtypes = {"String", "Object[]"})
	public void func2(String str, Object[] args) {
		System.out.printf(str, args);
	}
}
