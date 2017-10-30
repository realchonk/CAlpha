package de.longcity.interpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import de.longcity.interpreter.type.nullptr_t;
import de.longcity.interpreter.type.pointer_t;

public final class Interpreter {
	private static final nullptr_t nullptr = nullptr_t.nullptr;
	public static final List<Variable> vars;
	public static final List<Function> funcs;
	private static final List<IncludeLib> natives;
	public static final Interpreter interpreter = new Interpreter();
	static {
		vars = new LinkedList<>();
		funcs = new ArrayList<>();
		natives = new ArrayList<>();
	}
	/*
	 * #######################################
	 * ########### PUBLIC METHODS ############
	 * #######################################
	 */
	private Interpreter() {}
	public byte interprete(File file, boolean binary) {
		if(!file.exists())return 1;
		Scanner scanner;
		try {
			if(binary) scanner = decode(file);
			else scanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		if(scanner == null) return 2;
		while(scanner.hasNextLine()) {
			parse(scanner.nextLine().trim(), binary);
		}
		scanner.close();
		return 0;
	}
	public byte interprete(InputStream in, boolean binary) {
		try {
			File file = File.createTempFile("lclangi", ".lclangtmp");
			Scanner scanner = new Scanner(in);
			FileWriter fw = new FileWriter(file);
			
			while(scanner.hasNextLine()) {
				fw.write(scanner.nextLine());
			}
			
			scanner.close();
			fw.close();
			return interprete(file, binary);
		} catch (IOException e) {
			return 2;
		}
	}
	public static Object invoke(Function f, Object[] params) {
		try {
			return f.invoke(params);
		} catch (NullPointerException e) {
			System.err.println("The function isn't avaible!");
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Not the right parameter-count!");
		} catch (ClassCastException e) {
			System.err.println("Invalid types given! Check the Docs!");
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid type for a native function!");
		} catch (Exception e) {
			System.err.println("An Exception with type "+e.getClass().getName()+" occured!");
			if(Main.__DEBUG)e.printStackTrace();
		}
		return nullptr;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * #######################################
	 * ########### PRIVATE METHODS ###########
	 * #######################################
	*/
	/*
	 * #######################################
	 * ############ STATIC METHODS ###########
	 * #######################################
	 */
	private static Scanner decode(File file) throws Exception {
		Scanner scanner = new Scanner(file);
		String code = "", line;
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			if(line.isEmpty())continue;
			code += new String(new BigInteger(line, 2).toByteArray()) + "\n";
		}
		scanner.close();
		return new Scanner(code);
	}
	private static Variable getV(String name) {
		for(Variable v : vars) {
			if(v.name.equals(name)) return v;
		}
		Variable v = new Variable(name);
		vars.add(v);
		return v;
	}
	private static Variable getDV(String name) {
		for(Variable v : vars) {
			if(v.name.equals(name)) return v;
		}
		return null;
	}
	private static Function getF(String name) {
		for(Function f : funcs) {
			if(f.name.equals(name)) return f;
		}
		return null;
	}
	/*
	 * #######################################
	 * ########## NONSTATIC METHODS ##########
	 * #######################################
	 */
	@SuppressWarnings("unchecked")
	private boolean importlib(String path, boolean mode) {
		if (mode) {
			try {
				Class<? extends IncludeLib> clazz = (Class<? extends IncludeLib>) getClass().getClassLoader()
						.loadClass("include.nativelib." + path);
				IncludeLib lib = clazz.newInstance();
				lib.init();
				return natives.add(lib);
			} catch (Exception e) {
				return false;
			} 
		} else {
			return false;
		}
	}
	private Object[] parseP(String params) {
		String[] split = params.split(",");
		if(split.length==1 && split[0].isEmpty()) return new Object[0];
		Object[] rparams = new Object[split.length];
		
		for(int i = 0; i < split.length; i++) {
			rparams[i] = parseV(split[i]);
		}
		
		return rparams;
	}
	private Object parseV(String value) {
		value = value.trim();
		if(value.equals("nullptr"))	return nullptr;
		if(value.equals("true"))return true;
		if(value.equals("false"))return false;
		if(value.startsWith("\"") && value.endsWith("\"")) return value.substring(1, value.length()-1);
		if(value.length() == 3 && value.startsWith("'") && value.endsWith("'")) return value.charAt(1);
		try {
			String str = value.substring(0, value.length()-1);
			if(value.endsWith("B")) {
				return Byte.parseByte(str);
			}else if(value.endsWith("C")) {
				return (char) Integer.parseInt(str);
			}else if(value.endsWith("S")) {
				return Short.parseShort(str);
			}else if(value.endsWith("I")) {
				return Integer.parseInt(str);
			}else if(value.endsWith("L")) {
				return Long.parseLong(str);
			}else if(value.endsWith("D")) {
				return Double.parseDouble(str);
			}else if(value.endsWith("F")) {
				return Float.parseFloat(str);
			}else if(value.startsWith("0b")) {
				return Integer.parseInt(value.substring(2), 2);
			}else if(value.startsWith("0x")) {
				return Integer.parseInt(value.substring(2), 16);
			}else if(value.startsWith("0o")) {
				return Integer.parseInt(value.substring(2), 8);
			}else if(value.startsWith("0z")) {
				return Integer.parseInt(value.substring(2), 36);
			}else if(value.endsWith("b")) {
				return Integer.parseInt(str, 2);
			}else if(value.endsWith("h")) {
				return Integer.parseInt(str, 16);
			}else if(value.endsWith("o")) {
				return Integer.parseInt(str, 8);
			}else if(value.endsWith("z")) {
				return Integer.parseInt(str, 36);
			}
		} catch (NumberFormatException e) {}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {}
		for(Variable v : vars) {
			if(v.name == value) return v;
		}
		if(value.endsWith(")")) {
			String funcname = value.substring(0, value.indexOf('(')).trim();
			Function f = getF(funcname);
			if(f == null) {
				System.err.println("Function "+funcname+" not found!");
				return nullptr;
			}
			return invoke(f, parseP(value.substring(value.indexOf('(')+1, value.lastIndexOf(')'))));
		}
		if(value.startsWith("[") && value.endsWith("]")) {
			if(value.length() == 2)return new Object[0];
			String[] split = value.substring(value.indexOf('[')+1, value.length()-1).split(",");
			Object[] array = new Object[split.length];
			for(int i = 0; i < split.length; i++) {
				array[i] = parseV(split[i]);
			}
			return array;
		}
		if(value.startsWith("&")) {
			String varname = value.substring(1);
			int pvalue = -1;
			for(int i = 0; i < vars.size(); i++) {
				if(vars.get(i).name.equals(varname)) {
					pvalue = i;
					break;
				}
			}
			if(pvalue == -1) return nullptr;
			return new pointer_t(pvalue);
		}else if(value.startsWith("*")) {
			Variable var = getDV(value.substring(1));
			if(var == null) {
				return nullptr.getReference();
			}
			if(var.value instanceof pointer_t) {
				return ((pointer_t)var.value).getReference();
			} else {
				System.err.println("Can't dereference an object!");
				return nullptr.getReference();
			}
		}
		for(Variable v : vars) {
			if(v.name.equals(value))return v.value;
		}
		System.err.println("Variable "+value+" not found!");
		return nullptr;
	}
	private void parse(String line, boolean binary) {
		if(line.startsWith("//") || line.isEmpty())return;
		if(line.startsWith("var ")) {
			Variable v = getV(line.substring(4, line.indexOf('=')).trim());
			if(v.isConst()) {
				System.err.println("Can't change a const value!");
				return;
			}
			v.value = parseV(line.substring(line.indexOf('=')+1));
			return;
		}
		if(line.startsWith("const ")) {
			Variable v = getV(line.substring(5, line.indexOf('=')).trim());
			if(v.isConst()) {
				System.err.println("Can't change a const value!");
				return;
			}
			v.value = parseV(line.substring(line.indexOf('=')+1));
			v.setConst();
			return;
		}
		if(line.endsWith(")")) {
			String fn = line.substring(0, line.indexOf('('));
			invoke(getF(fn), parseP(line.substring(line.indexOf('(')+1, line.length()-1)));
			return;
		}
		if(line.startsWith("importlib")) {
			String path;
			boolean mode;
			if(line.contains("<")) {
				if(!line.contains(">")) {
					System.err.println("Syntax error!");
					return;
				}
				mode = true;
				path = line.substring(line.indexOf('<')+1, line.indexOf('>'));
			} else {
				if(!line.contains("\"")) {
					System.err.println("Syntax error!");
					return;
				}
				mode = false;
				path = line.substring(line.indexOf('"')+1, line.lastIndexOf('"'));
			}
			if(!importlib(path, mode)) {
				System.err.println("Can't import nativelib "+path+"!");
			}
		}
		if(line.startsWith("*")) {
			String varname = line.substring(1, line.indexOf('=')).trim();
			Variable var = getDV(varname);
			if(var == null) {
				System.err.println("Variable not found!");
				return;
			}
			if(!(var.value instanceof pointer_t)) {
				System.err.println("Can't dereference an object!");
				return;
			}
			((pointer_t)var.value).setReference(parseV(line.substring(line.indexOf('=')+1).trim()));
		}
		// nativefunc <name>(<Type>*) from <nativelib>
		if(line.startsWith("nativefunc")) {
			String name = line.substring("nativefunc ".length(), line.indexOf('(')).trim();
			String from = line.substring(line.indexOf("from ")+5).trim();
			String[] types = line.substring(line.indexOf('(')+1, line.indexOf(')')).split(",");
			for(int i = 0; i < types.length; i++)types[i] = types[i].trim();
			if(types.length == 1 && types[0].isEmpty()) types = new String[0];
			for(IncludeLib lib : natives) {
				if(lib.name.equalsIgnoreCase(from)) {
					try {
						Method m = lib.getFunction(name, types);
						String ns;
						if(lib.getNameSpace().isEmpty())ns = "";
						else ns = lib.getNameSpace() + "::";
						funcs.add(new Function(ns+name) {
							@Override
							public Object invoke(Object... params) throws Exception {
								if(m.getName() == "doNothing")m.invoke(lib);
								return m.invoke(lib, params);
							}
						});
						
					} catch (Exception e) {
						System.err.println("Can't define the nativefunc "+name);
					}
					return;
				}
			}// nativevar <name> from <lib>
		}else if(line.startsWith("nativevar")) {
			String sub = line.substring(line.indexOf(' ')+1).trim();
			String name = sub.substring(0, sub.indexOf(' ')).trim();
			String libname = sub.substring(sub.indexOf("from ")+5).trim();
			for(IncludeLib lib : natives) {
				if(lib.name.equalsIgnoreCase(libname)) {
					try {
						if(lib.getVariable(name) == null) throw new NullPointerException();
						vars.add(lib.getVariable(name));
					} catch (Exception e) {
						System.err.println("Can't load a native variable!");
					}
				}
			}
		}
	}
}
