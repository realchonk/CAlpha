package include.nativelib;

import de.longcity.interpreter.IncludeLib;

public class stdint extends IncludeLib {
	@Override
	protected String getNameSpace() {
		return "int";
	}
	@NativeFunction(name = "tob", paramtypes = "Number")
	public byte func0(Number num) {
		return num.byteValue();
	}
	@NativeFunction(name = "tos", paramtypes = "Number")
	public short func2(Number num) {
		return num.shortValue();
	}
	@NativeFunction(name = "toi", paramtypes = "Number")
	public int func3(Number num) {
		return num.intValue();
	}
	@NativeFunction(name = "tol", paramtypes = "Number")
	public long func4(Number num) {
		return num.longValue();
	}
	@NativeFunction(name = "tof", paramtypes = "Number")
	public float func5(Number num) {
		return num.floatValue();
	}
	@NativeFunction(name = "tod", paramtypes = "Number")
	public double func6(Number num) {
		return num.doubleValue();
	}
	@NativeFunction(name = "toa", paramtypes = "Number")
	public String func7(Number num) {
		return num.toString();
	}
}
