package include.nativelib;

import de.longcity.interpreter.IncludeLib;

public class math extends IncludeLib {
	@Override
	protected String getNameSpace() {
		return "";
	}
	@NativeFunction(name = "add", paramtypes = {"Number", "Number"})
	public Number add(Number x, Number y) {
		return x.doubleValue() + y.doubleValue();
	}
	@NativeFunction(name = "sub", paramtypes = {"Number", "Number"})
	public Number sub(Number x, Number y) {
		return x.doubleValue() - y.doubleValue();
	}
	@NativeFunction(name = "mul", paramtypes = {"Number", "Number"})
	public Number mul(Number x, Number y) {
		return x.doubleValue() * y.doubleValue();
	}
	@NativeFunction(name = "div", paramtypes = {"Number", "Number"})
	public Number div(Number x, Number y) {
		return x.doubleValue() / y.doubleValue();
	}
	@NativeFunction(name = "mod", paramtypes = {"Number", "Number"})
	public Number mod(Number x, Number y) {
		return x.doubleValue() % y.doubleValue();
	}
	@NativeFunction(name = "pow", paramtypes = {"Number", "Number"})
	public Number pow(Number x, Number y) {
		return Math.pow(x.doubleValue(), y.doubleValue());
	}
	@NativeFunction(name = "sin", paramtypes = "Number")
	public Number sin(Number x) {
		return Math.sin(x.doubleValue());
	}
	@NativeFunction(name = "sinh", paramtypes = "Number")
	public Number sinh(Number x) {
		return Math.sinh(x.doubleValue());
	}
	@NativeFunction(name = "cos", paramtypes = "Number")
	public Number cos(Number x) {
		return Math.cos(x.doubleValue());
	}
	@NativeFunction(name = "cosh", paramtypes = "Number")
	public Number cosh(Number x) {
		return Math.cosh(x.doubleValue());
	}
	@NativeFunction(name = "tan", paramtypes = "Number")
	public Number tan(Number x) {
		return Math.tan(x.doubleValue());
	}
	@NativeFunction(name = "tanh", paramtypes = "Number")
	public Number tanh(Number x) {
		return Math.tanh(x.doubleValue());
	}
	@NativeFunction(name = "atan", paramtypes = "Number")
	public Number atan(Number x) {
		return Math.atan(x.doubleValue());
	}
	@NativeFunction(name = "atan2", paramtypes = {"Number", "Number"})
	public Number atan2(Number x, Number y) {
		return Math.atan2(x.doubleValue(), y.doubleValue());
	}
	@NativeFunction(name = "increment", paramtypes = "Number")
	public Number inc(Number x) {
		return new Double(x.doubleValue()+1);
	}
	@NativeFunction(name = "decrement", paramtypes = "Number")
	public Number dec(Number x) {
		return new Double(x.doubleValue()-1);
	}
	@NativeFunction(name = "ceil", paramtypes = "Number")
	public Number ceil(Number x) {
		return Math.ceil(x.doubleValue());
	}
	@NativeFunction(name = "sqrt", paramtypes = "Number")
	public Number sqrt(Number x) {
		return Math.sqrt(x.doubleValue());
	}
	@NativeFunction(name = "max", paramtypes = {"Number", "Number"})
	public Number max(Number x, Number y) {
		return Math.max(x.doubleValue(), y.doubleValue());
	}
	@NativeFunction(name = "min", paramtypes = {"Number", "Number"})
	public Number min(Number x, Number y) {
		return Math.min(x.doubleValue(), y.doubleValue());
	}
}
