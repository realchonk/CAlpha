package include.nativelib;

import java.io.File;

import de.longcity.interpreter.Compiler;
import de.longcity.interpreter.IncludeLib;
import de.longcity.interpreter.Interpreter;

public class runtime extends IncludeLib {
	@NativeFunction(name = "execute", paramtypes = "Path")
	public byte execute(String path) {
		return Interpreter.interpreter.interprete(new File(path), true);
	}
	@NativeFunction(name = "compile", paramtypes = {"Path", "Path"})
	public byte compile(String in, String out) {
		return Compiler.compile(new File(in), new File(out));
	}
	@NativeFunction(name = "preprocess", paramtypes = {"Path", "Path"})
	public byte preprocess(String in, String out) {
		return Compiler.preprocess(new File(in), new File(out));
	}
	@Override
	protected String getNameSpace() {
		return "";
	}
}
